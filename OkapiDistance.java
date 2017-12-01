public class OkapiDistance {
   /*public double findDistance(TextVector query, TextVector document, DocumentCollection documents) {
      double k1 = 1.2, b = .75, k2 = 100.0;
      double ret = 0;
      for (String word: query.getKeys()) {
         double dfi = documents.getDocumentFrequency(word); // number of documents that contain term ti
         double fij = document.getRawFrequency(word); // number of times term ti appears in document dj
         double avgLen = documents.getAverageDocumentLength(); // average document len
         double docLen = document.getTotalWordCount(); // length of document dj
         double fiq = query.getRawFrequency(word); // number of times term ti appears in query
         double x = Math.log((documents.getSize() - dfi + .5) / (dfi + .5)); // inverse document frequency
         double y = ((k1 + 1) * fij) / ((k1 * (1 - b + b * (docLen/avgLen))) + fij); // word freq relative to doc size
         double z = ((k2 + 1)*fiq) / (k2 + fiq); //freq in query
         System.out.println("For word [" + word + "] Okapi dist = " + x + " * " + y + " * " + z + " = " + (x*y*z));
         ret += x * y * z;
      }
      return ret;
   }*/
}
