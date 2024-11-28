package hr.java.restaurant.model;

import hr.java.production.main.Main;
import hr.java.restaurant.sort.NamirnicePoAbecediComparator;
import hr.java.utils.DataInput;
import hr.java.utils.InputValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Klasa Ingredient koja sadrzi razna svojstva za objekt
 * Sadrži metode inputIngredients, ispisIngredient
 */
public class Ingredient extends Entity {

    private String name;
    private Category category;
    private BigDecimal kcal;
    private String preparationMethod;

    /**
     * Konstruktor klase Ingredient koji prima (Long id, String name, Category category, BigDecimal kcal, String preparationMethod)
     */
    public Ingredient(Long id, String name, Category category, BigDecimal kcal, String preparationMethod) {
        super(id);
        this.name = name;
        this.category = category;
        this.kcal = kcal;
        this.preparationMethod = preparationMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getKcal() {
        return kcal;
    }

    public void setKcal(BigDecimal kcal) {
        this.kcal = kcal;
    }

    public String getPreparationMethod() {
        return preparationMethod;
    }

    public void setPreparationMethod(String preparationMethod) {
        this.preparationMethod = preparationMethod;
    }

    /**
     * Metoda inputIngredients za upis Ingredient-a (upis svojstava)
     * @param n količinu kategorija koje želimo upisati
     * @param categories polje vrste Category[] koje je nam je potrebno pri stvaranju objekta Ingredient
     * @param sc Objekt klase Scanner za upis
     * @return vraća polje vrste Ingredient[]
     */
    public static List<Ingredient> inputIngredients(int n, List<Category> categories, Scanner sc) {
        Main.log.info("Pozvana metoda inputIngredients.");
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + ". sastojak: ");
            Main.log.info("Upis imena "  + (i + 1) + ". sastojka: ");
            System.out.println("Upiši ime sastojka: ");
            String name = sc.nextLine();

            System.out.println("Upisi index od 0 do " + (categories.size() - 1) + ", " + (i + 1) + ". sastojka: ");
            for (int j = 0; j < categories.size(); j++) {
                Main.log.info("Upis indexa za unos kategorije.");
                System.out.println("Index: " + j + " - ime: " + categories.get(j).getName() + " - opis: " + categories.get(j).getDescription());
            }
            int index = InputValidator.integerValidator(sc);

            System.out.println("Kalorije: ");
            Main.log.info("Upis brKal "  + (i + 1) + ". sastojka: ");
            BigDecimal brKal = InputValidator.bigDecimalValidator(sc);
            sc.nextLine();
            Main.log.info("Upis metode pripreme "  + (i + 1) + ". sastojka: ");
            System.out.println("Upisi metodu pripreme: ");
            String metoda = sc.nextLine();
            ingredients.add(new Ingredient((long) i, name, categories.get(index), brKal, metoda));
        }
        return ingredients;
    }

    public void ispisIngredient() {
        System.out.println("Ime sastojka: " + getName() + " - Broj kalorija: " + getKcal() + " - Metoda pripreme: " + getPreparationMethod());
        getCategory().ispisCategory();
    }

    public static void sortIngredientsAlphabetically(List<Ingredient> ingredients) {
        ingredients.sort(new NamirnicePoAbecediComparator());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(kcal, that.kcal) && Objects.equals(preparationMethod, that.preparationMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, kcal, preparationMethod);
    }
}
