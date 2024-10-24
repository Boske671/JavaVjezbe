package hr.java.restaurant.model;

import java.math.BigDecimal;

public class Chef
{

    private String firstName;
    private String lastName;
    private BigDecimal salary;

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public BigDecimal getSalary()
    {
        return salary;
    }

    public void setSalary(BigDecimal salary)
    {
        this.salary = salary;
    }


    public Chef(String firstName, String lastName, BigDecimal salary)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public static void ispisChef(Chef chef)
    {
        System.out.println("Ime: " + chef.getFirstName() + "- Prezime: " + chef.getLastName());
    }


}
