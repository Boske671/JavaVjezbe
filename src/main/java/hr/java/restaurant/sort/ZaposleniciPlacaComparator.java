package hr.java.restaurant.sort;

import hr.java.restaurant.model.Person;

import java.util.Comparator;

public class ZaposleniciPlacaComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return o2.getSalaryAndBonusZaposlenika().compareTo(o1.getSalaryAndBonusZaposlenika());
    }
}
