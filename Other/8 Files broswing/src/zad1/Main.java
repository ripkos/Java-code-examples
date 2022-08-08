/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad1;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) throws Exception {
    //przetwarzanie
    URL url = new URL("http://wiki.puzzlers.org/pub/wordlists/unixdict.txt");
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));


    Map<String, List<String>> anagrams = reader.lines()
            .collect(Collectors.groupingBy(Main::anagram));
    anagrams.entrySet().stream().filter(entry -> entry.getValue().size()==Collections.max(anagrams.keySet())

    max.forEach((k,v)-> System.out.println(k+" "+v));


  }
  public static String anagram(String word) {
    char[] chars = word.toCharArray();
    Arrays.sort(chars);
    return new String(chars);
  }
}
