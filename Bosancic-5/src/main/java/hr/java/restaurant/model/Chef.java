package hr.java.restaurant.model;

import hr.java.production.main.Main;
import hr.java.restaurant.enumeration.ContractType;
import hr.java.utils.DataInput;
import hr.java.utils.InputValidator;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


/**
 * Klasa "Chef" koja sadrži parametre za opis objekta Chef
 */
public class Chef extends Person {

    private Contract contract;
    private Bonus bonus;


    /**
     * Konstruktor za kreiranje objekta klase `Chef`, koja nasljeđuje osnovne informacije
     * iz nadklase te dodaje detalje o ugovoru i bonusu specifične za kuhara.
     *
     * @param id        Jedinstveni identifikator kuhara.
     * @param firstName Ime kuhara.
     * @param lastName  Prezime kuhara.
     * @param contract  Objekt klase `Contract` koji sadrži informacije o ugovoru kuhara.
     * @param bonus     Objekt klase `Bonus` koji sadrži podatke o bonusima i dodatnim primanjima kuhara.
     */

    public Chef(Long id, String firstName, String lastName, Contract contract, Bonus bonus) {
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

    @Override
    public BigDecimal getSalaryZaposlenika() {
        return getContract().getSalary().add(getBonus().bonus());
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

        public Chef build() {
            return new Chef(id, firstName, lastName, contract, bonus);
        }
    }

    /**
     * Metoda za unos podataka o kuharima, uključujući ime, prezime, plaću, datum početka i završetka
     * ugovora, vrstu zaposlenja te iznos bonusa. Korisnik unosi podatke putem konzole.
     *
     * @param n Broj kuhara koje korisnik želi unijeti.
     * @param sc Objekt klase `Scanner` koji se koristi za unos podataka s konzole.
     * @return Polje objekata tipa `Chef` koje sadrži sve unesene kuhare s njihovim podacima.
     */


    public static List<Chef> inputChefs(int n, Scanner sc) {
        Main.log.info("Pozvana metoda inputChefs.");
        List<Chef> chefs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + ". chef: ");
            Main.log.info("Upis imena " + (i + 1) + ". chefa: ");
            System.out.println("Upiši ime chefa: ");
            String name = sc.nextLine();
            System.out.println("Upiši prezime chefa: ");
            Main.log.info("Upis prezimena " + (i + 1) + ". chefa: ");
            String lastName = sc.nextLine();


            Main.log.info("Upis place " + (i + 1) + ". chefa: ");
            BigDecimal placa = DataInput.placaInput(sc);
            sc.nextLine();


            Main.log.info("Upis start datea " + (i + 1) + ". chefa: ");
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
            System.out.println("Upisi start date (dd.MM.yyyy.) : ");
            String datum = sc.nextLine();
            LocalDate startDate = LocalDate.parse(datum, dateFormat);
            Main.log.info("Upis end datea " + (i + 1) + ". chefa: ");
            System.out.println("Upisi end date (dd.MM.yyyy.) : ");
            datum = sc.nextLine();
            LocalDate endDate = LocalDate.parse(datum, dateFormat);

            Main.log.info("Upis 1 ili 2 FULL TIME ili 2 za PART TIME " + (i + 1) + ". chefa: ");
            System.out.println("Upisi 1 za FULL TIME ili 2 za PART TIME: ");
            int upis = InputValidator.integerValidator(sc);

            Main.log.info("Upis bonusa " + (i + 1) + ". chefa: ");
            System.out.println("Upisi bonus: ");

            BigDecimal bonus = InputValidator.bigDecimalValidator(sc);
            sc.nextLine();
            ContractType contractType = null;
            if (upis == 1) {
                contractType = ContractType.FULL_TIME;
            } else if (upis == 2) {
                contractType = ContractType.PART_TIME;
            }
            chefs.add(new Chef((long) i, name, lastName, new Contract(placa, startDate, endDate, contractType), new Bonus(bonus)));
        }
        return chefs;
    }

    public long getUgovorTrajanjeZaposlenika() {
        long ugovorTrajanje = java.time.temporal.ChronoUnit.DAYS.between(
                getContract().getStartDate(),
                getContract().getEndDate());
        return ugovorTrajanje;
    }
    public void ispisZaposlenik() {
        System.out.println(getClass().getName() + " - Ime: " + getFirstName() + " - Prezime: " + getLastName() + " - Placa: " + getContract().getSalary() + " - StartDate: " + getContract().getStartDate() + " - EndDate: " + getContract().getEndDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chef chef = (Chef) o;
        return Objects.equals(contract, chef.contract) && Objects.equals(bonus, chef.bonus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contract, bonus);
    }
}
