package hr.java.production.main;

import hr.java.restaurant.model.*;
import hr.java.restaurant.repository.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileInputOutput {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        List<Category> categories = new ArrayList<Category>();
        CategoriesRepository categoriesRepository = new CategoriesRepository();
        categories = categoriesRepository.readFromFile();
        for (Category category : categories) {
            category.ispisCategory();
        }

        IngredientsRepository ingredientsRepository = new IngredientsRepository();
        List<Ingredient> ingredients = ingredientsRepository.readFromFile();
        for (Ingredient ingredient : ingredients) {
            ingredient.ispisIngredient();
        }

        MealsRepository mealsRepository = new MealsRepository();
        List<Meal> meals = mealsRepository.readFromFile();
        for (Meal meal : meals) {
            meal.ispisMeal();
        }

        ChefsRepository chefsRepository = new ChefsRepository();
        List<Chef> chefs = chefsRepository.readFromFile();
        for (Chef chef : chefs) {
            chef.ispisZaposlenik();
        }

        DeliverersRepository deliverersRepository = new DeliverersRepository();
        List<Deliverer> deliverers = deliverersRepository.readFromFile();
        for (Deliverer deliverer : deliverers) {
            deliverer.ispisZaposlenik();
        }

        WaitersRepository waitersRepository = new WaitersRepository();
        List<Waiter> waiters = waitersRepository.readFromFile();
        for (Waiter waiter : waiters) {
            waiter.ispisZaposlenik();
        }

        RestaurantRepository restaurantRepository = new RestaurantRepository();
        List<Restaurant> restaurants = restaurantRepository.readFromFile();
        for (Restaurant restaurant : restaurants) {
            restaurant.ispisRestoran();
        }

        OrdersRepository ordersRepository = new OrdersRepository();
        List<Order> orders = ordersRepository.readFromFile();
        for (Order order : orders) {
            order.ispisOrder();
        }


        System.out.println("-------------------------------------\n BINARY \n-------------------------------------\n");
        categoriesRepository.writeListToBinaryFile(categories);
        List<Category> categories2 = categoriesRepository.readFromFile();
        for (Category category : categories2) {
            category.ispisCategory();
        }
        ingredientsRepository.writeListToBinaryFile(ingredients);
        List<Ingredient> ingredients2 = ingredientsRepository.readFromFile();
        for (Ingredient ingredient : ingredients) {
            ingredient.ispisIngredient();
        }
        mealsRepository.writeListToBinaryFile(meals);
        List<Meal> meals2 = mealsRepository.readFromFile();
        for (Meal meal : meals) {
            meal.ispisMeal();
        }
        chefsRepository.writeListToBinaryFile(chefs);
        List<Chef> chefs2 = chefsRepository.readFromFile();
        for (Chef chef : chefs) {
            chef.ispisZaposlenik();
        }

        deliverersRepository.writeListToBinaryFile(deliverers);
        List<Deliverer> deliverers2 = deliverersRepository.readFromFile();
        for (Deliverer deliverer : deliverers) {
            deliverer.ispisZaposlenik();
        }

        waitersRepository.writeListToBinaryFile(waiters);
        List<Waiter> waiters2 = waitersRepository.readFromFile();
        for (Waiter waiter : waiters) {
            waiter.ispisZaposlenik();
        }

        restaurantRepository.writeListToBinaryFile(restaurants);
        List<Restaurant> restaurants2 = restaurantRepository.readFromFile();
        for (Restaurant restaurant : restaurants) {
            restaurant.ispisRestoran();
        }
        ordersRepository.writeListToBinaryFile(orders);
        List<Order> orders2 = ordersRepository.readFromFile();
        for (Order order : orders) {
            order.ispisOrder();
        }



    }
}
