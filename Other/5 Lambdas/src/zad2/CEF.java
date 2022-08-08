package zad2;
import java.io.*;
import java.util.function.*;

@FunctionalInterface
interface CEF<T,R> extends Function<T,R> {

    abstract R checkedApply(T arg) throws Exception;

    default R apply(T arg) {
        try{
            return checkedApply(arg);
        }  catch (Exception exc){
            throw new RuntimeException(exc);
        }


    }
}
