package hr.java.production.main;

import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.IllegalFormatCodePointException;
import java.util.Scanner;

import static hr.java.restaurant.model.Chef.ispisChef;
import static hr.java.restaurant.model.Deliverer.ispisDeliverer;
import static hr.java.restaurant.model.Meal.ispisMeal;
import static hr.java.restaurant.model.Restaurant.ispisRestoran;
import static hr.java.restaurant.model.Waiter.ispisWaiter;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        Category[] categories = inputCategories(3, sc);
        Ingredient[] ingredients = inputIngredients(5, categories, sc);
        Meal[] meals = inputMeals(3, categories, ingredients, sc);
        Chef[] chefs = inputChefs(3, sc);
        Deliverer[] deliverers = inputDeliverers(3, sc);
        Waiter[] waiters = inputWaiters(3, sc);
        Restaurant[] restaurants = inputRestaurants(3, meals, chefs, waiters, deliverers, sc);
        Order[] orders = inputOrders(3, restaurants, meals, deliverers, sc);
    }

    private static Category[] inputCategories(int n, Scanner sc)
    {
        Category[] categories = new Category[n];
        for (int i = 0; i < n; i++)
        {
            System.out.println((i + 1) + ". kategorija: ");

            String name;
            do
            {
                System.out.println("Upiši ime: ");
                name = sc.nextLine();
                if (provjeraStringa(name) == false)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (provjeraStringa(name) == false);

            String description;
            do
            {
                System.out.println("Upiši opis: ");
                description = sc.nextLine();
                if (provjeraStringa(description) == false)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (provjeraStringa(description) == false);
            categories[i] = new Category(name, description);
        }
        return categories;
    }

    private static Ingredient[] inputIngredients(int n, Category[] categories, Scanner sc)
    {
        Ingredient[] ingredients = new Ingredient[n];
        for (int i = 0; i < n; i++)
        {
            System.out.println((i + 1) + ". sastojak: ");

            String name;
            do
            {
                System.out.println("Upisi ime: ");
                name = sc.nextLine();
                if (provjeraStringa(name) == false)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (provjeraStringa(name) == false);

            System.out.println("Upisi index od 0 do " + (categories.length - 1)+ ", " + (i + 1) + ". sastojka: ");

            for (int j = 0; j < categories.length; j++)
            {
                System.out.println("Index: " + j + " - ime: " + categories[j].getName() + " - opis: " + categories[j].getDescription());
            }


            int index;
            do
            {
                index = sc.nextInt();
                sc.nextLine();
                if (index < 0 || index > categories.length)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (index < 0 || index > categories.length);

            System.out.println("Upisi broj kalorija: ");
            BigDecimal brKal = sc.nextBigDecimal();
            sc.nextLine();


            String metoda;
            do
            {
                System.out.println("Upisi metodu pripreme: ");
                metoda = sc.nextLine();
                if (provjeraStringa(metoda) == false)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (provjeraStringa(metoda) == false);
            ingredients[i] = new Ingredient(name, categories[index], brKal, metoda);
        }
        return ingredients;
    }


    private static Meal[] inputMeals(int n, Category[] categories, Ingredient[] ingredients, Scanner sc)
    {
        Meal[] meals = new Meal[n];
        for (int i = 0; i < n; i++)
        {

            System.out.println((i + 1) + ". jelo: ");
            String name;
            do
            {
                System.out.println("Upiši ime: ");
                name = sc.nextLine();
                if (provjeraStringa(name) == false)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (provjeraStringa(name) == false);


            System.out.println("Upisi index od 0 do " + (categories.length - 1) + ", " + (i + 1) + ". jela: ");
            for (int j = 0; j < categories.length; j++)
            {
                System.out.println("Index: " + j + " - ime: " + categories[j].getName() + " - opis: " + categories[j].getDescription());
            }


            int indexKat;
            do
            {
                indexKat = sc.nextInt();
                sc.nextLine();
                if (indexKat < 0 || indexKat > categories.length)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (indexKat < 0 || indexKat > categories.length);

            System.out.println("Upisi broj sastojaka " + (i + 1) + ". jela: ");
            int brSastojaka = sc.nextInt();
            sc.nextLine();

            for (int j = 0; j < ingredients.length; j++)
            {
                System.out.println("Index: " + j + " - ime: " + ingredients[j].getName() + " - kategorija: " + ingredients[j].getCategory().getName() + " - kalorije: " + ingredients[j].getKcal() + " - metoda pripreme: " + ingredients[j].getPreparationMethod());
            }

            Ingredient[] sastojci = new Ingredient[brSastojaka];


            for (int j = 0; j < brSastojaka; j++)
            {
                System.out.println("Upisi index od 0 do " + (ingredients.length - 1)+  ", " + (i + 1) + ". jela: ");
                int indexSastojka = sc.nextInt();
                sc.nextLine();
                sastojci[j] = ingredients[indexSastojka];
            }
            System.out.println("Upisi cijenu: " + (i + 1) + ". jela: ");
            BigDecimal cijena = sc.nextBigDecimal();
            sc.nextLine();
            meals[i] = new Meal(name, categories[indexKat], sastojci, cijena);
        }
        return meals;
    }

    private static Chef[] inputChefs(int n, Scanner sc)
    {
        Chef[] chefs = new Chef[n];
        for (int i = 0; i < n; i++)
        {
            System.out.println((i + 1) + ". chef: ");
            String name;
            do
            {
                System.out.println("Upiši ime: ");
                name = sc.nextLine();
                if (provjeraStringa(name) == false)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (provjeraStringa(name) == false);


            String lastName;
            do
            {
                System.out.println("Upiši prezime: ");
                lastName = sc.nextLine();
                if (provjeraStringa(lastName) == false)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (provjeraStringa(lastName) == false);


            BigDecimal placa;
            do
            {
                System.out.println("Upisi placu: ");
                placa = sc.nextBigDecimal();
                sc.nextLine();

                if (placa.compareTo(BigDecimal.ZERO) < 0)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (placa.compareTo(BigDecimal.ZERO) < 0);

            chefs[i] = new Chef(name, lastName, placa);
        }
        return chefs;
    }

    private static Deliverer[] inputDeliverers(int n, Scanner sc)
    {
        Deliverer[] Deliverers = new Deliverer[n];
        for (int i = 0; i < n; i++)
        {
            System.out.println((i + 1) + ". Deliverer: ");
            String name;
            do
            {
                System.out.println("Upiši ime: ");
                name = sc.nextLine();
                if (provjeraStringa(name) == false)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (provjeraStringa(name) == false);


            String lastName;
            do
            {
                System.out.println("Upiši prezime: ");
                lastName = sc.nextLine();
                if (provjeraStringa(lastName) == false)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (provjeraStringa(lastName) == false);


            BigDecimal placa;
            do
            {
                System.out.println("Upisi placu: ");
                placa = sc.nextBigDecimal();
                sc.nextLine();

                if (placa.compareTo(BigDecimal.ZERO) < 0)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (placa.compareTo(BigDecimal.ZERO) < 0);

            Deliverers[i] = new Deliverer(name, lastName, placa);
        }
        return Deliverers;
    }

    private static Waiter[] inputWaiters(int n, Scanner sc)
    {
        Waiter[] Waiters = new Waiter[n];
        for (int i = 0; i < n; i++)
        {
            System.out.println((i + 1) + ". Waiter: ");
            String name;
            do
            {
                System.out.println("Upiši ime: ");
                name = sc.nextLine();
                if (provjeraStringa(name) == false)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (provjeraStringa(name) == false);


            String lastName;
            do
            {
                System.out.println("Upiši prezime: ");
                lastName = sc.nextLine();
                if (provjeraStringa(lastName) == false)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (provjeraStringa(lastName) == false);


            BigDecimal placa;
            do
            {
                System.out.println("Upisi placu: ");
                placa = sc.nextBigDecimal();
                sc.nextLine();

                if (placa.compareTo(BigDecimal.ZERO) < 0)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (placa.compareTo(BigDecimal.ZERO) < 0);

            Waiters[i] = new Waiter(name, lastName, placa);
        }
        return Waiters;
    }


    private static Restaurant[] inputRestaurants(int n, Meal[] meals, Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers, Scanner sc)
    {
        Restaurant[] restaurants = new Restaurant[n];
        for (int i = 0; i < n; i++)
        {

            System.out.println("UPISI PODATKE ZA " + (i + 1) + ". RESTORAN: ");
            String name;
            do
            {
                System.out.println("Upiši ime: ");
                name = sc.nextLine();
                if (provjeraStringa(name) == false)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (provjeraStringa(name) == false);


            String street;
            do
            {
                System.out.println("Upiši ime ulice: ");
                street = sc.nextLine();
                if (provjeraStringa(street) == false)
                {
                    System.out.println("Pogrešan upis! Pokušajte ponovo.");
                }
            } while (provjeraStringa(street) == false);

            System.out.println("Upisi kucni broj: ");
            String houseNumber = sc.nextLine();

            System.out.println("Upisi grad: ");
            String city = sc.nextLine();

            System.out.println("Upisi postanski broj: ");
            String postalCode = sc.nextLine();

            Address address = new Address(street, houseNumber, city, postalCode);


            System.out.println("Upisi broj jela: ");
            int brJela = sc.nextInt();
            sc.nextLine();
            Meal novaJela[] = new Meal[brJela];
            System.out.println("Odaberi index od 0 do " + (meals.length - 1));
            for (int k = 0; k < meals.length; k++)
            {
                System.out.print("index: " + k + " - ");
                ispisMeal(meals[k]);
            }
            for (int j = 0; j < brJela; j++)
            {
                int index = sc.nextInt();
                sc.nextLine();
                novaJela[j] = meals[index];
            }

            System.out.println("Upisi broj chefova: ");
            int brChef = sc.nextInt();
            sc.nextLine();
            Chef noviChefs[] = new Chef[brChef];
            System.out.println("Odaberi index od 0 do " + (chefs.length - 1));
            for (int k = 0; k < chefs.length; k++)
            {
                System.out.print("index: " + k + " - ");
                ispisChef(chefs[k]);
            }
            for (int j = 0; j < brChef; j++)
            {
                int index = sc.nextInt();
                sc.nextLine();
                noviChefs[j] = chefs[index];
            }

            System.out.println("Upisi broj konobara: ");
            int brWaiter = sc.nextInt();
            sc.nextLine();
            Waiter[] newWaiters = new Waiter[brWaiter];
            System.out.println("Odaberi index od 0 do " + (waiters.length - 1));
            for (int k = 0; k < waiters.length; k++)
            {
                System.out.print("index: " + k + " - ");
                ispisWaiter(waiters[k]);
            }
            for (int j = 0; j < brWaiter; j++)
            {
                int index = sc.nextInt();
                sc.nextLine();
               newWaiters[j] = waiters[index];
            }

            System.out.println("Upisi broj dostavljaca: ");
            int brDeliverer = sc.nextInt();
            sc.nextLine();
            Deliverer[] newDeliverers = new Deliverer[brDeliverer];
            System.out.println("Odaberi index od 0 do " + (deliverers.length - 1)   );
            for (int k = 0; k < deliverers.length; k++)
            {
                System.out.print("index: " + k + " - ");
                ispisDeliverer(deliverers[k]);
            }
            for (int j = 0; j < brDeliverer; j++)
            {
                int index = sc.nextInt();
                sc.nextLine();
                newDeliverers[j] = deliverers[index];
            }
            restaurants[i] = new Restaurant(name, address, novaJela, noviChefs, newWaiters, newDeliverers);
        }
        return restaurants;
    }


    private static Order[] inputOrders(int n, Restaurant[] restaurants, Meal[] meals, Deliverer[] deliverers, Scanner sc)
    {
        Order[] orders = new Order[n];

        for (int i = 0; i < n; i++)
        {
            System.out.println((i + 1) + ". narudzba: ");
            System.out.println("Upisi index od 0 do " + (restaurants.length - 1) + " za restoran: ");
            for (int k = 0; k < meals.length; k++)
            {
                System.out.print("index: " + k + " - ");
                ispisRestoran(restaurants[k]);
            }
            int indexRestoran = sc.nextInt();
            sc.nextLine();

            System.out.println("Upisi broj jela: ");
            int brJela = sc.nextInt();
            sc.nextLine();
            Meal novaJela[] = new Meal[brJela];
            System.out.println("Odaberi index od 0 do " + (meals.length - 1));
            for (int k = 0; k < meals.length; k++)
            {
                System.out.print("index: " + k + " - ");
                ispisMeal(meals[k]);
            }
            for (int j = 0; j < brJela; j++)
            {
                int index = sc.nextInt();
                sc.nextLine();
                novaJela[j] = meals[index];
            }


            System.out.println("Upisi broj dostavljaca: ");
            for (int k = 0; k < deliverers.length; k++)
            {
                System.out.print("index: " + k + " - ");
                ispisDeliverer(deliverers[k]);
            }
            int indexDel = sc.nextInt();
            sc.nextLine();

            System.out.println("Upisi datum (dd.MM.yyyy. HH:mm) : ");
            String datum = sc.nextLine();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
            LocalDateTime newDate = LocalDateTime.parse(datum, dateFormat);
            orders[i] = new Order(restaurants[indexRestoran], novaJela, deliverers[indexDel], newDate);
        }
        sc.close();
        return orders;
    }

    private static void restoranNajskupljaDostava(Order[] orders)
    {
        BigDecimal max = BigDecimal.ZERO;
        int[] indexiNarudzbi = new int[orders.length];
        int brojac = 0; // Broji koliko narudžbi ima maksimalnu cijenu

        for (int i = 0; i < orders.length; i++)
        {
            BigDecimal ukCijena = BigDecimal.ZERO;

            // Računanje ukupne cijene narudžbe
            for (int j = 0; j < orders[i].getMeals().length; j++)
            {
                ukCijena = ukCijena.add(orders[i].getMeals()[j].getPrice());
            }

            // Ako je pronađena nova maksimalna cijena, ažuriraj max i resetiraj brojač
            if (ukCijena.compareTo(max) > 0)
            {
                max = ukCijena;
                brojac = 0; // Resetiramo brojač jer imamo novu maksimalnu cijenu
                indexiNarudzbi[brojac] = i; // Spremamo trenutni indeks narudžbe
                brojac++;
            }
            // Ako je cijena jednaka trenutnoj maksimalnoj cijeni, dodaj indeks u niz
            else if (ukCijena.compareTo(max) == 0)
            {
                indexiNarudzbi[brojac] = i;
                brojac++;
            }
        }


        // Ispis svih restorana s najskupljom narudžbom bez duplikata
        System.out.println("Restorani s najskupljom narudžbom:");
        for (int i = 0; i < brojac; i++)
        {
            boolean ispisan = false;
            Order najskupljaNarudzba = orders[indexiNarudzbi[i]];

            // Provjeravamo da li je restoran već ispisan
            for (int j = 0; j < i; j++)
            {
                if (najskupljaNarudzba.getRestaurant().getName().equals(orders[indexiNarudzbi[j]].getRestaurant().getName()))
                {
                    ispisan = true;
                    break; // Ako je pronađen duplikat, prekidamo unutarnju petlju
                }
            }

            // Ako restoran nije već ispisan, ispisujemo ga
            if (!ispisan)
            {
                System.out.println("Ime restorana: " + najskupljaNarudzba.getRestaurant().getName());
                System.out.println("Adresa: " + najskupljaNarudzba.getRestaurant().getAddress());
                System.out.println("Cijena narudžbe: " + max);
                System.out.println("-----");
            }
        }
    }


    private static void dostavljacNajviseDostava(Order[] orders, Deliverer[] deliverers)
    {
        int indexiDostavljaca[] = new int[deliverers.length];
        for (int i = 0; i < orders.length; i++)
        {
            for (int j = 0; j < deliverers.length; j++)
            {
                if (orders[i].getDeliverer().equals(deliverers[j]))
                {
                    indexiDostavljaca[j]++;
                    break;
                }
            }
        }

        // Pronađi maksimalni broj dostava
        int maxDostave = 0;
        for (int i = 0; i < indexiDostavljaca.length; i++)
        {
            if (indexiDostavljaca[i] > maxDostave)
            {
                maxDostave = indexiDostavljaca[i];
            }
        }

        // Ispis svih dostavljača s maksimalnim brojem dostava
        System.out.println("Dostavljači s najviše dostava (" + maxDostave + " dostava):");
        for (int i = 0; i < indexiDostavljaca.length; i++)
        {
            if (indexiDostavljaca[i] == maxDostave)
            {
                System.out.println("Ime: " + deliverers[i].getFirstName() + " - Prezime: " + deliverers[i].getLastName());
            }
        }
    }


    private static boolean provjeraStringa(String upis)
    {
        /*if (upis.matches("^[a-zA-Z.!?,; ]+$"))
        {
            return true;
        } else
        {
            return false;
        }
         */
        return true;
    }
}

