package hr.java.restaurant.generics;

import hr.java.restaurant.model.Restaurant;

import java.util.List;

public class RestaurantLabourExchangeOffice <T extends Restaurant> {
    private List<T> restaurantsList;

    public RestaurantLabourExchangeOffice(List<T> restaurantsList) {
        this.restaurantsList = restaurantsList;
    }

    public List<T> getRestaurantsList() {
        return restaurantsList;
    }

    public void setRestaurantsList(List<T> restaurantsList) {
        this.restaurantsList = restaurantsList;
    }
}
