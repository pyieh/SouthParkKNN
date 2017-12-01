import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Knn {
   private QuoteCollection vectors;
   private Integer k = 3;
   //private Integer readUpTo = 100;
   private OkapiDistance okapi_dist;

   public Knn(String filename, int k, OkapiDistance dist) {
      this.k = k;
      this.vectors = readVectors(filename, true, 100);
      this.okapi_dist = dist;
   } 

   public static QuoteCollection readVectors(String filename, boolean write_vectors, int readUpTo) {
      QuoteCollection vectors = new QuoteCollection();
      try {
         FileReader fr = new FileReader(filename);
         BufferedReader br = new BufferedReader(fr);
         String line;
         int i = 0;
         while ( (line = br.readLine()) != null && i < readUpTo) {
            if (i%2 == 1) { // only every other line contains information
               System.out.println(i + ":" + line);
               String[] words = line.split(",");
               Integer season = Integer.parseInt(words[0]);
               Integer episode = Integer.parseInt(words[1]);
               String character = words[2];
               words = getWords(Arrays.copyOfRange(words, 3, words.length));
               Vector v = new Vector(words, character);
               vectors.add(v);
            }
            i++;
         }
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

   private static void printArray(String[] arr) {
      System.out.print("[");
      for(String s: arr) {
         System.out.print(s + ", ");
      }
      System.out.println("]");
   }


   private static String[] getWords(String quote) {
      return getWords(quote.split(" "));
   }

   private static String[] getWords(String[] quote) {
      String fullQuote = "";
      for(String s: quote) {
         fullQuote += s;
      }
      fullQuote = fullQuote.replaceAll("[^A-Za-z0-9 ]","");
      return fullQuote.split(" ");
   }

   /*private static double l2Norm(Vector v1, Vector v2) {
      double ret = 0;
      System.out.println("For "+ v2.toString2() + ":");
      if (v1.size() != v2.size())
         return -1;
      System.out.print("sqrt(");
      for(int i = 0; i < v1.size(); i++) {
         if (i != v1.size() - 1)
            System.out.print("(" + v1.get(i) + "-" + v2.get(i) + ")^2 + ");
         else
            System.out.print("(" + v1.get(i) + "-" + v2.get(i) + ")^2");
         ret += Math.pow(v1.get(i)-v2.get(i), 2);
      }
      System.out.println(")=" + Math.pow(ret,1.0/2));
      return Math.pow(ret, 1.0/2);
   }*/
   
   public String classify(String input) {
      return classifyVector(new Vector(getWords(input), null));
   }
   

   public String classifyVector(Vector newV) {
      // calculate distances
      ArrayList<VectorDistance> vds = new ArrayList<>();
      for (Vector v: this.vectors.getAllVectors()) {
         double dist = this.okapi_dist.findDistance(newV, v, this.vectors);
         VectorDistance vd = new VectorDistance(v, dist);
         vds.add(vd);
         //System.out.println(vd.toString());
      }

      // get k nearest neighbors and have them vote
      Collections.sort(vds, new VectorComparator());
      HashMap<String, Integer> counts = new HashMap<>();
      Integer maxCount = -1;
      String retClass = null;
      for(int i = 0; i < this.k; i++) {
         System.out.println(i + " closest: " + vds.get(i));
         String classification = vds.get(i).getVector().classification;
         Integer currCount = counts.getOrDefault(classification, 0);
         if (++currCount > maxCount) {
            maxCount = currCount;
            retClass = classification;
         }
         counts.put(classification, currCount+1);
      }
      newV.setClassification(retClass);
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
      //readVectors("all-seasons.csv");

      // Age = middle age, Income = middle class
      /*Vector newVector = new Vector();
      newVector.add(2);
      newVector.add(2);

      classifyVector(newVector);
      System.out.println(newVector);
      */
   }
}
