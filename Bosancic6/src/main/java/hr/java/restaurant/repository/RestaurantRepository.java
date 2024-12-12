package hr.java.restaurant.repository;

import hr.java.restaurant.enumeration.ContractType;
import hr.java.restaurant.model.*;
import hr.java.utils.FileNames;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantRepository extends AbstractRepository<Restaurant> {

    @Override
    public Restaurant findById(long id) {
        List<Restaurant> restaurants = new ArrayList<>();
        RestaurantRepository restaurantRepository = new RestaurantRepository();
        restaurants = restaurantRepository.readFromFile();
        Restaurant restaurant;
        restaurant = restaurants.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        return restaurant;

    }

    @Override
    public List<Restaurant> findAll() {
        return List.of();
    }

    @Override
    public List<Restaurant> readFromFile() {
        List<Restaurant> restaurants = new ArrayList<>();
        File file = new File(FileNames.RestaurantsFileName);
        MealsRepository mealsRepository = new MealsRepository();
        ChefsRepository chefsRepository = new ChefsRepository();
        WaitersRepository waitersRepository = new WaitersRepository();
        DeliverersRepository deliverersRepository = new DeliverersRepository();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Long id = Long.parseLong(line);
                String name = br.readLine();
                Long addressId = Long.parseLong(br.readLine());
                String streetName = br.readLine();
                String houseNumber = br.readLine();
                String city = br.readLine();
                String postalCode = br.readLine();

                String mealsIDs = br.readLine();
                HashSet<Meal> meals = Arrays.stream(mealsIDs.split(","))
                        .map(Long::parseLong)
                        .map(mealsRepository::findById)
                        .collect(Collectors.toCollection(HashSet::new));

                String chefsIDs = br.readLine();
                HashSet<Chef> chefs = Arrays.stream(chefsIDs.split(","))
                        .map(Long::parseLong)
                        .map(chefsRepository::findById)
                        .collect(Collectors.toCollection(HashSet::new));

                String waitersIDs = br.readLine();
                HashSet<Waiter> waiters = Arrays.stream(waitersIDs.split(","))
                        .map(Long::parseLong)
                        .map(waitersRepository::findById)
                        .collect(Collectors.toCollection(HashSet::new));

                String deliverersIDs = br.readLine();
                HashSet<Deliverer> deliverers = Arrays.stream(deliverersIDs.split(","))
                        .map(Long::parseLong)
                        .map(deliverersRepository::findById)
                        .collect(Collectors.toCollection(HashSet::new));

                restaurants.add(new Restaurant(id, name, new Address(addressId, streetName, houseNumber, city, postalCode), meals, chefs, waiters, deliverers));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return restaurants;
    }

    @Override
    public void save(List<Restaurant> restaurants) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FileNames.RestaurantsFileName)))) {
            for (Restaurant restaurant : restaurants) {
                bw.write(restaurant.getId() + "\n");
                bw.write(restaurant.getName() + "\n");
                bw.write(restaurant.getAddress().getId() + "\n");
                bw.write(restaurant.getAddress().getId() + "\n");
                bw.write(restaurant.getAddress().getStreet() + "\n");
                bw.write(restaurant.getAddress().getHouseNumber() + "\n");
                bw.write(restaurant.getAddress().getCity() + "\n");
                bw.write(restaurant.getAddress().getPostalCode() + "\n");
                String mealsString = restaurant.getMeals().stream()
                        .map(meal -> String.valueOf(meal.getId())) // Convert ID to String
                        .collect(Collectors.joining(","));
                bw.write(mealsString + "\n");
                String chefsString = restaurant.getChefs().stream()
                        .map(chef -> String.valueOf(chef.getId()))
                        .collect(Collectors.joining(","));
                bw.write(chefsString + "\n");
                String waitersString = restaurant.getWaiters().stream()
                        .map(waiter -> String.valueOf(waiter.getId()))
                        .collect(Collectors.joining(","));
                bw.write(waitersString + "\n");
                String deliverersString = restaurant.getDeliverers().stream()
                        .map(deliverer -> String.valueOf(deliverer.getId()))
                        .collect(Collectors.joining(","));
                bw.write(deliverersString + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeListToBinaryFile(List<Restaurant> restaurants) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileNames.RestaurantsBinaryFileName))) {
            oos.writeObject(restaurants);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Restaurant> readListFromBinaryFile() {
        List<Restaurant> restaurants = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FileNames.RestaurantsFileName))) {
            restaurants = (List<Restaurant>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return restaurants;
    }
}
