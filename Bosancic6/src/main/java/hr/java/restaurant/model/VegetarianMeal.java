package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.util.HashSet;

public final class VegetarianMeal extends Meal implements Vegetarian {


    private String mainIngredient;

    public VegetarianMeal(Long id, String name, Category category, HashSet<Ingredient> ingredients, BigDecimal price, String mainIngredient) {
        super(id, name, category, ingredients, price);
        this.mainIngredient = mainIngredient;
    }

    public String getMainIngredient()
    {
        return mainIngredient;
    }

    public void setMainIngredient(String mainIngredient)
    {
        this.mainIngredient = mainIngredient;
    }

    @Override
    public boolean isDairyFree()
    {
        return false;
    }

    @Override
    public boolean containsEggs()
    {
        return true;
    }

    public void ispisVegetarianMeal()
    {
        ispisMeal();
        System.out.println("Glavni sastojak: " + getMainIngredient());
        System.out.println((isDairyFree()) ? "Ima mlijecnih proizvoda" : "Nema mlijecnih proizvoda");
        System.out.println((containsEggs()) ? "Ima jaja" : "Nema jaja");
    }
}
