/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad1;


import java.io.*;
import java.util.*;
import java.util.function.Function;

public class Main {
  public static void main(String[] args) {

    /*<--
     *  definicja operacji w postaci lambda-wyrażeń:
     *  - flines - zwraca listę wierszy z pliku tekstowego
     *  - join - łączy napisy z listy (zwraca napis połączonych ze sobą elementów listy napisów)
     *  - collectInts - zwraca listę liczb całkowitych zawartych w napisie
     *  - sum - zwraca sumę elmentów listy liczb całkowitych
     */
    // flines
    Function< String , List<String>> flines = fname -> {
      List<String> list = new ArrayList<>();
      try {
        BufferedReader br = new BufferedReader(new FileReader(fname));

        String line = br.readLine();
        while (line!= null){
          list.add(line);
          line=br.readLine();
        }

      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return list;
    };
    // join
    Function< List<String>, String> join = a -> {
      String napis="";
      for (String str : a) {
        napis=napis.concat(str);
      }
      return napis;
    };
    // collectInts
    Function<String, List<Integer>> collectInts = a-> {
      List<Integer> intlist = new ArrayList<>();
      String str = a.replaceAll("[^\\d]", " ");
      str = str.trim();
      str = str.replaceAll(" +", " ");
      String[] all = str.split(" ");
      for(String s : all){
        intlist.add(Integer.valueOf(s));
      }
      return intlist;
    };
    //
    Function<List<Integer>, Integer> sum = a-> {
      int suma=0;
      for( int in : a){
        suma+=in;
      }
      return suma;
    };
    //END

    String fname = System.getProperty("user.home") + "/LamComFile.txt"; 
    InputConverter<String> fileConv = new InputConverter<>(fname);
    List<String> lines = fileConv.convertBy(flines);
    String text = fileConv.convertBy(flines, join);
    List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
    Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

    System.out.println(lines);
    System.out.println(text);
    System.out.println(ints);
    System.out.println(sumints);

    List<String> arglist = Arrays.asList(args);
    InputConverter<List<String>> slistConv = new InputConverter<>(arglist);  
    sumints = slistConv.convertBy(join, collectInts, sum);
    System.out.println(sumints);

  }
}
