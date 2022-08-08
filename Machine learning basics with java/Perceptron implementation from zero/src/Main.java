import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Wczytywanie danych

        ArrayList<Vector> trainingData = new ArrayList<>();
        ArrayList<Vector> testData = new ArrayList<>();
        int vectorsNum=0;
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(args[0]));
            String line = reader1.readLine();
            Vector.len=line.split("\t").length-1;
            while (line!=null){
                trainingData.add(new Vector(line.split("\t")));
                line = reader1.readLine();
                vectorsNum++;
            }
            reader1.close();
            BufferedReader reader2 = new BufferedReader(new FileReader(args[1]));
            String line2 = reader2.readLine();
            while (line2!=null){
                testData.add(new Vector(line2.split("\t")));
                line2 = reader2.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Maximum / minimum
        Vector.setMinMax(trainingData);
        // przetwaraznie danych
        Vector.normalize(trainingData);
        Vector.normalize(testData);
        // menu / dzialanie programu
        int k=3;
        Scanner scanner = new Scanner(System.in);
        States state = States.MAIN;
        while (state != States.EXIT) {
            switch (state){
                case MAIN:
                    // glowne menu
                    System.out.println("Wybierz tryb dzialania:");
                    System.out.println("1 - Wprowadz liczbe k dla algorytmu kNN; obecna liczba k to " + k);
                    System.out.println("2 - Wprowadz recznie wektor atrybutow");
                    System.out.println("3 - zakonczyc dzialanie programu");
                    String option = scanner.nextLine();
                    switch (option) {
                        case "1":
                            state=States.K_SELECT;
                            break;
                        case "2":
                            state=States.CUSTOM_DATA;
                            break;
                        default:
                            state=States.EXIT;
                            break;
                    }
                    break;
                case K_SELECT:
                    // wprowadzenie liczby k
                    System.out.println("Wprowadz liczbe k obecna lizcba k to "+ k);
                    System.out.print("Jesli checsz wyjsc wprowadz liczbe ujemna");
                    int tmp_k = Integer.parseInt(scanner.nextLine());
                    if(tmp_k<0){
                        state=States.MAIN;
                    }
                    else {
                        if (tmp_k>vectorsNum) {
                            k=vectorsNum;
                        }
                        else {
                            k=tmp_k;
                        }
                        Vector.forTestData(trainingData,testData,k);
                    }
                    break;
                case CUSTOM_DATA:
                    // wektor wlasny
                    System.out.println("Wprowadz wektor wlasny w postaci: \nwartosc1;wartosc2;wartosc3\nliczba wartosci musi byc dokladnie rowna " + Vector.len);
                    System.out.println("Jesli chesz wyjsc wprowadz liczbe ujemna");
                    String line = scanner.nextLine();
                    String[] parsedValues = line.split(";");
                    if  (parsedValues.length==1) {
                        state = States.MAIN;
                    }
                        else {
                            Vector tmpVector = new Vector(parsedValues);
                            Vector.normalize(tmpVector);
                            String tmpResult = Vector.forSingleVector(trainingData, tmpVector ,k);
                            System.out.println("Dla k="+k+" algorytm klasyfikuje dane jako " + tmpResult);
                        }

                    break;

            }
        }
    }

    public enum States {
        EXIT,
        MAIN,
        K_SELECT,
        CUSTOM_DATA,
    }
}

class Vector {
    //static
    static int len;
    static double[] min;
    static double[] max;

    public static void setMinMax(ArrayList<Vector> trainingData) {
        min = new double[len];
        max = new double[len];
        Vector first = trainingData.get(0);
        for (int i = 0; i < len; i++) {
            min[i]=first.values[i];
            max[i]=first.values[i];
        }
        for (Vector v : trainingData) {
            for (int i = 0; i < len; i++) {
                if(v.values[i]<min[i]) min[i]=v.values[i];
                if(v.values[i]>max[i]) max[i]=v.values[i];
            }
        }

    }

    public static void normalize(ArrayList<Vector> vectors) {
        for (Vector v : vectors) {
            normalize(v);
        }
    }

    public static void normalize(Vector v) {
        for (int i = 0; i < len ; i++) {
            v.values[i] =
                    (v.values[i] - min[i])
                / //-----------------------
                    (max[i]-min[i]);
        }

    }


    public static void forTestData(ArrayList<Vector> trainingData, ArrayList<Vector> testData, int k) {
        int success=0;
        int vector_num=0;
        for(Vector v : testData) {
            String result = forSingleVector(trainingData,v,k);
            if (result.equals(v.decision)) {
                success++;
            }
            vector_num++;
        }
        System.out.println("Liczba trafien to " + success + " z " + vector_num + " ; odsetek trafien to " + (double)success/vector_num);
    }

    public static String forSingleVector(ArrayList<Vector> trainingData, Vector targetVector, int k) {
        ArrayList<Vector> kNN = new ArrayList<>();
        for (Vector vector : trainingData) {
            int tmpIndex = 0;
            boolean modify=false;
            for (Vector tmp : kNN) {
                if (vector.distanceTo(targetVector) < tmp.distanceTo(targetVector)) {
                    modify=true;
                    break;
                }
                tmpIndex++;
            }
            if (modify || kNN.size()<k) {
                kNN.add(tmpIndex,vector);
                if (kNN.size()>k){
                    kNN.remove(k);
                }
            }

        }
        HashMap<String, Integer> decisionMap = new HashMap<>();
        for (Vector vector : kNN) {
            if (decisionMap.containsKey(vector.decision)) {
                decisionMap.replace(vector.decision,decisionMap.get(vector.decision)+1);
            }
            else{
                decisionMap.put(vector.decision,0);
            }
        }
        String str_decision="";
        int count_decision=-1;
        for (String key : decisionMap.keySet()){
            if (decisionMap.get(key)>count_decision) {
                str_decision=key;
                count_decision=decisionMap.get(key);
            }
            else {
                if (decisionMap.get(key)==count_decision) {
                    if(((int)(Math.random()*10))%2>0)
                        str_decision=key;
                }
            }
        }
        return str_decision;
    }

    private static double distanceTo(Vector v1, Vector v2) {
        double distance = 0.0;
        for (int i = 0; i < len; i++) {
            distance+=(v1.values[i]-v2.values[i])*(v1.values[i]-v2.values[i]);
        }
        return distance;
    }

    //non-static
    double[] values;
    String decision;
    public Vector (String[] values){
        this.values = new double[len];
        for (int i = 0; i < len ; i++) {
            this.values[i]=Double.parseDouble(values[i].replace(',','.').replaceAll(" ", ""));
        }
        if (values.length==len+1)
            decision=values[len].replaceAll(" ", "");
        else
            decision="";

    }

    public double distanceTo(Vector v2) {
        return distanceTo(this, v2);
    }
}