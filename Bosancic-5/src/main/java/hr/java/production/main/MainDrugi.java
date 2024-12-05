package hr.java.production.main;

import hr.java.restaurant.model.Ingredient;
import hr.java.restaurant.sort.ProbaComparator;

import java.util.ArrayList;
import java.util.List;

public class MainDrugi {
    public static void main(String[] args) {
        List<Integer> lista = new ArrayList<>();
        lista.add(10);
        lista.add(1);
        lista.add(2);
        lista.add(3);
        lista.add(-1);
        lista.add(4);
        lista.sort(new ProbaComparator());
        System.out.println(lista);

    }
}
