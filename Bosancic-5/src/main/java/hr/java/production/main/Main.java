package hr.java.production.main;

import hr.java.restaurant.generics.RestaurantLabourExchangeOffice;
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
        List<RestaurantLabourExchangeOffice<Restaurant>> offices = new ArrayList<>();
        offices.add(new RestaurantLabourExchangeOffice<>(restaurants));

        // Dohvaćanje liste restorana iz prve instance u listi
        List<Restaurant> restaurantList = offices.get(0).getRestaurantsList();
        List<Order> orders = new ArrayList<>();
        orders = Order.inputOrders(3, restaurants, meals, deliverers, sc);

        Restaurant restaurantMostEmployees = restaurants.stream().max((a, b) ->
                Integer.compare(a.getEmployeeSize(),
                        b.getEmployeeSize())).orElse(null);
        restaurantMostEmployees.ispisRestoran();

        /*

        orders.stream()  // Kreiramo stream iz liste narudžbi
                .flatMap(order -> order.getMeals().stream())  // Razvijamo listu jela u stream
                .collect(Collectors.groupingBy(Meal -> Meal, Collectors.counting()))  // Grupiraje po jelu i brojanje
                .entrySet().stream()  // Stream od ulaza mape (jelo, broj narudžbi)
                .max((entry1, entry2) -> Long.compare(entry1.getValue(), entry2.getValue()))  // Pronalazimo jelo sa najviše narudžbi
                .map(Map.Entry::getKey)  // Uzimamo samo jelo
                .ifPresent(meal -> System.out.println("Most ordered meal: " + meal));
        sc.close();

         */


        System.out.println("\nko zna sta ja radim");
        orders.stream()
                .forEach(order -> {
                    System.out.println("Order id: " + order.getId());
                    order.getMeals().stream().forEach(meal -> {
                        System.out.println("Meal: " + meal.getName());
                        meal.getIngredients().forEach(Ingredient::ispisIngredient);
                    });
                });


        BigDecimal ukCijena = orders.stream()
                .flatMap(order -> order.getMeals().stream())
                .map(meal -> meal.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        restaurants.stream()
                .collect(Collectors.groupingBy(restaurant -> restaurant.getAddress().getCity()))  // Grupisanje restorana po gradu
                .forEach((city, restaurants1) -> // Za svaki grad i njegovu listu restorana
                        {
                            System.out.println("Grad " + city);
                            restaurants1.forEach(restaurant -> {
                                restaurant.ispisRestoran();
                            });
                        }
                );
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