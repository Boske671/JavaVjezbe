package hr.java.restaurant.model;

public sealed interface Vegan permits VeganMeal {
    boolean isGlutenFree();
    boolean containsSoy();

}