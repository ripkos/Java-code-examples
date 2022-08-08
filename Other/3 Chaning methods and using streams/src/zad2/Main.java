/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad2;


/*<-- niezbędne importy */
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    // Lista destynacji: port_wylotu port_przylotu cena_EUR
    List<String> dest = Arrays.asList(
      "bleble bleble 2000",
      "WAW HAV 1200",
      "xxx yyy 789",
      "WAW DPS 2000",
      "WAW HKT 1000"
    );
    double ratePLNvsEUR = 4.30;
    List<String> result =
    /*<-- tu należy dopisać fragment
     * przy czym nie wolno używać żadnych własnych klas, jak np. ListCreator
     * ani też żadnych własnych interfejsów
     * Podpowiedź: należy użyć strumieni
     */
    dest.stream()
            .filter(n -> n.indexOf("WAW")==0)
            .map( w-> w = "to " + w.substring(4,4+3) + " - price in PLN:	" + (Double.parseDouble(w.substring(8))*ratePLNvsEUR))
            .collect(Collectors.toList());


    for (String r : result) System.out.println(r);
  }
}
