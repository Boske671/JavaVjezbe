package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.util.HashSet;

public final class MeatMeal extends Meal implements Meat {
    BigDecimal amountOfProtein;

    public MeatMeal(Long id, String name, Category category, HashSet<Ingredient> ingredients, BigDecimal price, BigDecimal amountOfProtein) {
        super(id, name, category, ingredients, price);
        this.amountOfProtein = amountOfProtein;
    }

    public BigDecimal getAmountOfProtein()
    {
        return amountOfProtein;
    }

    public void setAmountOfProtein(BigDecimal amountOfProtein)
    {
        this.amountOfProtein = amountOfProtein;
    }

    @Override
    public boolean isGrilled()
    {
        return true;
    }

    @Override
    public boolean hasBone()
    {
        return false;
    }

    public void ispisMeatMeal()
    {
        ispisMeal();
        System.out.println("Broj proteina: " + getAmountOfProtein());
        System.out.println((isGrilled()) ? "Pečeno je" : "Nije pečeno");
        System.out.println((hasBone()) ? "Ima kosti" : "Nema kosti");
    }




}
