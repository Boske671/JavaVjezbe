package hr.java.production.main;

import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.IllegalFormatCodePointException;
import java.util.Scanner;
import static hr.java.restaurant.model.Restaurant.ispisRestoran;
import java.time.Period;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        Category[] categories = inputCategories(3, sc);
        Ingredient[] ingredients = inputIngredients(3, categories, sc);
        Meal[] meals = inputMeals(3, categories, ingredients, sc);
        Chef[] chefs = inputChefs(3, sc);
        Deliverer[] deliverers = inputDeliverers(3, sc);
        Waiter[] waiters = inputWaiters(3, sc);
        Restaurant[] restaurants = inputRestaurants(3, meals, chefs, waiters, deliverers, sc);
        Order[] orders = inputOrders(3, restaurants, meals, deliverers, sc);


        /*
        Chef[] chefs = inputChefs(1, sc);
        Deliverer[] deliverers = inputDeliverers(1, sc);
        Person people[] = new Person[1];
        people[0] = chefs[0];
        people[1] = deliverers[0];
        people[2] = waiters[0];

        najvecaPlacaZaposlenika(people);
        najduziUgovorZaposlenika(people);


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
        ispisVrstiJela(meals123);

        */
        sc.close();
    }

    private static void najvecaPlacaZaposlenika(Person[] people)
    {
        System.out.println("Zaposlenik s najvećom plaćom: ");
        BigDecimal maks = BigDecimal.ZERO;
        Person osobaNajPlaca = null;
        for (int i = 0; i < people.length; i++)
        {
            Contract contract = null;
            if (people[i] instanceof Chef) {
                contract = ((Chef) people[i]).getContract();
            } else if (people[i] instanceof Waiter) {
                contract = ((Waiter) people[i]).getContract();
            }
            else if (people[i] instanceof Deliverer) {
                contract = ((Deliverer) people[i]).getContract();
            }

            if (contract.getSalary().compareTo(maks) > 0)
            {
                maks = contract.getSalary();
                osobaNajPlaca = people[i];
            }
        }
        if (osobaNajPlaca instanceof Chef) {
            ((Chef) osobaNajPlaca).ispisChef();
        } else if (osobaNajPlaca instanceof Waiter) {
            ((Waiter) osobaNajPlaca).ispisWaiter();
        }
        else if (osobaNajPlaca instanceof Deliverer) {
            ((Deliverer) osobaNajPlaca).ispisDeliverer();
        }
    }

    private static void najduziUgovorZaposlenika(Person[] people)
    {
        System.out.println("Zaposlenik s najdužim ugovorom (koji je najranije započeo): ");
        long max = 0;
        Person najUgovor = null;

        for (int i = 0; i < people.length; i++)
        {
            Contract contract = null;
            if (people[i] instanceof Chef) {
                contract = ((Chef) people[i]).getContract();
            } else if (people[i] instanceof Waiter) {
                contract = ((Waiter) people[i]).getContract();
            }
            else if (people[i] instanceof Deliverer) {
                contract = ((Deliverer) people[i]).getContract();
            }
            long days1 = ChronoUnit.DAYS.between(contract.getStartDate(), contract.getEndDate());
            if (days1 > max) {
                najUgovor = people[i];
                max = days1;
            }
        }

        if (najUgovor instanceof Chef) {
            ((Chef) najUgovor).ispisChef();
        } else if (najUgovor instanceof Waiter) {
            ((Waiter) najUgovor).ispisWaiter();
        }
        else if (najUgovor instanceof Deliverer) {
            ((Deliverer) najUgovor).ispisDeliverer();
        }
    }


    private static void ispisVrstiJela(Meal[] meals)
    {
        BigDecimal maksDec = BigDecimal.ZERO;
        BigDecimal minDec = BigDecimal.valueOf(999999);
        Meal max = null;
        Meal min = null;
        for (int i = 0; i < meals.length; i++)
        {
            BigDecimal brkal = BigDecimal.ZERO;
            for (int j = 0; j < meals[i].getIngredients().length; j++)
            {
                brkal = brkal.add(meals[i].getIngredients()[j].getKcal());
            }

            if (brkal.compareTo(maksDec) == 1)
            {
                max = meals[i];
                maksDec = brkal;
            }
            if (brkal.compareTo(minDec) == -1)
            {
                min = meals[i];
                minDec = brkal;
            }
        }
        System.out.println("MIN: ");
        if (min instanceof VeganMeal)
        {
            ((VeganMeal) min).ispisVeganMeal();
        } else if (min instanceof VegetarianMeal)
        {
            ((VegetarianMeal) min).ispisVegetarianMeal();
        } else if (min instanceof MeatMeal)
        {
            ((MeatMeal) min).ispisMeatMeal();
        }
        System.out.println("MAX: ");
        if (max instanceof VeganMeal)
        {
            ((VeganMeal) max).ispisVeganMeal();
        } else if (max instanceof VegetarianMeal)
        {
            ((VegetarianMeal) max).ispisVegetarianMeal();
        } else if (max instanceof MeatMeal)
        {
            ((MeatMeal) max).ispisMeatMeal();
        }
    }

    private static Category[] inputCategories(int n, Scanner sc)
    {
        Category[] categories = new Category[n];
        for (int i = 0; i < n; i++)
        {
            System.out.println((i + 1) + ". kategorija: ");


            System.out.println("Upiši ime: ");
            String name = sc.nextLine();
            System.out.println("Upiši opis: ");
            String description = sc.nextLine();
        }
        return categories;
    }

    private static Ingredient[] inputIngredients(int n, Category[] categories, Scanner sc)
    {
        Ingredient[] ingredients = new Ingredient[n];
        for (int i = 0; i < n; i++)
        {
            System.out.println((i + 1) + ". sastojak: ");

            System.out.println("Upisi ime: ");
            String name = sc.nextLine();

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


            System.out.println("Upisi metodu pripreme: ");
            String metoda = sc.nextLine();
            ingredients[i] = new Ingredient((long) i, name, categories[index], brKal, metoda);
        }
        return ingredients;
    }


    private static Meal[] inputMeals(int n, Category[] categories, Ingredient[] ingredients, Scanner sc)
    {
        Meal[] meals = new Meal[n];
        for (int i = 0; i < n; i++)
        {

            System.out.println((i + 1) + ". jelo: ");
            System.out.println("Upiši ime: ");
            String name = sc.nextLine();


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
            meals[i] = new Meal((long) i, name, categories[indexKat], sastojci, cijena);
        }
        return meals;
    }

    private static Chef[] inputChefs(int n, Scanner sc)
    {
        Chef[] chefs = new Chef[n];
        for (int i = 0; i < n; i++)
        {
            System.out.println((i + 1) + ". chef: ");
            System.out.println("Upiši ime: ");
            String name = sc.nextLine();
            System.out.println("Upiši prezime: ");
            String lastName = sc.nextLine();

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

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
            System.out.println("Upisi start date (dd.MM.yyyy.) : ");
            String datum = sc.nextLine();
            LocalDate startDate = LocalDate.parse(datum, dateFormat);
            System.out.println("Upisi end date (dd.MM.yyyy.) : ");
            datum = sc.nextLine();
            LocalDate endDate = LocalDate.parse(datum, dateFormat);

            System.out.println("Upisi 1 za FULL TIME ili 2 za PART TIME: ");
            int upis = sc.nextInt();
            sc.nextLine();
            System.out.println("Upisi bonus: ");
            BigDecimal bonus = sc.nextBigDecimal();
            sc.nextLine();
            if (upis == 1)
            {
                chefs[i] = new Chef((long) i, name, lastName, new Contract(placa, startDate, endDate, "FULL_TIME"), new Bonus(bonus));
            }
            else if (upis == 2)
            {
                chefs[i] = new Chef((long) i, name, lastName, new Contract(placa, startDate, endDate, "PART_TIME"), new Bonus(bonus));
            }
        }
        return chefs;
    }

    private static Deliverer[] inputDeliverers(int n, Scanner sc)
    {
        Deliverer[] Deliverers = new Deliverer[n];
        for (int i = 0; i < n; i++)
        {
            System.out.println((i + 1) + ". Deliverer: ");
            System.out.println("Upiši ime: ");
            String name = sc.nextLine();

            System.out.println("Upiši prezime: ");
            String lastName = sc.nextLine();

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

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
            System.out.println("Upisi start date (dd.MM.yyyy.) : ");
            String datum = sc.nextLine();
            LocalDate startDate = LocalDate.parse(datum, dateFormat);
            System.out.println("Upisi end date (dd.MM.yyyy.) : ");
            datum = sc.nextLine();
            LocalDate endDate = LocalDate.parse(datum, dateFormat);

            System.out.println("Upisi 1 za FULL TIME ili 2 za PART TIME: ");
            int upis = sc.nextInt();
            sc.nextLine();
            System.out.println("Upisi bonus: ");
            BigDecimal bonus = sc.nextBigDecimal();
            sc.nextLine();
            if (upis == 1)
            {
                Deliverers[i] = new Deliverer((long) i, name, lastName, new Contract(placa, startDate, endDate, "FULL_TIME"), new Bonus(bonus));
            }
            else if (upis == 2)
            {
                Deliverers[i] = new Deliverer((long) i, name, lastName, new Contract(placa, startDate, endDate, "PART_TIME"), new Bonus(bonus));
            }

        }
        return Deliverers;
    }

    private static Waiter[] inputWaiters(int n, Scanner sc)
    {
        Waiter[] Waiters = new Waiter[n];
        for (int i = 0; i < n; i++)
        {
            System.out.println((i + 1) + ". Waiter: ");
            System.out.println("Upiši ime: ");
            String name = sc.nextLine();

            System.out.println("Upiši prezime: ");
            String lastName = sc.nextLine();



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

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
            System.out.println("Upisi start date (dd.MM.yyyy.) : ");
            String datum = sc.nextLine();
            LocalDate startDate = LocalDate.parse(datum, dateFormat);
            System.out.println("Upisi end date (dd.MM.yyyy.) : ");
            datum = sc.nextLine();
            LocalDate endDate = LocalDate.parse(datum, dateFormat);

            System.out.println("Upisi 1 za FULL TIME ili 2 za PART TIME: ");
            int upis = sc.nextInt();
            sc.nextLine();
            System.out.println("Upisi bonus: ");
            BigDecimal bonus = sc.nextBigDecimal();
            sc.nextLine();
            if (upis == 1)
            {
                Waiters[i] = new Waiter((long) i, name, lastName, new Contract(placa, startDate, endDate, "FULL_TIME"), new Bonus(bonus));
            }
            else if (upis == 2)
            {
                Waiters[i] = new Waiter((long) i, name, lastName, new Contract(placa, startDate, endDate, "PART_TIME"), new Bonus(bonus));
            }

        }
        return Waiters;
    }


    private static Restaurant[] inputRestaurants(int n, Meal[] meals, Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers, Scanner sc)
    {
        Restaurant[] restaurants = new Restaurant[n];
        for (int i = 0; i < n; i++)
        {

            System.out.println("UPISI PODATKE ZA " + (i + 1) + ". RESTORAN: ");
            System.out.println("Upiši ime: ");
            String name = sc.nextLine();


            System.out.println("Upiši ime ulice: ");
            String street = sc.nextLine();


            System.out.println("Upisi kucni broj: ");
            String houseNumber = sc.nextLine();

            System.out.println("Upisi grad: ");
            String city = sc.nextLine();

            System.out.println("Upisi postanski broj: ");
            String postalCode = sc.nextLine();

            Address address = new Address((long) i, street, houseNumber, city, postalCode);


            System.out.println("Upisi broj jela: ");
            int brJela = sc.nextInt();
            sc.nextLine();
            Meal novaJela[] = new Meal[brJela];
            System.out.println("Odaberi index od 0 do " + (meals.length - 1));
            for (int k = 0; k < meals.length; k++)
            {
                System.out.print("index: " + k + " - ");
                meals[k].ispisMeal();
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
                chefs[i].ispisChef();
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
                waiters[k].ispisWaiter();
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
                deliverers[k].ispisDeliverer();
            }
            for (int j = 0; j < brDeliverer; j++)
            {
                int index = sc.nextInt();
                sc.nextLine();
                newDeliverers[j] = deliverers[index];
            }
            restaurants[i] = new Restaurant((long) i, name, address, novaJela, noviChefs, newWaiters, newDeliverers);
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
                meals[k].ispisMeal();
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
                deliverers[k].ispisDeliverer();
            }
            int indexDel = sc.nextInt();
            sc.nextLine();

            System.out.println("Upisi datum (dd.MM.yyyy. HH:mm) : ");
            String datum = sc.nextLine();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
            LocalDateTime newDate = LocalDateTime.parse(datum, dateFormat);
            orders[i] = new Order((long) i, restaurants[indexRestoran], novaJela, deliverers[indexDel], newDate);
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


/*
imetKat1
opisKat1
imeKat2
opisKat2
imeKat3
opisKat3
imeSas1
0
100
opisSastojka1
imeSas1
1
200
opisSastojka2
imeSas3
2
300
opisSastojka3
imeJelo1
0
1
1
120
imeJelo2
2
1
2
200
imeJelo3
2
1
1
300
imeChef1
prezimeChef1
10000
01.01.2022.
05.05.2024.
1
500
imeChef2
prezimeChef2
11000
20.5.2021.
10.04.2023.
2
600
imeChef3
prezimeChef3
12000
1.5.2019.
15.04.2024.
2
600
imeDeliverer1
prezimeDeliverer1
6000
1.8.2015.
27.12.2018.
1
200
imeDeliverer2
prezimeDeliverer2
5000
2.9.2017.
02.12.2018.
2
200
imeDeliverer3
prezimeDeliverer3
4000
1.8.2012.
27.12.2015.
1
400
imeWaiter1
prezimeWaiter1
6000
1.8.2015.
27.12.2016.
1
400
imeWaiter2
prezimeWaiter2
6000
1.8.2016.
20.10.2018.
1
100
imeWaiter3
prezimeWaiter3
5000
1.8.2012.
27.12.2016.
1
400
imeRestoran1
restoranImeUlice1
restoranKucniBroj1
restoranGrad1
restoranPosBr1
1
0
1
1
1
1
1
1
imeRestoran2
restoranImeUlice2
restoranKucniBroj2
restoranGrad2
restoranPosBr2
1
0
1
1
1
1
1
1
imeRestoran3
restoranImeUlice3
restoranKucniBroj23
restoranGrad3
restoranPosBr3
1
0
1
1
1
1
1
1
0
1
1
1
05.05.2004.
0
1
1
1
10.10.2024.
0
1
1
1
05.05.2008.
 */

