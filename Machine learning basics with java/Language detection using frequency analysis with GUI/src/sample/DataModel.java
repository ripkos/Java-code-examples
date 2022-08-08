package sample;

import java.util.List;

public class DataModel {
    String name;
    List<double[]> proportions;
    public DataModel(String n, List<double[]> l) {
        name=n; proportions=l;
    }
}
