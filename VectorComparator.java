import java.util.Comparator;

public class VectorComparator implements Comparator<VectorDistance> {
   @Override
   public int compare(VectorDistance v1, VectorDistance v2) {
      if (v1.dist == v2.dist)
         return 0;
      else if (v1.dist < v2.dist)
         return -1;
      else
         return 1;
   }
}
