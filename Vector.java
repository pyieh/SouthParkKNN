import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Vector {
   HashMap<String, Integer> words;
   String classification;

   public Vector() {
      words = new HashMap<>();
   }
   public Vector(String[] words, String classification) {
      this.classification = classification;
      this.words = new HashMap<>();
      for(String s: words) {
         add(s);
      }
   }
   public void add(String s) {
      Integer count = words.getOrDefault(s,0);
      words.put(s,count+1);
   }
   public void setClassification(String i) {
      classification = i;
   }
   public String toString() {
      String ret = classification + ":{";
      for (String s: words.keySet()) {
         ret += s + ":" + words.get(s) + ",";
      }
      return ret;
   }

   /*public String toString2() {
      return "Vector" + values.toString();
   }*/

   public Integer getWordCount() {
      Integer len = 0;
      for (String s: words.keySet()) {
         len += words.get(s);
      }
      return len;
   }

   public Set<String> getWords() { return words.keySet(); }

   public boolean contains(String s) { return words.containsKey(s); }

   public Integer size() {
      return words.size();
   }

   public Integer get(String s) {
      return words.get(s);
   }

}
