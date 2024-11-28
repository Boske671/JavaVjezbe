package hr.java.production.main;

import hr.java.restaurant.model.*;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Main klasa, služi za korištenje ostalih klasa i poziv metoda
 */
public class Main {

    public static final BigDecimal MINIMALNA_PLACA = new BigDecimal("800");
    public static final Logger log = LoggerFactory.getLogger(Main.class); // Correct class name here


    public static void main(String[] args) {
        redirectSystemOutToLogger();
        System.out.println("Pocinje main");
        Scanner sc = new Scanner(System.in);

        /*
        INPUT METODE


         Category[] categories = Category.inputCategories(3, sc);
        Ingredient[] ingredients = Ingredient.inputIngredients(3, categories, sc);
        //ingredients[2].ispisIngredient();
        Meal[] meals = Meal.inputMeals(3, categories, ingredients, sc);
        //meals[2].ispisMeal();
        Chef[] chefs = Chef.inputChefs(3, sc);
        Deliverer[] deliverers = Deliverer.inputDeliverers(3, sc);
        Waiter[] waiters = Waiter.inputWaiters(3, sc);
        Restaurant[] restaurants = Restaurant.inputRestaurants(3, meals, chefs, waiters, deliverers, sc);
        Order[] orders = Order.inputOrders(3, restaurants, meals, deliverers, sc);
        Order.restoranNajskupljaDostava(orders);
         */

        List<Category> categories = new ArrayList<>();
        categories = Category.inputCategories(3, sc);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients = Ingredient.inputIngredients(3, categories, sc);
        List<Meal> meals = new ArrayList<>();
        meals = Meal.inputMeals(3, categories, ingredients, sc);
        List<Chef> chefs = new ArrayList<>();
        chefs = Chef.inputChefs(3, sc);
        List<Waiter> waiters = new ArrayList<>();
        waiters = Waiter.inputWaiters(3, sc);
        List<Deliverer> deliverers = new ArrayList<>();
        deliverers = Deliverer.inputDeliverers(3, sc);
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants = Restaurant.inputRestaurants(3, meals, chefs, waiters, deliverers, sc);
        List<Order> orders = new ArrayList<>();
        orders = Order.inputOrders(3, restaurants, meals, deliverers, sc);
        HashMap<Meal, List<Restaurant>> restaurantMap = new HashMap<>();

        System.out.println("Lista jela:");
        meals.forEach(meal -> System.out.println(meal.getName()));

        System.out.println("Lista restorana i njihovih jela:");
        for (Restaurant restaurant : restaurants) {
            System.out.println(restaurant.getName() + " nudi: " +
                    restaurant.getMeals().stream().map(Meal::getName).collect(Collectors.joining(", ")));
        }

        System.out.println("----------------");


        restaurantMap = Restaurant.getRestaurantWithCertainMeal(meals, restaurants);
        for (Map.Entry<Meal, List<Restaurant>> entry : restaurantMap.entrySet()) {
            Meal key = entry.getKey();
            List<Restaurant> value = entry.getValue();

            key.ispisMeal();
            if (value.isEmpty()) {
                System.out.println("PRAZNO! Nema restorana za ovo jelo.");
            } else {
                System.out.println("Restorani koji nude ovo jelo:");
                for (Restaurant restaurant : value) {
                    System.out.println("- " + restaurant.getName());
                }
            }
            System.out.println("\n");
        }
        System.out.println("\n\n");
        Meal.jelaSortiranaPoBrojuRestorana(meals, restaurantMap);
        for (Meal meal : meals) {
            System.out.println(meal.getName());
        }

        /*
        for (Map.Entry<Meal, List<Restaurant>> entry : restaurantMap.entrySet()) {
            Meal key = entry.getKey();
            List<Restaurant> value = entry.getValue();

            key.ispisMeal();
            if (value.isEmpty()) {
                System.out.println("PRAZNO! Nema restorana za ovo jelo.");
            } else {
                System.out.println("Restorani koji nude ovo jelo:");
                for (Restaurant restaurant : value) {
                    System.out.println("- " + restaurant.getName());
                }
            }
            System.out.println("\n");
        }
         */
        /*
        Meal.jelaSortiranaPoBrojuRestorana(meals, restaurantMap);
        for (Meal meal : meals) {
            meal.ispisMeal();
        }

         */
        sc.close();
    }
    
    private static void redirectSystemOutToLogger() {
        Logger systemOutLogger = LoggerFactory.getLogger("SYSTEM_OUT");
        PrintStream customOut = new PrintStream(System.out) {
            @Override
            public void println(String message) {
                systemOutLogger.info(message);
            }
        };
        System.setOut(customOut); // Redirect System.out
    }
}





        /*
        VEC UNSENE VRIJEDNOSTI
        Category kategorija11 = new Category((long) 11, "Kategorija11", "Kat11 Opis");
        Category kategorija22 = new Category((long) 22, "Kategorija22", "Kat22 Opis");
        Category kategorija33 = new Category((long) 33, "Kategorija33", "Kat33 Opis");

        Ingredient ingredient11 = new Ingredient((long) 11, "Ingredient11", kategorija11, BigDecimal.valueOf(100), "preparationMethod11");
        Ingredient ingredient22 = new Ingredient((long) 22, "Ingredient22", kategorija22, BigDecimal.valueOf(200), "preparationMethod22");
        Ingredient ingredient33 = new Ingredient((long) 33, "Ingredient33", kategorija33, BigDecimal.valueOf(300), "preparationMethod33");

        Ingredient ingredients11[] = {ingredient11, ingredient22, ingredient33};
        Ingredient ingredients22[] = {ingredient22, ingredient33};
        Ingredient ingredients33[] = {ingredient11, ingredient22};
        Meal meal11 = new Meal((long) 11, "Meal11", kategorija11, ingredients11, BigDecimal.valueOf(100));
        Meal meal22 = new Meal((long) 22, "Meal22", kategorija22, ingredients22, BigDecimal.valueOf(200));
        Meal meal33 = new Meal((long) 33, "Meal33", kategorija33, ingredients33, BigDecimal.valueOf(300));

        Meal meals11[] = {meal11, meal22, meal33};
        Meal meals22[] = {meal22, meal33};
        Meal meals33[] = {meal11, meal22};

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        Chef chef11 = new Chef((long) 11, "ChefName11", "ChefLastName11", new Contract(BigDecimal.valueOf(1000),
                LocalDate.parse("10.10.2023.", dateFormat), LocalDate.parse("10.10.2024.", dateFormat), "FULL_TIME"),
                new Bonus(BigDecimal.valueOf(300)));
        Chef chef22 = new Chef((long) 22, "ChefName22", "ChefLastName22", new Contract(BigDecimal.valueOf(1100),
                LocalDate.parse("05.05.2023.", dateFormat), LocalDate.parse("10.10.2024.", dateFormat), "FULL_TIME"),
                new Bonus(BigDecimal.valueOf(300)));
        Chef chef33 = new Chef((long) 33, "ChefName33", "ChefLastName3", new Contract(BigDecimal.valueOf(1200),
                LocalDate.parse("15.12.2022.", dateFormat), LocalDate.parse("11.08.2024.", dateFormat), "PART_TIME"),
                new Bonus(BigDecimal.valueOf(300)));
        Deliverer Deliverer11 = new Deliverer((long) 11, "DelivererName11", "DelivererLastName11", new Contract(BigDecimal.valueOf(1000),
                LocalDate.parse("10.10.2023.", dateFormat), LocalDate.parse("10.10.2024.", dateFormat), "FULL_TIME"),
                new Bonus(BigDecimal.valueOf(300)));
        Deliverer Deliverer22 = new Deliverer((long) 22, "DelivererName22", "DelivererLastName22", new Contract(BigDecimal.valueOf(1100),
                LocalDate.parse("05.05.2023.", dateFormat), LocalDate.parse("10.10.2024.", dateFormat), "FULL_TIME"),
                new Bonus(BigDecimal.valueOf(300)));
        Deliverer Deliverer33 = new Deliverer((long) 33, "DelivererName33", "DelivererLastName3", new Contract(BigDecimal.valueOf(1200),
                LocalDate.parse("15.12.2022.", dateFormat), LocalDate.parse("11.08.2024.", dateFormat), "PART_TIME"),
                new Bonus(BigDecimal.valueOf(300)));
        Waiter Waiter11 = new Waiter((long) 11, "WaiterName11", "WaiterLastName11", new Contract(BigDecimal.valueOf(1000),
                LocalDate.parse("10.10.2023.", dateFormat), LocalDate.parse("10.10.2024.", dateFormat), "FULL_TIME"),
                new Bonus(BigDecimal.valueOf(300)));
        Waiter Waiter22 = new Waiter((long) 22, "WaiterName22", "WaiterLastName22", new Contract(BigDecimal.valueOf(1100),
                LocalDate.parse("05.05.2023.", dateFormat), LocalDate.parse("10.10.2024.", dateFormat), "FULL_TIME"),
                new Bonus(BigDecimal.valueOf(300)));
        Waiter Waiter33 = new Waiter((long) 33, "WaiterName33", "WaiterLastName3", new Contract(BigDecimal.valueOf(1200),
                LocalDate.parse("15.12.2022.", dateFormat), LocalDate.parse("11.08.2024.", dateFormat), "PART_TIME"),
                new Bonus(BigDecimal.valueOf(300)));

        Chef chefs11[] = {chef11, chef22, chef33};
        Chef chefs22[] = {chef22, chef33};
        Chef chefs33[] = {chef11, chef22};

        Waiter Waiters11[] = {Waiter11, Waiter22, Waiter33};
        Waiter Waiters22[] = {Waiter22, Waiter33};
        Waiter Waiters33[] = {Waiter11, Waiter22};

        Deliverer Deliverers11[] = {Deliverer11, Deliverer22, Deliverer33};
        Deliverer Deliverers22[] = {Deliverer22, Deliverer33};
        Deliverer Deliverers33[] = {Deliverer11, Deliverer22};
        Address address11 = new Address((long) 11, "addressStreet11", "houseNumber11", "city11", "postalCode11");
        Address address22 = new Address((long) 22, "addressStreet22", "houseNumber22", "city22", "postalCode22");
        Address address33 = new Address((long) 33, "addressStreet33", "houseNumber33", "city33", "postalCode33");
        Restaurant restaurant11 = new Restaurant((long) 11, "Restaurant11", address11, meals11, chefs11, Waiters11, Deliverers11 );
        Restaurant restaurant22 = new Restaurant((long) 22, "Restaurant22", address22, meals22, chefs22, Waiters22, Deliverers22 );
        Restaurant restaurant33 = new Restaurant((long) 33, "Restaurant33", address33, meals33, chefs33, Waiters33, Deliverers33 );


        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        Order order11 = new Order ((long) 11, restaurant11, meals11, Deliverer11, LocalDateTime.parse("02.02.2022. 22:22", dateTimeFormat));
        Order order22 = new Order ((long) 22, restaurant22, meals22, Deliverer22, LocalDateTime.parse("05.05.2023. 12:40", dateTimeFormat));
        Order order33 = new Order ((long) 33, restaurant33, meals33, Deliverer33, LocalDateTime.parse("10.10.2024. 09:30", dateTimeFormat));
         */




        /*
        INPUT METODE


         Category[] categories = Category.inputCategories(3, sc);
        Ingredient[] ingredients = Ingredient.inputIngredients(3, categories, sc);
        //ingredients[2].ispisIngredient();
        Meal[] meals = Meal.inputMeals(3, categories, ingredients, sc);
        //meals[2].ispisMeal();
        Chef[] chefs = Chef.inputChefs(3, sc);
        Deliverer[] deliverers = Deliverer.inputDeliverers(3, sc);
        Waiter[] waiters = Waiter.inputWaiters(3, sc);
        Restaurant[] restaurants = Restaurant.inputRestaurants(3, meals, chefs, waiters, deliverers, sc);
        Order[] orders = Order.inputOrders(3, restaurants, meals, deliverers, sc);
        Order.restoranNajskupljaDostava(orders);
         */



        /*
        Chef[] chefs = inputChefs(1, sc);
        Deliverer[] deliverers = inputDeliverers(1, sc);
        Person people[] = new Person[1];
        people[0] = chefs[0];
        people[1] = deliverers[0];
        people[2] = waiters[0];

        Person.najvecaPlacaZaposlenika(people);
        Person.najduziUgovorZaposlenika(people);


        Chef chef1 = new Chef.Builder().withFirstName("sef1").build();
        Waiter waiter1 = new Waiter.Builder().withFirstName("waiter1").build();
        Deliverer deliverer1 = new Deliverer.Builder().withFirstName("deliverer1").build();
        Address address1 = new Address.Builder().withStreet("ulica1").build();
        Category category1 = new Category.Builder().withName("cat1").build();

        Ingredient[] ingredientsVegan = new Ingredient[] { new Ingredient( (long) 1, "jabuka", new Category((long)1, "voce", "opisvoce"), BigDecimal.valueOf(10), "spremanjejabuke"),
                new Ingredient( (long) 5, "jabuka", new Category((long)1, "voce", "opisvoce"), BigDecimal.valueOf(60), "spremanjejabuke")};
        VeganMeal prviVegan = new VeganMeal ((long) 1, "prvoVeganJelo", new Category((long) 1, "voce", "opisvoce"), ingredientsVegan, BigDecimal.valueOf(100), "aaaaaaaaa");

        Ingredient[] ingredientsVegetarian = new Ingredient[] { new Ingredient( (long) 2, "kruska", new Category((long)1, "voce", "opisvoce"), BigDecimal.valueOf(15), "spremanjekruske")};
        VegetarianMeal prviVegetarian = new VegetarianMeal((long) 1, "prvoVegetarianJelo", new Category((long)1, "voce", "opisvoce"), ingredientsVegetarian, BigDecimal.valueOf(100), "bbbbbbbbbbbb");

        Ingredient[] ingredientsMeat = new Ingredient[] { new Ingredient( (long) 3,"banana", new Category((long)1, "voce", "opisvoce"), BigDecimal.valueOf(20), "spremanjebanane")};
        MeatMeal prviMeat = new MeatMeal((long) 1, "prvoMeatJelo", new Category((long)1, "voce", "opisvoce"), ingredientsMeat, BigDecimal.valueOf(100), BigDecimal.valueOf(100));

        Meal[] meals123 = new Meal[3];
        meals123[0] = prviVegetarian;
        meals123[1] = prviVegan;
        meals123[2] = prviMeat;
        Meal.ispisVrstiJela(meals123);

        */