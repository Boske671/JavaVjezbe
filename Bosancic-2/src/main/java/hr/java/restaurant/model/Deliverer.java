package hr.java.restaurant.model;

import java.math.BigDecimal;

public class Deliverer extends Person
{
    private Contract contract;
    private Bonus bonus;

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

    public void ispisDeliverer() {
        System.out.println("Ime: " + getFirstName() + " - Prezime: " + getLastName() + " - Placa: " + getContract().getSalary() + " - StartDate: " + getContract().getStartDate() + " - EndDate: " + getContract().getEndDate());
    }
}
