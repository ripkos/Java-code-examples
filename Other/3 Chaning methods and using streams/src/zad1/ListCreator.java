package zad1;

import java.util.*;
import java.util.function.*;

public class ListCreator<T> {
    private List<T> list;
     static<T> ListCreator<T> collectFrom(List<T> a){
        ListCreator<T> nowe = new ListCreator<>();
        nowe.list=new ArrayList<>();
        nowe.list.addAll(a);
        return nowe;
     }
     ListCreator<T> when (Predicate<T> filter){
         List<T> newlist= new ArrayList<>();
         for(T obj : this.list){
             if(filter.test(obj)){
                 newlist.add(obj);
             }
         }
         this.list=newlist;
         return this;
     }
     List<T> mapEvery (Function<T,T> w){
         List<T> newlist= new ArrayList<>();
         for(T obj : this.list){
             newlist.add(w.apply(obj));
         }
         this.list=newlist;
         return this.list;
     }

}
