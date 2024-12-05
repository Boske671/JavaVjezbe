package hr.java.restaurant.sort;

import java.util.Comparator;

public class ProbaComparator implements Comparator<Integer> {
    public int compare(Integer o1, Integer o2) {
        //Integer.compare -> built in isto ko ovo

        //OVO JE UZLAZNO
        if (o1 > o2) {
            return 1;
            /*
            NPR 4 I 3.5
            4 > 3.5
            RETURN 1 -> ZNACI DA O1 IDE NAKON O2
             */
        }
        if (o1 < o2) {
            return -1;
            /*
            NPR 2 I 6
            2 < 6
            RETURN -1 -> O1 PRIJE O2
             */
        }
        else {
            return 0;
        }
    }
}
