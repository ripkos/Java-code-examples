/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad1;
import java.util.*;

public interface Selector<T> { // Uwaga: interfejs musi być sparametrtyzowany
    public abstract boolean test(T a);
}  
