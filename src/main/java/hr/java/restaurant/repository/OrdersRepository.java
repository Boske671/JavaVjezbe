package hr.java.restaurant.repository;

import hr.java.restaurant.model.*;
import hr.java.utils.FileNames;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersRepository extends AbstractRepository<Order> {

    @Override
    public Order findById(long id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }

    public List<Order> readFromFile() {
        List<Order> orders = new ArrayList<>();
        File file = new File(FileNames.OrdersFileName);
        RestaurantRepository restaurantRepository = new RestaurantRepository();
        MealsRepository mealsRepository = new MealsRepository();
        DeliverersRepository deliverersRepository = new DeliverersRepository();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Read the ID and Restaurant ID
                Long id = Long.parseLong(line);
                Long restaurantId = Long.parseLong(br.readLine());

                // Read and parse the meals IDs
                String mealsIDsString = br.readLine();
                List<Meal> meals = Arrays.stream(mealsIDsString.split(","))
                        .map(Long::parseLong) // Parse IDs as Long
                        .map(mealsRepository::findById) // Find Meal by ID
                        .toList();

                // Read the deliverer ID
                Long delivererID = Long.parseLong(br.readLine());

                // Read and parse the date
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
                LocalDateTime date = LocalDateTime.parse(br.readLine(), dateFormat);

                // Create and add the Order object
                orders.add(new Order(
                        id,
                        restaurantRepository.findById(restaurantId),
                        meals,
                        deliverersRepository.findById(delivererID),
                        date
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public void save(List<Order> orders) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FileNames.OrdersFileName)))) {
            for (Order order : orders) {
                bw.write(order.getId() + "\n");
                bw.write(order.getRestaurant().getId() + "\n");
                String mealsString = order.getMeals().stream()
                        .map(meal -> String.valueOf(meal.getId())) // Convert ID to String
                        .collect(Collectors.joining(","));
                bw.write(mealsString + "\n");
                bw.write(order.getDeliverer().getId() + "\n");
                bw.write(order.getDeliveryDateAndTime() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeListToBinaryFile(List<Order> orders) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileNames.OrdersBinaryFileName))){
            oos.writeObject(orders);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> readListFromBinaryFile() {
        List<Order> orders = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FileNames.OrdersBinaryFileName))) {
            orders = (List<Order>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
}
