import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Knn {
   public static QuoteCollection vectors;
   public static Integer k = 3;
   public static DocumentDistance distanceFunc;
   public static Integer readUpTo = 100;

   public Knn(String filename, int k, DocumentDistance dist) {
      this.k = k;
      this.vectors = readVectors(filename, true, 100);
      this.distanceFunc = dist;
   }

   public Knn(QuoteCollection quotes, int k, DocumentDistance dist) {
      this.k = k;
      this.vectors = quotes;
      this.distanceFunc = dist;
   }

   public void setK(int k) {
      this.k = k;
   }

   public static QuoteCollection readVectors(String filename, boolean write_vectors, int readUpTo) {
      // uncomment this when we've properly processed the input
      /*try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("QuoteVectors")))) {
         return (QuoteCollection) is.readObject();
      } catch (Exception e) {
         System.out.println("Couldn't read vectors from file, recreating...");
      }*/
      QuoteCollection vectors = new QuoteCollection();
      try {
         FileReader fr = new FileReader(filename);
         BufferedReader br = new BufferedReader(fr);
         String line;
         int i = 0;
         while ( (line = br.readLine()) != null && i < readUpTo) {
            String character = null;
            if (i%2 == 1) { // only every other line contains information
               //System.out.println(i + ":" + line);
               String[] words = line.split(",");
               Integer season = Integer.parseInt(words[0]);
               Integer episode = Integer.parseInt(words[1]);
               character = words[2];
               words = getWords(Arrays.copyOfRange(words, 3, words.length));
               if(words.length >= 3 && words.length <= 20) {
                  Vector v = new DocumentVector(words, character);
                  vectors.add(v);
               }
            }
            else {
               while (i != 0 && line.length() != 1) {
                  String[] words = line.split(",");
                  words = getWords(words);
                  if(words.length >= 3 && words.length <= 20) {
                     Vector v = new DocumentVector(words, character);
                     
                     if(character != null)
                        vectors.add(v);
                  }
                  i++;
                  line = br.readLine();
               }
            }
            i++;
         }

         // normalize vectors
         vectors.normalize(vectors);

         if(write_vectors) {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File("QuoteVectors")));
            os.writeObject(vectors);
         }
      }
      catch (Exception e) {
         System.out.println(e);
         System.exit(-1);
      }

      return vectors;
   }

   private void printArray(String[] arr) {
      System.out.print("[");
      for(String s: arr) {
         System.out.print(s + ", ");
      }
      System.out.println("]");
   }


   private static String[] getWords(String quote) {
      return quote.replaceAll("[^A-Za-z0-9 ]","").split(" ");
   }

   private static String[] getWords(String[] quote) {
      String fullQuote = "";
      for(String s: quote) {
         fullQuote += s;
      }
      fullQuote = fullQuote.replaceAll("[^A-Za-z0-9 ]","");
      return fullQuote.split(" ");
   }
   
   public static String classify(String input) {
      QueryVector q = new QueryVector(getWords(input), null);
      q.normalize(vectors);
      return classifyVector(q);
   }

   public static String classifyVector(Vector newV) {
      // calculate distances
      ArrayList<VectorDistance> vds = new ArrayList<>();
      for (Vector v: vectors.getAllVectors()) {
         double dist = distanceFunc.findDistance(newV, v, vectors);
         VectorDistance vd = new VectorDistance(v, dist);
         vds.add(vd);
         //System.out.println(vd.toString());
      }

      // get k nearest neighbors and have them vote
      Collections.sort(vds, new VectorComparator());
      Collections.reverse(vds);
      HashMap<String, Integer> counts = new HashMap<>();
      Integer maxCount = -1;
      String retClass = null;
      for(int i = 0; i < k; i++) {
         //System.out.println(i + " closest: " + vds.get(i));
         String classification = vds.get(i).getVector().classification;
         Integer currCount = counts.getOrDefault(classification, 0);
         if (++currCount > maxCount) {
            maxCount = currCount;
            retClass = classification;
         }
         counts.put(classification, currCount+1);
      }
      //newV.setClassification(retClass);
      return retClass;
   }


   public static void main(String[] args) {
      // uncomment this when we've properly processed the input
      /*try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("QuoteVectors")))) {
         vectors = (ArrayList<Vector>) is.readObject();
      } catch (Exception e) {
         System.out.println("Couldn't read vectors from file, recreating...");
         readVectors("all-seasons.csv");
      }*/
      vectors = readVectors("all-seasons.csv",false, 143666);
      distanceFunc = new CosineDistance();

      classify("fatass");
   }
}
