package hr.java.restaurant.model;

public sealed interface Vegetarian permits VegetarianMeal  {
    boolean isDairyFree();
    boolean containsEggs();
}
