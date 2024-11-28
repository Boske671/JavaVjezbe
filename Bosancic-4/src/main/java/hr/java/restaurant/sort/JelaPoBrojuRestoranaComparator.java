package hr.java.restaurant.sort;

import hr.java.restaurant.model.Meal;
import hr.java.restaurant.model.Restaurant;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class JelaPoBrojuRestoranaComparator implements Comparator<Meal> {

    private Map<Meal, List<Restaurant>> mealMap;

    public JelaPoBrojuRestoranaComparator(Map<Meal, List<Restaurant>> mealMap) {
        this.mealMap = mealMap;
    }

    @Override
    public int compare(Meal m1, Meal m2) {
        int brojRestorana1 = mealMap.getOrDefault(m1, List.of()).size();
        int brojRestorana2 = mealMap.getOrDefault(m2, List.of()).size();

        return Integer.compare(brojRestorana2, brojRestorana1);
    }
}
