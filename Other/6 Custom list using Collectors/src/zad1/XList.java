package zad1;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/*
    of()

    ofChars(napis) - zwraca x-listę znaków napisu,
    ofTokens(napis, [ sep ]) - zwraca x-listę symboli napisu, rozdzielonych separatorami z sep (jesśi brak - to białymi znakami).

    union(dowolna_kolekcja)  -  zwraca  nową x-list z dołączaną do tej x-list  zawartością kolekcji,
    diff(dowolna_kolekcja) - zwraca x-list zawierającą te elementy tej x-list, które nie występują w kolekcji,
    unique() - zwraca nową x-list, która zawiera wszystkie niepowtarzające się elementy tej x-list
    combine() - zwraca x-listę list-kombinacji elementów z poszczególnych kolekcji, będących elementami tej x-listy
    collect(Function) - zwraca nową x-listę, której elemenatmi są wyniki funkcji stosowanej wobec elementów tej x-listy,
    join([sep]) - zwraca napis, będący połączeniem elementów tej x-listy, z ewentualnie wstawionym pomiędzy nie separatorem,
    forEachWithIndex(konsumer_z_dwoma argumentami: element, index) - do iterowania po liście z dostępem i do elementów i do ich indeksów.

 */



public class XList<T> extends ArrayList<T> {

    //Konstruktory + Metody statyczne
        //przecinki
    public XList(List<T> list){
        super(list);
    }
    public XList(T ... array) {
        super(Arrays.asList(array));
    }

    public static <T> XList<T> of(T ... array) {
        return new XList<>(array);
    }

        //z kolekcji
    public <C extends Collection<T>>XList(C collection){
        super(collection);

    }
    public static <S,C extends Collection<S>> XList<S> of(C set) {
        return new XList<>(set);
    }

    //Operowanie na stringach

    //chars
    public static <S extends  String> XList<S> charsOf(S string) {
        List<S> tmp = new ArrayList<S>();
        for(int i=0;i<string.length();i++){
            tmp.add((S) String.valueOf(string.charAt(i)));
        }
        return new XList<>(tmp);
    }

    //tokens
    public static <S extends  String> XList<S> tokensOf(S string) {
        return tokensOf(string,(S) " ");
    }
    public static <S extends  String> XList<S> tokensOf(S string, S token) {
        return new XList<>((List<S>) Arrays.asList(string.split(token)));
    }

    public <C extends Collection<T>>XList<T> union(C collection) {
        XList<T> toReturn = new XList<>(this);
        toReturn.addAll(collection);
        return toReturn;
    }

    public XList<T> union(T[] arr){
        return this.union(Arrays.asList(arr));
    }
        //dif unique

    public <C extends Collection<T>> XList<T> diff(C set) {
        XList<T> tor = new XList<T>();
        for (T obj: this) {
            if (!set.contains(obj))
                tor.add(obj);
        }
        return tor;
    }

    public XList<T> unique() {
        return new XList<T>(this.stream().distinct().collect(Collectors.toList()));
    }

    //[[a, b], [X, Y, Z], [1, 2]]
    //[[a, X, 1], [b, X, 1], [a, Y, 1], [b, Y, 1], [a, Z, 1], [b, Z, 1], [a, X, 2], [b, X, 2], [a, Y, 2], [b, Y, 2], [a, Z, 2], [b, Z, 2]]

    public <V> XList<XList<V>> combine() {
        XList<XList<V>> overlord = new XList<XList<V>>();
        for(T xlist : this){
            if(xlist instanceof List) {
                int currsize=overlord.size();
                //jesli overlord jest pusty
                if (currsize < 1) {
                    for (Object obj : (List) xlist) {  overlord.add(new XList<V>((V) obj)); }
                }
                //jesli overlord ma elementy
                else{
                    int element_count=0;
                    for (Object obj : (List) xlist){
                        element_count++;
                    }
                    int extra=-1+element_count;
                    //jesli sa potrzebne extra elementy
                    XList<XList<V>> tmp = new XList<XList<V>>();
                    while(extra>0){
                        tmp.addAll(overlord);
                        extra--;
                    }
                    overlord.addAll(tmp);
                    int current_index=0;
                    for(Object obj : (List) xlist){
                        for (int i = 0; i < currsize ; i++) {
                            XList<V> templist=new XList(overlord.get(current_index));
                            templist.add((V) obj);
                            overlord.set(current_index,templist);

                            current_index++;
                        }
                    }

                }
            }
        }


        return overlord;
    }
    //join
    public String join(){
        return this.join("");
    }
    public String join(String sep){
        String str="";
        for (T obj: this){
            if(str.equals("")){
                str=obj.toString();
            }
            else {
                str = str + sep + obj.toString();
            }
        }
        return str;
    }
    //collect
    public <V> XList<V> collect (Function<T,V> f){
        XList<V> tor = new XList<V>();
        for(T obj : this){
            tor.add(f.apply(obj));
        }
        return tor;
    }
    //forEachWithIndex

    public void forEachWithIndex(BiConsumer<T, Integer> consumer){

        for(int index=0;index<size();index++){
            consumer.accept(get(index),index);
        }

    }

}
