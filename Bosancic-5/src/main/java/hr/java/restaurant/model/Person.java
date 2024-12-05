package hr.java.restaurant.model;

import hr.java.restaurant.sort.ZaposleniciPlacaComparator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Person extends Entity {
    private String firstName;
    private String lastName;

    public Person(Long id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public static void najvecaPlacaZaposlenika(Person[] people) {
        System.out.println("Zaposlenik s najvećom plaćom: ");
        BigDecimal maks = BigDecimal.ZERO;
        Person osobaNajPlaca = null;
        for (int i = 0; i < people.length; i++) {
            Contract contract = null;
            if (people[i] instanceof Chef) {
                contract = ((Chef) people[i]).getContract();
            } else if (people[i] instanceof Waiter) {
                contract = ((Waiter) people[i]).getContract();
            } else if (people[i] instanceof Deliverer) {
                contract = ((Deliverer) people[i]).getContract();
            }

            if (contract.getSalary().compareTo(maks) > 0) {
                maks = contract.getSalary();
                osobaNajPlaca = people[i];
            }
        }
        if (osobaNajPlaca instanceof Chef) {
            ((Chef) osobaNajPlaca).ispisZaposlenik();
        } else if (osobaNajPlaca instanceof Waiter) {
            ((Waiter) osobaNajPlaca).ispisZaposlenik();
        } else if (osobaNajPlaca instanceof Deliverer) {
            ((Deliverer) osobaNajPlaca).ispisZaposlenik();
        }
    }

    public static void najduziUgovorZaposlenika(Person[] people) {
        System.out.println("Zaposlenik s najdužim ugovorom (koji je najranije započeo): ");
        long max = 0;
        Person najUgovor = null;

        for (int i = 0; i < people.length; i++) {
            Contract contract = null;
            if (people[i] instanceof Chef) {
                contract = ((Chef) people[i]).getContract();
            } else if (people[i] instanceof Waiter) {
                contract = ((Waiter) people[i]).getContract();
            } else if (people[i] instanceof Deliverer) {
                contract = ((Deliverer) people[i]).getContract();
            }
            long days1 = ChronoUnit.DAYS.between(contract.getStartDate(), contract.getEndDate());
            if (days1 > max) {
                najUgovor = people[i];
                max = days1;
            }
        }

        if (najUgovor instanceof Chef) {
            ((Chef) najUgovor).ispisZaposlenik();
        } else if (najUgovor instanceof Waiter) {
            ((Waiter) najUgovor).ispisZaposlenik();
        } else if (najUgovor instanceof Deliverer) {
            ((Deliverer) najUgovor).ispisZaposlenik();
        }
    }

    public static void najvecaPlacaZaposlenikaPoRestoranu(List<Restaurant> restaurants) {
        for (Restaurant restaurant : restaurants) {
            List<Person> zaposlenici = new ArrayList<>();
            zaposlenici.addAll(restaurant.getChefs());
            zaposlenici.addAll(restaurant.getWaiters());
            zaposlenici.addAll(restaurant.getDeliverers());

            if (zaposlenici.isEmpty()) {
                System.out.println("Restoran " + restaurant.getName() + " nema zaposlenika.");
                continue;
            }

            //Person person = Collections.max(zaposlenici, new ZaposleniciPlacaComparator());
            Person person = Collections.max(zaposlenici,
                    (p1, p2) -> p1.getSalaryZaposlenika().compareTo(p2.getSalaryZaposlenika()));
            System.out.print("Restoran " + restaurant.getName() + ": ");
            person.ispisZaposlenik();
        }
    }

    public static void zaposleniciSortiraniPoTrajanjuUgovora (List<Restaurant> restaurants) {
        for (Restaurant restaurant : restaurants) {
            List<Person> zaposlenici = new ArrayList<>();
            zaposlenici.addAll(restaurant.getChefs());
            zaposlenici.addAll(restaurant.getWaiters());
            zaposlenici.addAll(restaurant.getDeliverers());
            if (zaposlenici.isEmpty()) {
                System.out.println("Restoran " + restaurant.getName() + " nema zaposlenika.");
                continue;
            }

            zaposlenici.sort(new ZaposleniciPlacaComparator());
            restaurant.ispisRestoran();
            for (Person person : zaposlenici) {
                person.ispisZaposlenik();
            }
        }
    }


    public abstract BigDecimal getSalaryZaposlenika();

    public abstract long getUgovorTrajanjeZaposlenika();

    public abstract void ispisZaposlenik();
}
