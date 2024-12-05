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
 * Klasa Deliverer koja sadrzi razna svojstva za objekt
 * Sadrži metode poput inputDeliverers, ispisDeliverer
 */
public class Deliverer extends Person {
    private Contract contract;
    private Bonus bonus;


    /**
     * Konstruktor za kreiranje objekta klase `Deliverer`, koja nasljeđuje osnovne informacije
     * o osobi iz nadklase te dodaje podatke o ugovoru i bonusu specifične za dostavljača.
     *
     * @param id        Jedinstveni identifikator dostavljača.
     * @param firstName Ime dostavljača.
     * @param lastName  Prezime dostavljača.
     * @param contract  Objekt klase `Contract` koji sadrži informacije o ugovoru dostavljača.
     * @param bonus     Objekt klase `Bonus` koji sadrži podatke o bonusima i dodatnim primanjima dostavljača.
     */

    public Deliverer(Long id, String firstName, String lastName, Contract contract, Bonus bonus) {
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

        private Builder withBonus(Bonus bonus) {
            this.bonus = bonus;
            return this;
        }

        public Deliverer build() {
            return new Deliverer(id, firstName, lastName, contract, bonus);
        }
    }

    @Override
    public BigDecimal getSalaryZaposlenika() {
        return getContract().getSalary().add(getBonus().bonus());
    }

    /**
     * Metoda za unos podataka o dostavljačima, uključujući ime, prezime, plaću, datum početka i završetka
     * ugovora, vrstu zaposlenja te iznos bonusa. Korisnik unosi podatke putem konzole.
     *
     * @param n  Broj dostavljača koje korisnik želi unijeti.
     * @param sc Objekt klase `Scanner` koji se koristi za unos podataka s konzole.
     * @return Polje objekata tipa `Deliverer` koje sadrži sve unesene dostavljače s njihovim podacima.
     */

    public static List<Deliverer> inputDeliverers(int n, Scanner sc) {
        Main.log.info("Pozvana metoda inputDeliverers.");
        List<Deliverer> deliverers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Main.log.info("Upis " + (i + 1) + ". deliverera: ");
            System.out.println((i + 1) + ". Deliverer: ");
            System.out.println("Upiši ime deliverera: ");
            String name = sc.nextLine();
            System.out.println("Upiši prezime deliverera: ");
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
            deliverers.add(new Deliverer((long) i, name, lastName, new Contract(placa, startDate, endDate, contractType), new Bonus(bonus)));

        }
        return deliverers;
    }

    public static void dostavljacNajviseDostava(Order[] orders, Deliverer[] deliverers) {
        int indexiDostavljaca[] = new int[deliverers.length];
        for (int i = 0; i < orders.length; i++) {
            for (int j = 0; j < deliverers.length; j++) {
                if (orders[i].getDeliverer().equals(deliverers[j])) {
                    indexiDostavljaca[j]++;
                    break;
                }
            }
        }

        // Pronađi maksimalni broj dostava
        int maxDostave = 0;
        for (int i = 0; i < indexiDostavljaca.length; i++) {
            if (indexiDostavljaca[i] > maxDostave) {
                maxDostave = indexiDostavljaca[i];
            }
        }

        // Ispis svih dostavljača s maksimalnim brojem dostava
        System.out.println("Dostavljači s najviše dostava (" + maxDostave + " dostava):");
        for (int i = 0; i < indexiDostavljaca.length; i++) {
            if (indexiDostavljaca[i] == maxDostave) {
                System.out.println("Ime: " + deliverers[i].getFirstName() + " - Prezime: " + deliverers[i].getLastName());
            }
        }
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
        Deliverer deliverer = (Deliverer) o;
        return Objects.equals(contract, deliverer.contract) && Objects.equals(bonus, deliverer.bonus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contract, bonus);
    }
}
