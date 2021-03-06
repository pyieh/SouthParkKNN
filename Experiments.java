
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Experiments 
{
   //public static String characters[] = {"Stan", "Kyle", "Cartman", "Kenny", "Butters", "Randy"};
   public static String characters[] = {"Stan", "Kyle", "Cartman", "Kenny"};

   public static void main(String[] args) {

      String filename = "all-seasons.csv";
      double p_test = 0.2;
   
      ArrayList<String> k_results = run_k_experiments(filename, p_test, new OkapiDistance(1.4, 0.2, 50), new IntRange(1,21,2));

      System.out.println("\n\nK Experiment Results");
      for(String line : k_results)
         System.out.println(line);
      
      write_csv(k_results, "k_test_results.csv");
      
      //1.4,0.2,50.0
      /*
      ArrayList<String> okapi_results = run_okapi_experiments(filename, p_test, 3, 
                                                              new DoubleRange(1.0, 1.5, 0.1), // k1_range
                                                              new DoubleRange(0.0, 1.0, 0.2), // b_range
                                                              new DoubleRange(50, 100, 25)  // k2_range
                                                              );

      System.out.println("\n\nOkapi Experiment Results");
      for(String line : okapi_results)
         System.out.println(line);

      write_csv(okapi_results, "okapi_test_results.csv");
      */
   }

   public static QuoteCollection load_dataset(String filename) {
      System.out.println("Loading dataset...");
      HashSet<String> filter_chars = new HashSet<String>(Arrays.asList(characters));
      //QuoteCollection data = Knn.readVectors(filename, false, 1000000000).filter(250);
      //QuoteCollection data = Knn.readVectors(filename, false, 50000).filter(filter_chars);
      QuoteCollection data = Knn.readVectors(filename, false, 100000000).filter(filter_chars);
      Set<String> chars = data.getCharacters();
      System.out.println("Loaded " + data.size() + " data points for " + chars.size() + " characters");
      System.out.println("Characters");
      for(String c : chars)
         System.out.println("   " + c);
      System.out.println();
      return data;
   } 

   public static ArrayList<String> run_okapi_experiments(String filename, double p_test, int k, DoubleRange k1_range, DoubleRange b_range, DoubleRange k2_range) {
      // Load dataset
      QuoteCollection all_data = load_dataset(filename);
      
      // Split into training and testing sets
      System.out.println("Splitting dataset...");
      QuoteCollection train_data = new QuoteCollection();
      QuoteCollection test_data = new QuoteCollection();
      
      int train_size = (int)(all_data.size() * (1.0 - p_test));
      for(Vector v : all_data.getAllVectors()) {
         if(train_data.size() < train_size) 
            train_data.add(v);
         else
            test_data.add(v);
      }


      System.out.println("Training Data Size : " + train_data.size());
      System.out.println("Testing Data Size : " + test_data.size());

      String csv_header = "k1,b,k2,acc";
      ArrayList<String> results = new ArrayList<String>();
      results.add(csv_header);

      for(Double k1 : k1_range) {
         for(Double b : b_range) {
            for(Double k2 : k2_range) {
               System.out.println("Running k1 = " + k1 + ", b = " + b + ", k2 = " + k2 + " test");
                
               OkapiDistance dist = new OkapiDistance(k1, b, k2);
               Knn knn_model = new Knn(train_data, k, dist);
               double acc = test_knn(knn_model, test_data);


               System.out.println("Accuracy for k1 = " + k1 + ", b = " + b + ", k2 = " + k2 + " : " + acc);
               results.add(k1 + "," + b + "," + k2 + "," + acc);
            }  
         } 
      } 



      /*
      for(Double k1 : k1_range) {
         System.out.println("k1 = " + k1);
         for(Double b : b_range) {
            System.out.println("k1 = " + k1 + ", b = " + b);
            for(Double k2 : k2_range) {
               System.out.println("k2 = " + k2);
               System.out.println("Running k1 = " + k1 + ", b = " + b + ", k2 = " + k2 + " test");


               OkapiDistance dist = new OkapiDistance(k1, b, k2);
               Knn knn_model = new Knn(train_data, k, dist);
               double acc = test_knn(knn_model, test_data);
               results.add(k1 + "," + b + "," + k2 + "," + acc);
            }
         }
      }
      */

      return results;
   }   



   public static ArrayList<String> run_k_experiments(String filename, double p_test, OkapiDistance dist, IntRange k_range) {
      
      // Load dataset
      QuoteCollection all_data = load_dataset(filename);
      
      // Split into training and testing sets
      System.out.println("Splitting dataset...");
      QuoteCollection train_data = new QuoteCollection();
      QuoteCollection test_data = new QuoteCollection();
      
      int train_size = (int)(all_data.size() * (1.0 - p_test));
      for(Vector v : all_data.getAllVectors()) {
         if(train_data.size() < train_size) 
            train_data.add(v);
         else
            test_data.add(v);
      }
      
      System.out.println("Training Data Size : " + train_data.size());
      System.out.println("Testing Data Size : " + test_data.size());

      String csv_header = "k,acc";
      ArrayList<String> results = new ArrayList<String>();
      results.add(csv_header);
      // Run tests for all k values
      for(int k : k_range) {
         System.out.println("Running k = " + k + " test");
         Knn knn_model = new Knn(train_data, k, dist);

         double acc = test_knn(knn_model, test_data);
         
         System.out.println("Accuracy for k = " + k + " : " + acc);
         results.add(k + "," + acc);
      }

      return results;
      
   } 
   
   
   /** tests the given knn model over the given test data.
    * returns the accuracy
    */
   public static double test_knn(Knn knn_model, QuoteCollection test_data) {
      int correct = 0; 

      for(Vector v : test_data.getAllVectors()) {
         String pred = knn_model.classifyVector(v);

         //System.out.println("Pred " + pred + ": " + v);
         if((v == null && pred == null) || (pred != null && pred.equals(v.classification)))
            correct += 1;
      } 
      
      return correct / (double)test_data.size();
   } 


   public static void write_csv(ArrayList<String> lines, String filename) {
      try (FileWriter writer = new FileWriter(filename)) {
         for(String s : lines)
            writer.write(s + "\n");
         //vectors = (ArrayList<Vector>) is.readObject();
      } catch (Exception e) {
         System.out.println("aww shit");
         System.out.println(e);
         //System.out.println("Couldn't read vectors from file, recreating...");
         //readVectors("all-seasons.csv");
      }
  
   } 
   

   // Some range classes that just make passing around ranges of values to test with easier
   //
   private static class IntRange implements Iterable<Integer>, Iterator<Integer> {
      private int min, max, step, count;
      public IntRange(int min, int max, int step) {
         this.min = min;
         this.max = max;
         this.step = step;
         this.count = 0;
      }
      
      public IntRange(int min, int max) {
         this(min, max, 1);
      } 

      public Iterator<Integer> iterator() {
         return new IntRange(this.min, this.max, this.step); 
      } 

      public boolean hasNext() {
         return (this.min + this.step * this.count) <= this.max;
      }

      public Integer next() {
         return this.min + this.step * this.count++;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   } 

   private static class DoubleRange implements Iterable<Double>, Iterator<Double> {
      private double min, max, step;
      private int count;
      public DoubleRange(double min, double max, double step) {
         this.min = min;
         this.max = max;
         this.step = step;
         this.count = 0;
      }
      
      public DoubleRange(double min, double max) {
         this(min, max, 1);
      } 

      public Iterator<Double> iterator() {
         return new DoubleRange(this.min, this.max, this.step); 
      } 

      public boolean hasNext() {
         return (this.min + this.step * this.count) <= this.max;
      }

      public Double next() {
         double val = this.min + this.step * this.count++;
         
         // correct rounding errors to a precision of 5 decimal places
         val = (int)(val * 10e5) / 10e5;

         if(val > this.max)
            // catch overshoot
            return this.max;
         else
            return val;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   } 
   

}





