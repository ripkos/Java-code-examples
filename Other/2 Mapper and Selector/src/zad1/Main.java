/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad1;



import java.util.*;

public class Main {
  public Main() {
    List<Integer> src1 = src1 = Arrays.asList(1, 7, 9, 11, 12);
    System.out.println(test1(src1));

    List<String> src2 = Arrays.asList("a", "zzzz", "vvvvvvv" );
    System.out.println(test2(src2));
  }

  public List<Double> test1(List<Integer> src) {
    Selector <Integer> sel = new Selector<Integer>() {
      @Override
      public boolean test(Integer a) {
        return a<10;
      }
    };
    Mapper <Integer, Double> map = new Mapper<Integer,Double>() {
      @Override
      public Double modify(Integer a){
        return 0.0+a+10;
      }
    };
    return ListCreator.collectFrom(src).when(sel).mapEvery(map);
  }

  public List<Integer> test2(List<String> src) {
    Selector <String> sel = new Selector<String>() {
      @Override
      public boolean test(String a) {
        return a.length()>3;
      }
    };
    Mapper <String, Integer> map = new Mapper<String,Integer>() {
      @Override
      public Integer modify(String a){
        return a.length()+10;
      }
    };
    return ListCreator.collectFrom(src).when(sel).mapEvery(map);
  }

  public static void main(String[] args) {
    new Main();
  }
}
