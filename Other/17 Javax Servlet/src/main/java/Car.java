public class Car {
    public int id;
    public String name;
    public String type;
    public Car(){

    }
    public Car(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String toHTMLrow(){
        return "<tr>"
                + "<td>"+name+"</td>"
                + "<td>"+type+"</td>"
                + "</tr>";
    }
}
