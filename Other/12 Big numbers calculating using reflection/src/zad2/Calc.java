/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad2;


import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;

public class Calc {
    public String doCalc(String arg) {
        try {
            String[] args = arg.split("\\s+");
            BigDecimal v1 = new BigDecimal(args[0]);
            BigDecimal v2 = new BigDecimal(args[2]);
            String opp = args[1];
            opp=opp.replaceAll("\\+", "add");
            opp=opp.replaceAll("/", "divide");
            opp=opp.replaceAll("\\*", "multiply");
            opp=opp.replaceAll("-", "subtract");
            Method operation = BigDecimal.class.getMethod(opp,BigDecimal.class, MathContext.class);
            BigDecimal result = (BigDecimal) operation.invoke(v1,v2,MathContext.DECIMAL64);
            return result.toString();
        } catch (Exception ignored) {

        }
        return "Invalid command to calc";
    }
}  
