/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad2;


public class Purchase {
    private String line;
    private String id;
    private String nazwisko;
    private String towar;
    private double cena;
    private double ile;

    public Purchase(String line){
        this.line=line;
        String[] tmp=line.split(";");
        id=tmp[0];
        nazwisko=tmp[1];
        towar=tmp[2];
        cena=Double.parseDouble(tmp[3]);
        ile=Double.parseDouble(tmp[4]);
    }

    public double koszt(){
        return cena*ile;
    }

    public int getIdNumber() {
        return Integer.parseInt(id.substring(1));
    }

    public String getLine() {
        return line;
    }

    public String getNazwisko() {
        return nazwisko.split(" ")[0];
    }

    public String getId() {
        return id;
    }
}
