package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Contract {

    public static final String FULL_TIME = "FULL_TIME";
    public static final String PART_TIME = "PART_TIME";

    private BigDecimal salary;
    private LocalDate startDate;
    private LocalDate endDate;
    private String contractType;



    public Contract(BigDecimal salary, LocalDate startDate, LocalDate endDate, String contractType) {
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractType = contractType;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        if (contractType.equals(FULL_TIME) || contractType.equals(PART_TIME)) {
            this.contractType = contractType;
        } else {
            System.out.println("Krivi upis! Upis mora biti 'FULL_TIME' ili 'PART_TIME'");
        }
    }
}
