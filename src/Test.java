import orange.OrangeFormula;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yin on 17/04/16.
 */
public class Test {

    public static void main(String[] args) {

        OrangeFormula a = new OrangeFormula("p");
        OrangeFormula b = new OrangeFormula("q");

        Set<OrangeFormula> set1 = new HashSet<OrangeFormula>();
        set1.add(a);
        set1.add(b);
        Set<OrangeFormula> set2 = new HashSet<OrangeFormula>();
        set2.add(a);

        List<OrangeFormula> list = new ArrayList<OrangeFormula>();
        list.add(b);

        set2.addAll(list);

        System.out.println(b.equals(a));
        System.out.println(set1.equals(set2));

        System.out.println(set2);

//        System.out.println(set1.contains(b));
//
//        set1.addAll(set2);
//        System.out.println(set1.toString());
    }

}
