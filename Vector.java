import java.util.ArrayList;

public class Vector {
   ArrayList<Integer> values;
   Integer classification;

   public Vector() {
      values = new ArrayList<>();
   }
   public void add(Integer i) {
      values.add(i);
   }
   public void setClass(Integer i) {
      classification = i;
   }
   public String toString() {
      return values.toString() + "{" + classification + "}";
   }

   public String toString2() {
      return "Vector" + values.toString();
   }

   public int size() {
      return values.size();
   }

   public Integer get(int i) {
      return values.get(i);
   }

}
