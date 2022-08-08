/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad1;
import java.util.*;

public interface Mapper<T, S> { // Uwaga: interfejs musi być sparametrtyzowany
    public abstract S modify (T a);
}  
