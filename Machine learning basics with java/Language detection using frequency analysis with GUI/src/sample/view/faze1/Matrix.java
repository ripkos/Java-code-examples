package sample.view.faze1;

public class Matrix {
    String rowCase;

    public String getRowCase() {
        return rowCase;
    }

    public int getPositiveNum() {
        return positiveNum;
    }

    public int getNegativeNum() {
        return negativeNum;
    }

    public Matrix(String rowCase, int positiveNum, int negativeNum) {
        this.rowCase = rowCase;
        this.positiveNum = positiveNum;
        this.negativeNum = negativeNum;
    }

    int positiveNum;
    int negativeNum;

}
