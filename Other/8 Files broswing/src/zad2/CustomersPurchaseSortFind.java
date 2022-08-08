/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad2;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CustomersPurchaseSortFind {
    private List<Purchase> list= new ArrayList<>();
    public void readFile(String fname)   {
        Scanner scan = null;
        try {
            scan = new Scanner(new File(fname));
            while(scan.hasNextLine()){
            list.add(new Purchase(scan.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void showSortedBy(String tag) {
        System.out.println(tag);
        switch(tag){
            case "Nazwiska":
                sortByName();
                break;
            case "Koszty":
                sortByPrice();
                break;
        }
        System.out.println();
    }
    private void sortByName(){
        list.sort(((o1, o2) -> {
            if(o1.getNazwisko().compareToIgnoreCase(o2.getNazwisko())>-1){
                if(o1.getNazwisko().compareToIgnoreCase(o2.getNazwisko())>0)
                    return 1;
                else{
                    if(o1.getIdNumber()<o2.getIdNumber())
                        return -1;
                    else{
                        return 1;
                    }
                }
            }
            else{
                return -1;
            }
        }));
        print();
    }


    private void sortByPrice(){
        list.sort((o1, o2) -> {
            if(o1.koszt()>=o2.koszt()){
                if(o1.koszt()==o2.koszt()){
                    if(o1.getIdNumber()<o2.getIdNumber())
                        return -1;
                    else{
                        return 1;
                    }
                }
                else{
                    return -1;
                }
            }
            else{
                return 1;
            }
        });
        list.forEach(obj -> {
            System.out.println(obj.getLine()+" (koszt: "+obj.koszt()+")");
        });

    }
    public void showPurchaseFor(String id) {
        System.out.println("Klient "+id);
        list.stream().filter(obj -> {
            return obj.getId().equals(id);
        }).collect(Collectors.toList()).forEach(obj -> System.out.println(obj.getLine()));
        System.out.println();
    }
    private void print(){
        list.forEach(obj -> {
            System.out.println(obj.getLine());
        });
    }
}
