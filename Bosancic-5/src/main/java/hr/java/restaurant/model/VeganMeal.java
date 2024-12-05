package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.util.HashSet;

public final class VeganMeal extends Meal implements Vegan {
    private String proteinSource;
    public VeganMeal(long id, String name, Category category, HashSet<Ingredient> ingredients, BigDecimal price, String proteinSource) {
        super(id, name, category, ingredients, price);
        this.proteinSource = proteinSource;
    }

    public String getProteinSource() {
        return proteinSource;
    }

    public void setProteinSource(String proteinSource) {
        this.proteinSource = proteinSource;
    }

    @Override
    public boolean isGlutenFree()
    {
        return true;
    }

    @Override
    public boolean containsSoy()
    {
        return true;
    }

    public void ispisVeganMeal()
    {
        ispisMeal();
        System.out.println("Izvor proteina: " + getProteinSource());
        System.out.println((isGlutenFree()) ? "Gluten free" : "Nije gluten  free");
        System.out.println((containsSoy()) ? "Ima soju" : "Nema soju");
    }
}
