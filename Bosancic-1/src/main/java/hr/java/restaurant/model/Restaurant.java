package hr.java.restaurant.model;

public class Restaurant
{
    private String name;
    private Address address;
    private Meal[] meals;
    private Chef[] chefs;
    private Waiter[] waiters;
    private Deliverer[] deliverers;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public Meal[] getMeals()
    {
        return meals;
    }

    public void setMeals(Meal[] meals)
    {
        this.meals = meals;
    }

    public Chef[] getChefs()
    {
        return chefs;
    }

    public void setChefs(Chef[] chefs)
    {
        this.chefs = chefs;
    }

    public Waiter[] getWaiters()
    {
        return waiters;
    }

    public void setWaiters(Waiter[] waiters)
    {
        this.waiters = waiters;
    }

    public Deliverer[] getDeliverers()
    {
        return deliverers;
    }

    public void setDeliverers(Deliverer[] deliverers)
    {
        this.deliverers = deliverers;
    }



    public Restaurant(String name, Address address, Meal[] meals, Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers)
    {
        this.name = name;
        this.address = address;
        this.meals = meals;
        this.chefs = chefs;
        this.waiters = waiters;
        this.deliverers = deliverers;
    }

    public static void ispisRestoran(Restaurant restaurant)
    {
            System.out.println("Ime: " + restaurant.getName() + "- Adresa: " + restaurant.getAddress().getStreet() + " " + restaurant.getAddress().getHouseNumber() + " " + restaurant.getAddress().getCity()) ;
    }
}
