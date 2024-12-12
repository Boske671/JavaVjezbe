package hr.java.restaurant.model;

import hr.java.production.main.Main;
import hr.java.restaurant.enumeration.ContractType;
import hr.java.utils.DataInput;
import hr.java.utils.InputValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;



/**
 * Klasa Waiter koja sadrzi razna svojstva za objekt
 * Sadrži metode poput inputWaiters, ispisWaiter
 */
public class Waiter extends Person {
    private Contract contract;
    private Bonus bonus;


    /**
     * Konstruktor za kreiranje objekta klase `Waiter`, koja nasljeđuje osnovne informacije
     * o osobi iz nadklase te dodaje podatke o ugovoru i bonusu specifične za konobara.
     *
     * @param id Jedinstveni identifikator konobara.
     * @param firstName Ime konobara.
     * @param lastName Prezime konobara.
     * @param contract Objekt klase `Contract` koji sadrži informacije o ugovoru konobara.
     * @param bonus Objekt klase `Bonus` koji sadrži podatke o bonusima i dodatnim primanjima konobara.
     */

    public Waiter(Long id, String firstName, String lastName, Contract contract, Bonus bonus) {
        super(id, firstName, lastName);
        this.contract = contract;
        this.bonus = bonus;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }



    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private Contract contract;
        private Bonus bonus;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withContract(Contract contract) {
            this.contract = contract;
            return this;
        }

        public Builder withBonus(Bonus bonus) {
            this.bonus = bonus;
            return this;
        }

        public Waiter build() {
            return new Waiter(id, firstName, lastName, contract, bonus);
        }
    }

    /**
     * Metoda za unos podataka o konobarima, uključujući ime, prezime, plaću, datum početka i završetka
     * ugovora, vrstu zaposlenja te iznos bonusa. Korisnik unosi podatke putem konzole.
     *
     * @param n Broj konobara koje korisnik želi unijeti.
     * @param sc Objekt klase `Scanner` koji se koristi za unos podataka s konzole.
     * @return Polje objekata tipa `Waiter` koje sadrži sve unesene konobare s njihovim podacima.
     */


    public static List<Waiter> inputWaiters(int n, Scanner sc) {
        Main.log.info("Pozvana metoda inputWaiters.");
        List<Waiter> Waiters = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Main.log.info("Upis "  + (i + 1) + ". Waitera: ");
            System.out.println((i + 1) + ". Waiter: ");
            System.out.println("Upiši ime waitera: ");
            String name = sc.nextLine();
            System.out.println("Upiši prezime waitera: ");
            String lastName = sc.nextLine();
            BigDecimal placa = DataInput.placaInput(sc);
            sc.nextLine();

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
            System.out.println("Upisi start date (dd.MM.yyyy.) : ");
            String datum = sc.nextLine();
            LocalDate startDate = LocalDate.parse(datum, dateFormat);
            System.out.println("Upisi end date (dd.MM.yyyy.) : ");
            datum = sc.nextLine();
            LocalDate endDate = LocalDate.parse(datum, dateFormat);

            System.out.println("Upisi 1 za FULL TIME ili 2 za PART TIME: ");
            int upis = InputValidator.integerValidator(sc);
            System.out.println("Upisi bonus: ");
            BigDecimal bonus = InputValidator.bigDecimalValidator(sc);
            sc.nextLine();
            ContractType contractType = null;
            if (upis == 1) {
                contractType = ContractType.FULL_TIME;
            } else if (upis == 2) {
                contractType = ContractType.PART_TIME;
            }
            Waiters.add(new Waiter((long) i, name, lastName, new Contract(placa, startDate, endDate, contractType), new Bonus(bonus)));

        }
        return Waiters;
    }


    public long getUgovorTrajanjeZaposlenika() {
        long ugovorTrajanje = java.time.temporal.ChronoUnit.DAYS.between(
                getContract().getStartDate(),
                getContract().getEndDate());
        return ugovorTrajanje;
    }

    public void ispisZaposlenik() {
        System.out.println(getClass().getSimpleName() + " - Ime: " + getFirstName() + " - Prezime: " + getLastName() + " - Placa: " + getContract().getSalary() + " - StartDate: " + getContract().getStartDate() + " - EndDate: " + getContract().getEndDate());
    }

    @Override
    public BigDecimal getSalaryAndBonusZaposlenika() {
        return getContract().getSalary().add(getBonus().bonus());
    }

    @Override
    public BigDecimal getSalaryZaposlenika() {
        return getContract().getSalary();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Waiter waiter = (Waiter) o;
        return Objects.equals(contract, waiter.contract) && Objects.equals(bonus, waiter.bonus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contract, bonus);
    }
}
