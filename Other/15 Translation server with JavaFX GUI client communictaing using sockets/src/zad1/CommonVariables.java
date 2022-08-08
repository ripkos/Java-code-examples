package zad1;

public class CommonVariables {
    private static CommonVariables obj;

    public int proxyServerPort;
    public String localhost;
    public CommonVariables () {
        proxyServerPort=9001;
        localhost="127.0.0.1";
    }

    public static CommonVariables getVars() {
        if (obj == null) {
            obj = new CommonVariables();
        }
        return obj;
    }

}
