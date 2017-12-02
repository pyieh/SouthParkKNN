
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

import java.util.Iterator;
import java.util.ArrayList;

public class Experiments 
{
   public static void main(String[] args) {
      /*
      String line_a = "fdsa,fdsa,fdsa";
      String line_b = "qwer,weqer,qwer";
      String line_c = "uio,uio,uio";
      
      ArrayList<String> lines = new ArrayList<String>();
      lines.add(line_a);
      lines.add(line_b);
      lines.add(line_c);
      
      write_csv(lines, "csv_test.txt");
      */

      run_k_experiments("all-seasons.csv", 0.5, new IntRange(1,5));
   }
   
   public static void run_k_experiments(String filename, double p_test, IntRange k_range) {
      
      // Load dataset
      System.out.println("Loading dataset...");
      QuoteCollection all_data = Knn.readVectors(filename, false, 200);
      
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
      
      OkapiDistance dist = new OkapiDistance();

      ArrayList<String> results = new ArrayList<String>();
      // Run tests for all k values
      for(int k : k_range) {
         System.out.println("Running k = " + k + " test");
         Knn knn_model = new Knn(train_data, k, dist);

         double acc = test_knn(knn_model, test_data);
      
         results.add(k + "," + acc);
      }
      
      System.out.println("\n\nFinal Results");
      for(String line : results)
         System.out.println(line);

   } 
   
   
   /** tests the given knn model over the given test data.
    * returns the accuracy
    */
   public static double test_knn(Knn knn_model, QuoteCollection test_data) {
      int correct = 0; 

      for(Vector v : test_data.getAllVectors()) {
         String pred = knn_model.classify(v);

         if(pred.equals(v.classification))
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
         return this; 
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
         return this; 
      } 

      public boolean hasNext() {
         return (this.min + this.step * this.count) <= this.max;
      }

      public Double next() {
         return this.min + this.step * this.count++;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   } 




}





