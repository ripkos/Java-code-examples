package zad1;

import java.util.NoSuchElementException;
import java.util.function.*;

public class Maybe<T> {
    private T obj;
    private Maybe(T o){
        this.obj=o;
    }
    public static<T> Maybe<T> of(T s) {
        return new Maybe<>(s);
    }
    public void ifPresent(Consumer<T> cons){
        if(this.isPresent()){
            cons.accept(obj);
        }
    }
    public<S> Maybe<S> map(Function<T,S> func){
        if (this.isPresent()){
            if (null==func.apply(obj))
                return new Maybe<>(null);
            else return Maybe.of(func.apply(obj));
        }
        else {
            return new Maybe<>(null);
        }
    }
    public T get() {
        if (!this.isPresent()){
            throw new NoSuchElementException("maybe is empty");
        }
        else {
            return obj;
        }
    }
    public boolean isPresent(){
        return obj != null;
    }
    public T orElse(T defVal){
        return this.isPresent() ? obj : defVal;
    }
    public Maybe<T> filter (Predicate<T> pred){
        if (!this.isPresent()) {
            return this;
        }
        else {
            if (pred.test(obj)){
                return this;
            }
            else{
                obj=null;
                return this;
            }
        }
    }
    @Override
    public String toString(){
        if (this.isPresent()){
            return "Maybe has value "+obj.toString();
        }
        else return "Maybe is empty";
    }
}
