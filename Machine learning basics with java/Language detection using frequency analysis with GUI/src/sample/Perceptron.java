package sample;

import sample.view.faze1.Eval;
import sample.view.faze1.Matrix;
import sample.view.faze1.Weights;

public class Perceptron {
    public int TasT=0, TasF=0;
    public int FasT=0, FasF=0;



    public static double a =0.0001;
    public static double b =0.0001;
    public double[] w;
    public double t;
    public String lang;
    public Perceptron(String n) {
        t=1;
        lang=n;
        w = new double[26];
        for (int i = 0; i < w.length; i++) {
            w[i]=Math.random()*10+0.1;
        }
    }
    public boolean train(double[] x, String name) {
        int y = activate(x);
        int d = name.equals(lang) ? 1 : 0;
        if(d==y) return true;
        for (int i = 0; i < w.length ; i++) {
            w[i]=w[i]+(d-y)*x[i]*(a);
        }
        t=t+(d-y)*(b);
        return false;
    }

    public int activate(double[] x) {
        double sum=0;
        for (int i = 0; i < x.length; i++) {
            sum+=w[i]*x[i];
        }
        sum+=t;
        return sum>=0 ? 1 : 0;
    }

    public Weights[] getWeights() {
        Weights[] arr = new Weights[27];
        for(char c='a'; c<='z';c++) {
            arr[c-'a']=new Weights(String.valueOf(c),w[c-'a']);
        }
        arr[26]=new Weights("TETA",t);
        return arr;
    }

    @Override
    public String toString() {
        return this.lang;
    }

    public Eval[] getEval() {
        Eval[] r = new Eval[4];
        r[0]=new Eval("Dokladnosc",((double)(FasF+TasT)/(FasF+TasT+FasT+TasF)));
        double prec = ((double)(TasT)/(TasT+FasT));
        r[1]=new Eval("Precyzja", prec);
        double pel = ((double)(TasT)/(TasT+TasF));
        r[2]=new Eval("Pelnosc", pel);
        r[3]=new Eval("F-Miara", (2*prec*pel)/(prec+pel));
        return r;
    }

    public Matrix[] getMatrix() {
        Matrix[] r = new Matrix[2];
        r[0] = new Matrix("Faktycznie+",TasT,TasF);
        r[1] = new Matrix("Faktycznie-",FasT,FasF);
        return r;
    }
}
