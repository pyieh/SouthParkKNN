import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.io.Serializable;

public abstract class Vector implements Serializable {
   HashMap<String, Integer> words;
   String classification;
   HashMap<String, Double> normalizedVector;

   public Vector() {
      words = new HashMap<>();
      normalizedVector = new HashMap<>();
   }
   public Vector(String[] words, String classification) {
      this.classification = classification;
      this.words = new HashMap<>();
      for(String s: words) {
         add(s);
      }
      normalizedVector = new HashMap<>();
   }
   public void add(String s) {
      s = s.toLowerCase();
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

   public Integer getWordCount() {
      Integer len = 0;
      for (String s: words.keySet()) {
         len += words.get(s);
      }
      return len;
   }

   public Integer getDistinctWordCount() {
      return words.keySet().size();
   }

   public Set<String> getWords() { return words.keySet(); }

   public boolean contains(String s) { return words.containsKey(s); }

   public Integer size() {
      return words.size();
   }

   public Integer get(String s) {
      return words.getOrDefault(s, 0);
   }

   public int getHighestRawFrequency() {
      if (words.size() == 0)
         return 0;

      return Collections.max(words.values());
   }

   public double log2( double x ) {
      return Math.log10(x) / Math.log10(2.0);
   }

   // normalizes the frequency of each word using TF-IDF formula
   public abstract void normalize(QuoteCollection dc);

   public abstract double getNormalizedFrequency(String word);

   public double getL2Norm() {
      double sum = 0;
      for (String word: words.keySet()) {
         sum += Math.pow(getNormalizedFrequency(word), 2);
      }
      return Math.sqrt(sum);
   }
}
