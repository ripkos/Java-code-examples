import java.util.*;

public class K {
    List<Flower> flowers;
    Point centroid;
    int id;
    public K(int num) {
        id=num;
        flowers = new LinkedList<>();
        centroid = new Point();
    }

    public double calculateSumDist() {
        double sum=0.0;
        for (Flower f : flowers) {
            sum+=f.distanceTo(centroid);
        }
        return sum;
    }

    public void recalculateCentroid(){
        double[] sum = new double[Point.dims];
        for (Flower f : flowers) {
            for (int i = 0; i < Point.dims; i++) {
                sum[i]+=f.cords[i];
            }
        }
        for (int i = 0; i < Point.dims; i++) {
            sum[i]/=flowers.size()+0.0;
        }
        centroid.cords=sum;
    }
    public String name(){
        return "K-"+id;
    }
    public String countAndName() {
        return name()+" licznosc - " + flowers.size() + " , suma kwadratow odleglosci - " + calculateSumDist();
    }


}
