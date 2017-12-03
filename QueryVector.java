public class QueryVector extends Vector{
   public QueryVector(String[] words, String classification) {
      super(words, classification);
   }

   public void normalize(QuoteCollection collect) {
      for (String word: words.keySet()) {
         double val = 0.5 + 0.5*((double) words.get(word)) / (double)getHighestRawFrequency();
         double mult = (collect.getQuoteFrequency(word) == 0) ? 0 : log2((double)collect.size()  / (double)collect.getQuoteFrequency(word));
         normalizedVector.put(word, val * mult);
      }
   }

   // returns normalized frequency of the word
   public double getNormalizedFrequency(String word) {
      return normalizedVector.getOrDefault(word, 0.0);
   }
}