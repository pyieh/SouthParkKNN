
import java.util.Scanner;
import java.util.InputMismatchException;


public class Main {
   
   public static void main(String[] args) {
      
      try(Scanner input_reader = new Scanner(System.in)) {
         // prompt user for k value
         int k = prompt_k(input_reader);

         // initialize KNN
         System.out.println("Loading KNN...");

         start_input_loop(/*pass in knn*/input_reader);

      }
   }
   

   public static void start_input_loop(/*pass in knn*/Scanner input_reader) {
      
      boolean running = true;
      while(running) { 
         System.out.print("\nEnter a string: ");

         if(input_reader.hasNextLine()) {
            String input = input_reader.nextLine().trim();
            
            if(input.toLowerCase().equals("!q")) {
               // handle exit cases
               System.out.println("Quitting...");
               running = false; 
            } else {
               // predict character as normal
               //System.out.println("Input String: " + input);
               
               // send to KNN
               String predicted = "Test Character";

               System.out.println("Prediction: " + predicted);
            }
         }
      } 
   } 
   

   public static int prompt_k(Scanner input_reader) {
      Integer k = null;
      while(k == null) {
         System.out.print("Enter a value for k [default 3]: ");
         if(input_reader.hasNext()) {
            try {
               k = input_reader.nextInt();
                
               if(k <= 0) {
                  // catching invalid k values
                  System.out.println("k must be greater than zero!") ;
                  k = null;
               }
            } catch(InputMismatchException e) {
               // catch non-integer input
               System.out.println("Invalid input, expected an integer!");
            } 

            // purge any leftover characters in the input stream
            input_reader.nextLine();
         } 
      }
      return k;
   }


}





