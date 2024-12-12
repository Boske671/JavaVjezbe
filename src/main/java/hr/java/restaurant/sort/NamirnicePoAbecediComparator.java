package hr.java.restaurant.sort;

import hr.java.restaurant.model.Ingredient;

import java.util.Comparator;

public class NamirnicePoAbecediComparator implements Comparator<Ingredient> {

    @Override
    public int compare(Ingredient o1, Ingredient o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
