import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

public class mpp2 {
    static int times = 100_000;
    public static List<String> irisList;
    public static void main(String[] args) {
        // Wczytywanie danych
        String trainingPath = args[0];
        String testPath = args[1];
        ArrayList<Vector> trainingData = new ArrayList<>();
        ArrayList<Vector> testData = new ArrayList<>();
        irisList = new ArrayList<>();
        //int vectorsNum=0;
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(trainingPath));
            String line = reader1.readLine();
            Vector.len=line.split("\t").length-1;
            while (line!=null){
                trainingData.add(new Vector(line.split("\t")));
                line = reader1.readLine();
                //vectorsNum++;
            }
            reader1.close();
            BufferedReader reader2 = new BufferedReader(new FileReader(testPath));
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
        ArrayList<Perceptron> perceptrons = new ArrayList<>();
        for (String name : irisList) {
            //TODO TEST
            if(name.equals("Iris-versicolor")) {
                perceptrons.add(new Perceptron(name,0.5,0.9));
            }
            else
            perceptrons.add(new Perceptron(name));
        }
        // Training
        int times = mpp2.times;
        for (int i = 0; i < times; i++) {
            for (Vector v : trainingData) {
                for (Perceptron p : perceptrons) {
                    p.train(v);
                }
            }
        }
        System.out.println("//Wyniki treningu:");
        for (Perceptron p : perceptrons) {
            System.out.println("Perceptron " + p.iris + ", wagi:");
            for (int i = 0; i < p.weights.length; i++) {
                System.out.println(p.weights[i]);
            }
            System.out.println(p.threshold);
        }
        int count=0;
        int good=0;
        int randomCount=0;
        int randomGood=0;
        // Test
        for (Vector v : testData) {
            List<Perceptron> tmp = new ArrayList<>();
            int activated_count=0;
            for (Perceptron p :perceptrons) {
                int y = p.activate(v);
                if ( (y == 1 && !v.decision.equals(p.iris)) || (y == 0 && v.decision.equals(p.iris))) {
                    if (y == 1) {
                        System.out.println("Perceptron " + p.iris + " zle rozpoznal wektor:\n" + v.toString());
                    }
                    if (y == 0) {
                        System.out.println("Perceptron " + p.iris + " nie rozpoznal wektor:\n" + v.toString());
                    }
                    p.errorCounts++;
                    //System.out.println("Suma wag = " + p.suma(v));
                }
                if (y>0) {
                    activated_count++;
                    tmp.add(p);
                }
            }
            if(activated_count==0) {
                int d = (int)(Math.random()*10)%irisList.size();
                String randomResult = irisList.get(d);
                if (randomResult.equals(v.decision)) {
                    randomGood++;
                    good++;
                }
                randomCount++;
            }
            if(activated_count==1 && tmp.get(0).iris.equals(v.decision)) {
                good++;
            }
            if (activated_count>1) {
                int d = (int)(Math.random()*10)%activated_count;
                String randomResult = tmp.get(d).iris;
                if (randomResult.equals(v.decision)) {
                    randomGood++;
                    good++;
                }
                randomCount++;
            }

            count++;
        }
        System.out.println("Laczna liczba przypadkow - " + count);
        for (Perceptron p : perceptrons) {
            System.out.println(p.iris + ": " + (count-p.errorCounts) + " trafien, procent - " + ((double)(count-p.errorCounts)/count));
        }
        System.out.println("Wynik eksperymentu: " + good + " trafien (w tym " + randomGood + " przypadkowych), " + randomCount + " rzutow moneta, procent trafien = " + (double)(good)/count);
        System.out.println("Wprowadz wektor wlasny w postaci: \nwartosc1;wartosc2;wartosc3\nliczba wartosci musi byc dokladnie rowna " + Vector.len);
        System.out.println("Jesli chesz wyjsc wprowadz 'exit'");
        Scanner scanner = new Scanner(System.in);

        boolean running=true;
        while (running) {
            String line = scanner.nextLine();
            if ( line.equals("exit") ) {
                running=false;
            }
            else {
                String[] parsedValues = line.split(";");
                Vector v = new Vector(parsedValues);
                List<Perceptron> tmp = new ArrayList<>();
                for (Perceptron p: perceptrons) {
                    System.out.println(p.iris + ": " + p.activate(v));
                    if(p.activate(v)>0){
                        tmp.add(p);
                    }
                }
                String Result;

                if(tmp.size()==1) {
                    Result=tmp.get(0).iris;
                }
                else {
                    if (tmp.size()!=0) {
                        int d = (int)(Math.random()*10)%tmp.size();
                        Result = tmp.get(d).iris + " (rzut moneta)";
                    }
                    else{
                        int d = (int)(Math.random()*10)%irisList.size();
                        Result = irisList.get(d) + " (rzut moneta)";
                    }
                }
                System.out.println("Decyzja: " + Result);
            }
        }

        //.
    }

    public static void CheckAdd(String iris) {
        if (!mpp2.irisList.contains(iris)) {
            irisList.add(iris);
        }
    }
}

class Perceptron {
    public double[] weights;
    public double threshold;
    public String iris;
    public int errorCounts;
    //0.25
    //0.1
    //70000
    double a;
    double b;
    public Perceptron (String name) {
        iris=name;
        weights=new double[Vector.len];
        for (int i = 0; i < weights.length;i++) {
            //weights[i]=Math.random();
            weights[i]=1;
        }
        threshold=-1;
        //threshold=Math.random();
        errorCounts=0;
        a =0.25;
        b =0.1;
    }

    public Perceptron(String name, double a, double b) {
        iris=name;
        weights=new double[Vector.len];
        for (int i = 0; i < weights.length;i++) {
            //weights[i]=Math.random();
            weights[i]=1;
        }
        threshold=-1;
        //threshold=Math.random();
        errorCounts=0;
        this.a =a;
        this.b =b;

    }

    public double suma(Vector data) {
        double sum=threshold;
        for (int i =0; i < weights.length; i++) {
            sum+=weights[i]*data.values[i];
        }
        return sum;
    }

    public int activate (Vector data){
        double sum=threshold;
        for (int i =0; i < weights.length; i++) {
            sum+=weights[i]*data.values[i];
        }
        return sum >= 0 ? 1 : 0;
    }

    public void train (Vector data) {

        int d = iris.equals(data.decision) ? 1 : 0;
        int y = activate(data);
        for (int i = 0; i < data.values.length ; i++) {
            weights[i]=weights[i]+(d-y)*data.values[i]*(a);
        }
        threshold=threshold+(d-y)*(-1)*(b);
    }
}


class Vector {
    static int len;
    /*
    static double[] min;
    static double[] max;

     */
    double[] values;
    String decision;
    public Vector (String[] values){
        this.values = new double[len];
        for (int i = 0; i < len ; i++) {
            this.values[i]=Double.parseDouble(values[i].replace(',','.').replaceAll(" ", ""));
        }
        if (values.length==len+1) {
            decision = values[len].replaceAll(" ", "");
            mpp2.CheckAdd(decision);
        }
        else
            decision="";

    }

    @Override
    public String toString() {
        String r="";
        for (int i = 0; i < values.length; i++) {
            r=r+values[i]+";";
        }
        return r+decision;
    }
}
