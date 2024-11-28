package hr.java.restaurant.model;

import hr.java.restaurant.enumeration.ContractType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Contract {


    private BigDecimal salary;
    private LocalDate startDate;
    private LocalDate endDate;
    private ContractType contractType;


    public Contract(BigDecimal salary, LocalDate startDate, LocalDate endDate, ContractType contractType) {
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

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }
}
