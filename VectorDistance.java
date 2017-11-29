public class VectorDistance {
   Vector v;
   double dist;

   public VectorDistance(Vector v, double dist) {
      this.v = v;
      this.dist = dist;
   }
   public Vector getVector() {
      return v;
   }

   public String toString() {
      return v.toString() + ":" + dist;
   }
}
