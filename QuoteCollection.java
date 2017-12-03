import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class QuoteCollection implements Serializable {
   HashMap<String, HashSet<Vector>> quotes; // mapping of Character -> Quotes said by that character
   Integer totalWordCount = 0;
   HashMap<String, Integer> wordFrequency = new HashMap<>(); // mapping of Word -> Number of quotes that contain this word
   Integer size = 0;
   HashSet<Vector> vectors;

   public QuoteCollection() {
      quotes = new HashMap<>();
      vectors = new HashSet<>();
   }

   public void add(Vector v) {
      add(v, v.classification);
   }

   public void add(Vector v, String character) {
      // add vector v to set of all quotes
      vectors.add(v);

      // add Vector v to that corresponding character's set of quotes
      HashSet<Vector> set = quotes.getOrDefault(character, new HashSet<>());
      set.add(v);
      quotes.put(character, set);

      // update total word count over all Vectors
      totalWordCount += v.getWordCount();

      // update number of quotes count
      size++;

      // update document frequency for each word in vector
      for (String s: v.getWords()) {
         s = s.toLowerCase();
         Integer count = wordFrequency.getOrDefault(s, 0);
         count += v.get(s);
         wordFrequency.put(s, count);
      }
   }

   // helper for filter() when adding sets of quotes for a character
   private void add(HashSet<Vector> set, String character) {
      for(Vector v: set) {
         add(v,character);
      }
   }

   // return number of quotes that contain String s
   public Integer getQuoteFrequency(String s) {
      return wordFrequency.getOrDefault(s,0);
   }

   // filters out any Characters that don't meet minOccurence count for number of quotes
   public QuoteCollection filter(Integer minOccurence) {
      QuoteCollection qc = new QuoteCollection();
      for (String character: this.quotes.keySet()) {
         if (quotes.get(character).size() > minOccurence) {
            qc.add(quotes.get(character), character);
         }
      }
      return qc;
   }

   // filters out any Characters that aren't in the given character set
   public QuoteCollection filter(HashSet<Character> characters) {
      QuoteCollection qc = new QuoteCollection();
      for (String character: this.quotes.keySet()) {
         if (characters.contains(character)) {
            qc.add(quotes.get(character), character);
         }
      }
      return qc;
   }

   public HashSet<Vector> getAllVectors() {
      return vectors;
   }

   // number of quotes
   public Integer size() {
      return size;
   }

   public double getAvgDocLength() {
      return ((double)totalWordCount) / size();
   }

   public void normalize(QuoteCollection qc) {
      for (Vector v: vectors) {
         v.normalize(qc);
      }
   }
}
