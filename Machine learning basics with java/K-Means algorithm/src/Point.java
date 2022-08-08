public class Point {
    public static int dims;
    public double[] cords;
    public Point() {
        cords = new double[dims];
    }

    public double distanceTo(Point p){
        double sum = 0.0;
        for(int i = 0; i<dims; i++){
            sum+=(cords[i]-p.cords[i])*(cords[i]-p.cords[i]);
        }
        return sum;
    }
}
