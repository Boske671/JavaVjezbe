package hr.java.restaurant.model;

import hr.java.production.main.Main;
import hr.java.restaurant.sort.JelaPoBrojuRestoranaComparator;
import hr.java.utils.DataInput;
import hr.java.utils.InputValidator;

import java.math.BigDecimal;
import java.util.*;


/**
 * Klasa Meal koja sadrzi razna svojstva za objekt
 * Sadrži metode poput inputMeals, ispisMeal
 */
public class Meal extends Entity {

    private String name;
    private Category category;
    private HashSet<Ingredient> ingredients;
    private BigDecimal price;


    /**
     * Konstruktor klase Meal
     *
     * @param id          parametar vrste long (od nadklase Entity)
     * @param name        parametar koji je ime Meal-a
     * @param category    već stvoreni objekt potreban za stvaranje objekta Meal
     * @param ingredients polje vrste "Ingredient"
     * @param price       parametar koji je cijena "Meal"-a
     */
    public Meal(Long id, String name, Category category, HashSet<Ingredient> ingredients, BigDecimal price) {
        super(id);
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.price = price;
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

    public HashSet<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashSet<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    /**
     * Metoda za unos podataka o jelima, uključujući naziv jela, kategoriju, sastojke i cijenu.
     * Metoda traži od korisnika da unese sve potrebne podatke putem konzole.
     *
     * @param n           Broj jela koje korisnik želi unijeti.
     * @param categories  Polje dostupnih kategorija jela koje korisnik može odabrati.
     * @param ingredients Polje dostupnih sastojaka koje korisnik može odabrati za svako jelo.
     * @param sc          Objekt klase Scanner koji se koristi za unos podataka s konzole.
     * @return Polje objekata tipa `Meal` koje sadrži sva unesena jela s pridruženim podacima.
     */

    public static List<Meal> inputMeals(int n, List<Category> categories, List<Ingredient> ingredients, Scanner sc) {
        Main.log.info("Pozvana metoda inputMeals.");
        List<Meal> meals = new ArrayList<>();
        for (int i = 0; i < n; i++) {

            System.out.println((i + 1) + ". jelo: ");
            Main.log.info("Upis imena " + (i + 1) + ". jela: ");
            System.out.println("Upiši ime jela: ");
            String name = sc.nextLine();
            System.out.println("Upisi index od 0 do " + (categories.size() - 1) + ", " + (i + 1) + ". jela: ");
            Main.log.info("Upis indexa kategorije " + (i + 1) + ". jela: ");
            for (int j = 0; j < categories.size(); j++) {
                categories.get(j).ispisCategory();
            }


            int indexKat = InputValidator.integerValidator(sc);

            System.out.println("Upisi broj sastojaka " + (i + 1) + ". jela: ");
            Main.log.info("Upis broja sastojaka " + (i + 1) + ". jela: ");
            int brSastojaka = InputValidator.integerValidator(sc);
            sc.nextLine();

            for (int j = 0; j < ingredients.size(); j++) {
                ingredients.get(j).ispisIngredient();
            }

            HashSet<Ingredient> sastojci = new HashSet<>();


            for (int j = 0; j < brSastojaka; j++) {
                Main.log.info("Upis indexa sastojka " + (i + 1) + ". jela: ");
                System.out.println("Upisi index od 0 do " + (ingredients.size() - 1) + ", " + (i + 1) + ". jela: ");
                int indexSastojka = InputValidator.integerValidator(sc);
                sastojci.add(ingredients.get(indexSastojka));
            }
            Main.log.info("Upis cijene " + (i + 1) + ". jela: ");
            System.out.println("Upisi cijenu: " + (i + 1) + ". jela: ");
            BigDecimal cijena = InputValidator.bigDecimalValidator(sc);
            sc.nextLine();
            meals.add(new Meal((long) i, name, categories.get(indexKat), sastojci, cijena));
        }
        return meals;
    }


    public static void ispisVrstiJela(Meal[] meals) {
        BigDecimal maksDec = BigDecimal.ZERO;
        BigDecimal minDec = BigDecimal.valueOf(999999);
        Meal max = null;
        Meal min = null;

        for (Meal meal : meals) {
            BigDecimal brkal = BigDecimal.ZERO;

            for (Ingredient ingredient : meal.getIngredients()) {
                brkal = brkal.add(ingredient.getKcal());
            }

            if (brkal.compareTo(maksDec) > 0) {
                max = meal;
                maksDec = brkal;
            }
            if (brkal.compareTo(minDec) < 0) {
                min = meal;
                minDec = brkal;
            }
        }

        // Prikazivanje jela s najmanje kalorija
        System.out.println("MIN: ");
        if (min != null) {
            if (min instanceof VeganMeal) {
                ((VeganMeal) min).ispisVeganMeal();
            } else if (min instanceof VegetarianMeal) {
                ((VegetarianMeal) min).ispisVegetarianMeal();
            } else if (min instanceof MeatMeal) {
                ((MeatMeal) min).ispisMeatMeal();
            } else {
                min.ispisMeal();
            }
        }

        // Prikazivanje jela s najviše kalorija
        System.out.println("MAX: ");
        if (max != null) {
            if (max instanceof VeganMeal) {
                ((VeganMeal) max).ispisVeganMeal();
            } else if (max instanceof VegetarianMeal) {
                ((VegetarianMeal) max).ispisVegetarianMeal();
            } else if (max instanceof MeatMeal) {
                ((MeatMeal) max).ispisMeatMeal();
            } else {
                max.ispisMeal();
            }
        }
    }


    public static void jelaSortiranaPoBrojuRestorana(List<Meal> meals, HashMap<Meal, List<Restaurant>> restaurantMap) {
        meals.sort(new JelaPoBrojuRestoranaComparator(restaurantMap));
    }
    public static void jelaSortiranaPoBrojuRestoranaLambda(List<Meal> meals, HashMap<Meal, List<Restaurant>> restaurantMap) {
        //meals.sort((meal1, meal2) -> Integer.compare(restaurantMap.get(meal1).size(), restaurantMap.get(meal2).size()));
        meals.sort(Comparator.comparingInt(meal -> restaurantMap.get(meal).size()));
    }

    public void ispisMeal() {
        System.out.println("Ime: " + (getName()) + " - Price: " + getPrice());
        getCategory().ispisCategory();
        for (Ingredient ingredient : ingredients) {
            ingredient.ispisIngredient();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(name, meal.name) && Objects.equals(category, meal.category) && Objects.equals(ingredients, meal.ingredients) && Objects.equals(price, meal.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, ingredients, price);
    }
}
