public class Flower extends Point {
    int id;
    String decision;
    public Flower(int id, String[] data) {
        super();
        this.id = id;
        for (int i = 0; i < Point.dims ; i++) {
            this.cords[i]=Double.parseDouble(data[i].replace(',','.').replaceAll(" ", ""));
        }
        if (data.length==Point.dims+1) {
            decision = data[Point.dims].replaceAll(" ", "");
            Init.CheckAdd(decision);
        }
        else
            decision="";
    }
}
