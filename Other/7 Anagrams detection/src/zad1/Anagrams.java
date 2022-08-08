/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad1;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Anagrams {
    Map<String, List<String>> map;

    public Anagrams(String path) throws FileNotFoundException {
        map=new HashMap<String, List<String>>();
        Scanner scan = new Scanner(new File(path));
        while(scan.hasNext()) {
            String current=scan.next();
            char[] chars =current.toCharArray();
            Arrays.sort(chars);
            String key=new String(chars);
            if(map.containsKey(key)){
                map.get(key).add(current);
            }
            else{
                List<String> tmplist = new ArrayList<>();
                tmplist.add(current);
                map.put(key,tmplist);
            }
        }
    }

    public List<List<String>> getSortedByAnQty() {
        List<List<String>> tmplist = new ArrayList<>();
        map.forEach((key,value)->tmplist.add(value));
        tmplist.sort((o1, o2) -> {
            if(o1.size()>=o2.size()){
                if(o1.size()>o2.size()){
                    return -1;
                }
                else{
                    int minSize=Math.min(o1.get(0).length(), o2.get(0).length());
                    String tmp1=o1.get(0);
                    String tmp2=o2.get(0);
                    for (int i = 0; i < minSize ; i++) {
                        if(tmp1.charAt(i)>=tmp2.charAt(i)){
                            if(tmp1.charAt(i)>tmp2.charAt(i)){
                                return -1;
                            }
                        }
                        else return 1;
                    }
                    return o1.get(0).length() > o2.get(0).length() ? 1 : -1;
                }
            }
            else return 1;
        });
        return tmplist;
    }

    public String getAnagramsFor(String next) {
        char[] chars =next.toCharArray();
        Arrays.sort(chars);
        String key=new String(chars);
        List<String>list = map.getOrDefault(key, null);
        if(list!=null){
            list.remove(next);
        }
        return next+": "+list;
    }
}  
