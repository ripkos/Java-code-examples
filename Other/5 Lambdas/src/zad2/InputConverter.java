package zad2;

import java.util.function.Function;

public class InputConverter<Type>{
    Type fname;
    public InputConverter(Type fname) {
        this.fname=fname;
    }

    <Outcome> Outcome convertBy(Function ... fs) {
        Function f;
            f=fs[0];
            for (int i = 1; i < fs.length; i++) {
                f = fs[i].compose(f);
                //System.out.println(f.apply(fname));
            }

        Outcome Result = (Outcome) f.apply(fname);
        return Result;
    }
}
