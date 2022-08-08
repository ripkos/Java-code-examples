import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Init {
    public static List<String> irisList;
    public static void main(String[] args) {
        irisList = new ArrayList<>();
        //Wczydywanie danych
        String dataPath = "D:\\Study\\SEMESTR 4\\NAI\\mpp4\\data\\iris_training.txt";
        List<Flower> flowers = new LinkedList<>();
        int id=1;
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(dataPath));
            String line = reader1.readLine();
            Point.dims=line.split("\t").length-1;
            while (line!=null){
                flowers.add(new Flower(id,line.split("\t")));
                line = reader1.readLine();
                id++;
                //vectorsNum++;
            }
            reader1.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Interakcja
        Scanner scanner = new Scanner(System.in);
        int k=1;
        System.out.println("Wprowadz k");
        k = scanner.nextInt();
        while(k>0) {

            //if (k>flowers.size()){
           //     System.out.println("Nie ma sensu wprowadzac k wieksze od liczby kwiatkow, obecnie to " + flowers.size());
           // }
            //else{
                //Inicjalizacja K
                List<K> ks = new LinkedList<>();
                for (int i = 1; i <= k; i++) {
                    ks.add(new K(i));
                }
                for (Flower f:
                     flowers) {
                    int decision = (int)(Math.random()*1000*ks.size()) % ks.size();
                    ks.get(decision).flowers.add(f);
                }
                boolean process = true;
                boolean centroid = true;
                int nothingChanged=0;
                while (process){
                    boolean hasChanged=false;
                    System.out.println("==================");
                    //Obliczanie centroid
                    if (centroid) {
                        hasChanged=true;
                        for (K tmp : ks) {
                            tmp.recalculateCentroid();
                            System.out.println(tmp.countAndName());
                        }
                    }
                    // Pomalowanie
                    else{
                        for (Flower f : flowers
                             ) {
                            double minDistance = 0;
                            K minDistanceK = null;
                            for (K tmp : ks) {
                                if(minDistanceK==null) {
                                    minDistanceK= tmp;
                                    minDistance=f.distanceTo(tmp.centroid);
                                }
                                else {
                                    double currentDistance = f.distanceTo(tmp.centroid);
                                    if(currentDistance<=minDistance){
                                        if (currentDistance==minDistance){
                                            double rndtmp = Math.random();
                                            if (rndtmp > 0.5) {
                                                minDistanceK=tmp;
                                            }

                                        }
                                        else {
                                            minDistance=currentDistance;
                                            minDistanceK=tmp;

                                        }
                                    }
                                }
                            }
                            for (K tmp : ks){
                                if (tmp!=minDistanceK){
                                    tmp.flowers.remove(f);
                                }
                                else {
                                    if(!tmp.flowers.contains(f)){
                                        hasChanged=true;
                                        tmp.flowers.add(f);
                                    }
                                }
                            }
                        }
                    }
                    centroid=!centroid;

                    if(!hasChanged){
                        nothingChanged++;
                    }
                    if(nothingChanged>flowers.size()){
                        process=false;
                    }
                }
                System.out.println("==================");
                System.out.println("==================");
                System.out.println("Podsumowanie:");
                System.out.println();
                //Wyswietlanie entropii oraz licznosc
                for(K tmp : ks) {
                    System.out.println(tmp.name()+":");
                    HashMap<String, Integer> map = new HashMap<>();
                    for (String iris : irisList) {
                        map.put(iris,0);
                    }
                    for (Flower f : tmp.flowers){
                        int old = map.get(f.decision);
                        map.replace(f.decision,old+1);
                    }
                    for(String s : map.keySet()) {
                        System.out.println(s + " " + map.get(s));
                    }
                    System.out.println("Razem - " + tmp.flowers.size());
                    double entropy = 0.0;
                    for(String s : map.keySet()) {

                        double ratio = ((double)(map.get(s)))/(double)tmp.flowers.size();
                        if(ratio>0)
                        entropy+=ratio*log2(ratio);
                    }
                    entropy=-entropy;
                    System.out.println("Entropia = " + entropy);
                    System.out.println("==================");
                }

            //}

            //nastepny k
            System.out.println("Wprowadz k");
            k = scanner.nextInt();
        }
        System.out.println("koniec");
    }
    public static double log2(double N)
    {
        double base=Math.log(N);
        double sub = Math.log(2);
        return base/sub;

    }

    public static void CheckAdd(String iris) {
        if (!Init.irisList.contains(iris)) {
            irisList.add(iris);
        }
    }
}
