import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Knn {
   static ArrayList<Vector> vectors = new ArrayList<>();
   static Integer k = 3;

   public static void readVectors(String filename) {
      try {
         FileReader fr = new FileReader("./src/" + filename);
         BufferedReader br = new BufferedReader(fr);
         String line;

         while ( (line = br.readLine()) != null) {

            String[] attributes = line.split(",");
            Vector v = new Vector();
            for (int i = 0; i < attributes.length; i++) {
               if (i == attributes.length - 1)
                  v.setClass(Integer.parseInt(attributes[i]));
               else
                  v.add(Integer.parseInt(attributes[i]));
            }
            vectors.add(v);
            //System.out.println("Adding item:" + v.toString());
         }
      }
      catch (Exception e) {
         System.out.println(e);
         System.exit(-1);
      }
   }

   private static double l2Norm(Vector v1, Vector v2) {
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
   }

   public static Integer classifyVector(Vector newV) {
      // calculate distances
      ArrayList<VectorDistance> vds = new ArrayList<>();
      for (Vector v: vectors) {
         double dist = l2Norm(newV, v);
         VectorDistance vd = new VectorDistance(v, dist);
         vds.add(vd);
         //System.out.println(vd.toString());
      }

      // get k nearest neighbors and have them vote
      Collections.sort(vds, new VectorComparator());
      HashMap<Integer, Integer> counts = new HashMap<>();
      Integer maxCount = -1;
      Integer retClass = -1;
      for(int i = 0; i < k; i++) {
         System.out.println(i + " closest: " + vds.get(i));
         Integer classification = vds.get(i).getVector().classification;
         Integer currCount = counts.getOrDefault(classification, 0);
         if (++currCount > maxCount) {
            maxCount = currCount;
            retClass = classification;
         }
         counts.put(classification, currCount+1);
      }
      newV.setClass(retClass);
      return retClass;
   }

   public static void main(String[] args) {
      readVectors("input.txt");

      // Age = middle age, Income = middle class
      Vector newVector = new Vector();
      newVector.add(2);
      newVector.add(2);

      classifyVector(newVector);
      System.out.println(newVector);
   }
}
