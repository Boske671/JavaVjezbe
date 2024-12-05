package hr.java.restaurant.model;

import hr.java.production.main.Main;
import hr.java.utils.DataInput;
import hr.java.utils.InputValidator;

import java.util.*;

/**
 * Klasa Restaurant koja sadrzi razna svojstva za objekt
 * Sadrži metode poput inputRestaurants, ispisRestaurant
 */
public class Restaurant extends Entity {
    private String name;
    private Address address;
    private HashSet<Meal> meals;
    private HashSet<Chef> chefs;
    private HashSet<Waiter> waiters;
    private HashSet<Deliverer> deliverers;


    /**
     * Konstruktor za kreiranje objekta klase `Restaurant`, koja definira osnovne informacije
     * o restoranu, uključujući njegovo ime, adresu, jela, kao i osoblje koje uključuje kuhare,
     * konobare i dostavljače.
     *
     * @param id         Jedinstveni identifikator restorana.
     * @param name       Ime restorana.
     * @param address    Objekt klase `Address` koji predstavlja adresu restorana.
     * @param meals      Polje objekata `Meal` koje sadrži sva jela koja restoran nudi.
     * @param chefs      Polje objekata `Chef` koje sadrži kuhare zaposlene u restoranu.
     * @param waiters    Polje objekata `Waiter` koje sadrži konobare zaposlene u restoranu.
     * @param deliverers Polje objekata `Deliverer` koje sadrži dostavljače zaposlene u restoranu.
     */

    public Restaurant(Long id, String name, Address address, HashSet<Meal> meals, HashSet<Chef> chefs, HashSet<Waiter> waiters, HashSet<Deliverer> deliverers) {
        super(id);
        this.name = name;
        this.address = address;
        this.meals = meals;
        this.chefs = chefs;
        this.waiters = waiters;
        this.deliverers = deliverers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public HashSet<Meal> getMeals() {
        return meals;
    }

    public void setMeals(HashSet<Meal> meals) {
        this.meals = meals;
    }

    public HashSet<Chef> getChefs() {
        return chefs;
    }

    public void setChefs(HashSet<Chef> chefs) {
        this.chefs = chefs;
    }

    public HashSet<Waiter> getWaiters() {
        return waiters;
    }

    public void setWaiters(HashSet<Waiter> waiters) {
        this.waiters = waiters;
    }

    public HashSet<Deliverer> getDeliverers() {
        return deliverers;
    }

    public void setDeliverers(HashSet<Deliverer> deliverers) {
        this.deliverers = deliverers;
    }

    /**
     * Metoda za unos podataka o restoranima, uključujući naziv, adresu, jela, kuhare, konobare i dostavljače.
     * Korisnik unosi podatke putem konzole, a metoda vraća polje objekata `Restaurant` s odgovarajućim podacima.
     *
     * @param n          Broj restorana koje korisnik želi unijeti.
     * @param meals      Polje dostupnih objekata `Meal`, tj. jela koja se mogu dodati restoranima.
     * @param chefs      Polje dostupnih objekata `Chef`, tj. kuhara koji se mogu dodati restoranima.
     * @param waiters    Polje dostupnih objekata `Waiter`, tj. konobara koji se mogu dodati restoranima.
     * @param deliverers Polje dostupnih objekata `Deliverer`, tj. dostavljača koji se mogu dodati restoranima.
     * @param sc         Objekt klase `Scanner` koji se koristi za unos podataka s konzole.
     * @return Polje objekata tipa `Restaurant` koje sadrži sve unesene restorane s njihovim podacima.
     */



    public static List<Restaurant> inputRestaurants(int n, List<Meal> meals, List<Chef> chefs, List<Waiter> waiters, List<Deliverer> deliverers, Scanner sc) {
        Main.log.info("Pozvana metoda inputRestaurants.");
        List<Restaurant> restaurants = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Main.log.info("Upis " + (i + 1) + ". Restaurant-a: ");
            System.out.println("UPISI PODATKE ZA " + (i + 1) + ". RESTORAN: ");
            System.out.println("Upiši ime restorana: ");
            String name = sc.nextLine();
            System.out.println("Upiši ime ulice: ");
            String street = sc.nextLine();
            System.out.println("Upisi kucni broj: ");
            String houseNumber = sc.nextLine();
            System.out.println("Upiši grad: ");
            String city = sc.nextLine();
            System.out.println("Upiši postanski broj: ");
            String postalCode = sc.nextLine();

            Address address = new Address((long) i, street, houseNumber, city, postalCode);

            // nove kolekcije za svaki restoran
            HashSet<Meal> newMeals = new HashSet<>();
            HashSet<Chef> newChefs = new HashSet<>();
            HashSet<Waiter> newWaiters = new HashSet<>();
            HashSet<Deliverer> newDeliverers = new HashSet<>();

            // Unos jela
            System.out.println("Upiši broj jela: ");
            int brJela = InputValidator.integerValidator(sc);
            System.out.println("Odaberi index od 0 do " + (meals.size() - 1));
            for (int k = 0; k < meals.size(); k++) {
                System.out.print("index: " + k + " - ");
                meals.get(k).ispisMeal();
            }
            for (int j = 0; j < brJela; j++) {
                int index = InputValidator.integerValidator(sc);
                newMeals.add(meals.get(index));
            }

            // Unos chefova
            System.out.println("Upisi broj chefova: ");
            int brChef = InputValidator.integerValidator(sc);
            System.out.println("Odaberi index od 0 do " + (chefs.size() - 1));
            for (int k = 0; k < chefs.size(); k++) {
                System.out.print("index: " + k + " - ");
                chefs.get(k).ispisZaposlenik();
            }
            for (int j = 0; j < brChef; j++) {
                int index = InputValidator.integerValidator(sc);
                newChefs.add(chefs.get(index));
            }

            // Unos konobara
            System.out.println("Upisi broj konobara: ");
            int brWaiter = InputValidator.integerValidator(sc);
            System.out.println("Odaberi index od 0 do " + (waiters.size() - 1));
            for (int k = 0; k < waiters.size(); k++) {
                System.out.print("index: " + k + " - ");
                waiters.get(k).ispisZaposlenik();
            }
            for (int j = 0; j < brWaiter; j++) {
                int index = InputValidator.integerValidator(sc);
                newWaiters.add(waiters.get(index));
            }

            // Unos dostavljača
            System.out.println("Upisi broj dostavljaca: ");
            int brDeliverer = InputValidator.integerValidator(sc);
            System.out.println("Odaberi index od 0 do " + (deliverers.size() - 1));
            for (int k = 0; k < deliverers.size(); k++) {
                System.out.print("index: " + k + " - ");
                deliverers.get(k).ispisZaposlenik();
            }
            for (int j = 0; j < brDeliverer; j++) {
                int index = InputValidator.integerValidator(sc);
                newDeliverers.add(deliverers.get(index));
            }

            // Dodaj restoran
            sc.nextLine();  // Čišćenje linije nakon unosa
            restaurants.add(new Restaurant((long) i, name, address, newMeals, newChefs, newWaiters, newDeliverers));
        }
        return restaurants;
    }


    public static HashMap<Meal, List<Restaurant>> getRestaurantWithCertainMeal(List<Meal> meals, List<Restaurant> restaurants) {
        HashMap<Meal, List<Restaurant>> restaurantWithCertainMeal = new HashMap<>();
        for (Meal meal : meals) {
            // Stvori novu listu za ovo jelo
            List<Restaurant> restaurants1 = new ArrayList<>();

            for (Restaurant restaurant : restaurants) {
                if (restaurant.getMeals().contains(meal)) {
                    restaurants1.add(restaurant);
                }
            }
            // Dodaj listu u mapu
            restaurantWithCertainMeal.put(meal, restaurants1);
        }
        return restaurantWithCertainMeal;
    }

    public static void restaurantWithCertainMealIspis(HashMap<Meal, List<Restaurant>> restaurantWithCertainMeal) {
        for (Map.Entry<Meal, List<Restaurant>> entry : restaurantWithCertainMeal.entrySet()) {
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
    }

    public int getEmployeeSize() {
        return getChefs().size() + getDeliverers().size() + getWaiters().size();
    }


    public void ispisRestoran() {
        System.out.println("Ime: " + getName() + " - Adresa: " + getAddress().getStreet() + " - " + getAddress().getHouseNumber() + " - " + getAddress().getCity());
    }
}
