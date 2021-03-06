import java.util.ArrayList;

public class OkapiDistance implements DocumentDistance {
   
   private double k1 = 1.2, b = .75, k2 = 100.0;
   public OkapiDistance(double k1, double b, double k2) {
      this.k1 = k1;
      this.b = b;
      this.k2 = k2;
   } 
   
   public OkapiDistance() {} 

   public double findDistance(Vector query, Vector quote, QuoteCollection allQuotes) {
      double ret = 0;
      for (String word: query.getWords()) {
         double dfi = allQuotes.getQuoteFrequency(word); // number of documents that contain term ti
         double fij = quote.get(word); // number of times term ti appears in document dj
         double avgLen = allQuotes.getAvgDocLength(); // average document len
         double docLen = quote.getWordCount(); // length of document dj
         double fiq = query.get(word); // number of times term ti appears in query
         double x = Math.log((allQuotes.size() - dfi + .5) / (dfi + .5)); // inverse document frequency
         double y = ((k1 + 1) * fij) / ((k1 * (1 - b + b * (docLen/avgLen))) + fij); // word freq relative to doc size
         double z = ((k2 + 1)*fiq) / (k2 + fiq); //freq in query
         //System.out.println("For word [" + word + "] Okapi dist = " + x + " * " + y + " * " + z + " = " + (x*y*z));
         ret += x * y * z;
      }
      return ret;
   }
}
