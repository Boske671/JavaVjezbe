package hr.java.restaurant.model;

import hr.java.production.main.Main;
import hr.java.utils.InputValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



/**
 * Klasa Order koja sadrzi razna svojstva za objekt
 * Sadrži metode poput inputOrders, ispisOrder
 */
public class Order extends Entity {
    private Restaurant restaurant;
    private List<Meal> meals;
    private Deliverer deliverer;


    /**
     * Konstruktor za kreiranje objekta klase `Order`, koja predstavlja narudžbu u restoranu,
     * uključujući podatke o restoranu, naručenim jelima, dostavljaču i datumu te vremenu dostave.
     *
     * @param id Jedinstveni identifikator narudžbe.
     * @param restaurant Objekt klase `Restaurant` koji predstavlja restoran iz kojeg se narudžba šalje.
     * @param meals Polje objekata `Meal` koje sadrži sva jela uključena u narudžbu.
     * @param deliverer Objekt klase `Deliverer` koji predstavlja dostavljača zaduženog za isporuku narudžbe.
     * @param deliveryDateAndTime Vrijeme i datum dostave narudžbe kao objekt `LocalDateTime`.
     */

    public Order(Long id, Restaurant restaurant, List<Meal> meals, Deliverer deliverer, LocalDateTime deliveryDateAndTime) {
        super(id);
        this.restaurant = restaurant;
        this.meals = meals;
        this.deliverer = deliverer;
        this.deliveryDateAndTime = deliveryDateAndTime;
    }

    private LocalDateTime deliveryDateAndTime;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public Deliverer getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(Deliverer deliverer) {
        this.deliverer = deliverer;
    }

    public LocalDateTime getDeliveryDateAndTime() {
        return deliveryDateAndTime;
    }

    public void setDeliveryDateAndTime(LocalDateTime deliveryDateAndTime) {
        this.deliveryDateAndTime = deliveryDateAndTime;
    }


    /**
     * Metoda za unos podataka o narudžbama, uključujući restoran, jela, dostavljača i datum i vrijeme dostave.
     * Korisnik unosi podatke putem konzole, a metoda vraća polje objekata `Order` s odgovarajućim podacima.
     *
     * @param n Broj narudžbi koje korisnik želi unijeti.
     * @param restaurants Polje dostupnih objekata `Restaurant`, tj. restorana iz kojih se mogu naručiti jela.
     * @param meals Polje dostupnih objekata `Meal`, tj. jela koja su dostupna za naručivanje.
     * @param deliverers Polje dostupnih objekata `Deliverer`, tj. dostavljača koji mogu dostaviti narudžbe.
     * @param sc Objekt klase `Scanner` koji se koristi za unos podataka s konzole.
     * @return Polje objekata tipa `Order` koje sadrži sve unesene narudžbe s njihovim podacima.
     */

    public static List<Order> inputOrders(int n, List<Restaurant> restaurants, List<Meal> meals, List<Deliverer> deliverers, Scanner sc) {
        Main.log.info("Pozvana metoda inputOrders.");
        List<Order> orders = new ArrayList<>();


        for (int i = 0; i < n; i++) {
            List<Meal> newMeals = new ArrayList<>();

            Main.log.info("Upis "  + (i + 1) + ". Order-a: ");
            System.out.println((i + 1) + ". narudzba: ");
            System.out.println("Upisi index od 0 do " + (restaurants.size() - 1) + " za restoran: ");
            for (int k = 0; k < meals.size(); k++) {
                System.out.print("index: " + k + " - ");
                restaurants.get(k).ispisRestoran();
            }
            int indexRestoran = InputValidator.integerValidator(sc);

            System.out.println("Upisi broj jela: ");
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


            System.out.println("Upisi broj dostavljaca: ");
            for (int k = 0; k < deliverers.size(); k++) {
                System.out.print("index: " + k + " - ");
                deliverers.get(k).ispisZaposlenik();
            }
            int indexDel = InputValidator.integerValidator(sc);
            sc.nextLine();

            System.out.println("Upisi datum (dd.MM.yyyy. HH:mm) : ");
            String datum = sc.nextLine();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
            LocalDateTime newDate = LocalDateTime.parse(datum, dateFormat);
            orders.add(new Order((long) i, restaurants.get(indexRestoran), newMeals, deliverers.get(indexDel), newDate));
        }
        return orders;
    }


    public static void restoranNajskupljaDostava(Order[] orders) {

        BigDecimal max = BigDecimal.ZERO;
        int[] indexiNarudzbi = new int[orders.length];
        int brojac = 0; // Broji koliko narudžbi ima maksimalnu cijenu

        for (int i = 0; i < orders.length; i++) {
            BigDecimal ukCijena = BigDecimal.ZERO;

            // Računanje ukupne cijene narudžbe


            /*
            MOZE I OVO UMJESTO FOREACH
            for (int j = 0; j < orders[i].getMeals().size(); j++) {

                ukCijena = ukCijena.add(orders[i].getMeals().get(j).getPrice());
            }
            */

            for (Meal meal : orders[i].getMeals()) {
                ukCijena = ukCijena.add(meal.getPrice());
            }

            // Ako je pronađena nova maksimalna cijena, ažuriraj max i resetiraj brojač
            if (ukCijena.compareTo(max) > 0) {
                max = ukCijena;
                brojac = 0; // Resetiramo brojač jer imamo novu maksimalnu cijenu
                indexiNarudzbi[brojac] = i; // Spremamo trenutni indeks narudžbe
                brojac++;
            }
            // Ako je cijena jednaka trenutnoj maksimalnoj cijeni, dodaj indeks u niz
            else if (ukCijena.compareTo(max) == 0) {
                indexiNarudzbi[brojac] = i;
                brojac++;
            }
        }


        // Ispis svih restorana s najskupljom narudžbom bez duplikata
        System.out.println("Restorani s najskupljom narudžbom:");
        for (int i = 0; i < brojac; i++) {
            boolean ispisan = false;
            Order najskupljaNarudzba = orders[indexiNarudzbi[i]];

            // Provjeravamo da li je restoran već ispisan
            for (int j = 0; j < i; j++) {
                if (najskupljaNarudzba.getRestaurant().getName().equals(orders[indexiNarudzbi[j]].getRestaurant().getName())) {
                    ispisan = true;
                    break; // Ako je pronađen duplikat, prekidamo unutarnju petlju
                }
            }

            // Ako restoran nije već ispisan, ispisujemo ga
            if (!ispisan) {
                System.out.println("Ime restorana: " + najskupljaNarudzba.getRestaurant().getName());
                System.out.println("Adresa: " + najskupljaNarudzba.getRestaurant().getAddress().getCity());
                System.out.println("Cijena narudžbe: " + max);
                System.out.println("-----");
            }
        }
    }

    public void ispisOrder() {
        System.out.println("ID: " + getId() + " - Restoran " + getRestaurant().getName());
    }


}
