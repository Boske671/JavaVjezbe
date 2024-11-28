package hr.java.restaurant.sort;

import hr.java.restaurant.model.Chef;
import hr.java.restaurant.model.Deliverer;
import hr.java.restaurant.model.Person;
import hr.java.restaurant.model.Waiter;

import java.util.Comparator;

public class ZaposleniciPlacaComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return o2.getSalaryZaposlenika().compareTo(o1.getSalaryZaposlenika());
    }
}
