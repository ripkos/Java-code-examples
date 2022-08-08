/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad1;
import java.util.*;

public class ListCreator<T> { // Uwaga: klasa musi być sparametrtyzowana
    private List<T> list;
    private ListCreator(){
        this.list=new ArrayList<>();
    }
    public static <T> ListCreator<T> collectFrom(List<T> a){
        ListCreator<T> result = new ListCreator<>();
        result.list.addAll(a);
        return result;
    }

    public ListCreator<T> when(Selector<T> check) {
        list.removeIf(current -> !check.test(current));
        return this;
    }

    public <S>List<S> mapEvery( Mapper<T, S> map){
        List<S> returnlis = new ArrayList<>();
        for (T current : list){
            returnlis.add(map.modify(current));
        }
        return returnlis;
    }
}  
