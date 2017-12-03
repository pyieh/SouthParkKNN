import java.util.Collection;

public class CosineDistance implements DocumentDistance {
   /*public double findDistance(TextVector query, TextVector document, DocumentCollection documents) {
      QueryVector q = (QueryVector)query;
      DocumentVector d = (DocumentVector)document;
      if ((document.getTotalWordCount() == 0) || (query.getTotalWordCount() == 0))
         return 0.0;
      if ((document.getDistinctWordCount() == 0) || (query.getDistinctWordCount() == 0))
         return 0.0;

      if (( q.getL2Norm() * d.getL2Norm() ) == 0)
         return 0;
      return dotProduct(q, d) / ( q.getL2Norm() * d.getL2Norm() );
   }*/
   public double findDistance(Vector query, Vector quote, QuoteCollection allQuotes) {
      if (query.getDistinctWordCount() == 0 || quote.getDistinctWordCount() == 0) {
         return 0.0;
      }
      else {
         double top = 0.0;
         double bottom = 0.0;
         Collection<String> queries = query.getWords();
         for (String s : queries) {
            if (quote.getNormalizedFrequency(s) == 0) {
               top += 0;
            }
            else {
               top += query.getNormalizedFrequency(s) * quote.getNormalizedFrequency(s);
            }
         }

         bottom += (query.getL2Norm() * quote.getL2Norm());

         if (top == 0.0 || bottom == 0.0) {
            return 0.0;
         }
         else {
            return top/bottom;
         }
      }
   }

   private double dotProduct (Vector query, Vector document) {
      double sum = 0;
      for (String word: query.getWords()) {
         sum += query.getNormalizedFrequency(word) * document.getNormalizedFrequency(word);
      }
      return sum;
   }
}
