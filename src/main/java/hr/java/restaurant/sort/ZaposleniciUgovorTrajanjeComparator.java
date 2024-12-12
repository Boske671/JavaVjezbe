package hr.java.restaurant.sort;

import hr.java.restaurant.model.Person;

import java.util.Comparator;

public class ZaposleniciUgovorTrajanjeComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return Long.compare(o1.getUgovorTrajanjeZaposlenika(), o2.getUgovorTrajanjeZaposlenika());
    }
}