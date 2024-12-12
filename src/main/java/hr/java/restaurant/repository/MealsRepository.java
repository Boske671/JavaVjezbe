package hr.java.restaurant.repository;

import hr.java.restaurant.model.Category;
import hr.java.restaurant.model.Deliverer;
import hr.java.restaurant.model.Ingredient;
import hr.java.restaurant.model.Meal;
import hr.java.utils.FileNames;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class MealsRepository extends AbstractRepository<Meal> {

    @Override
    public Meal findById(long id) {
        List<Meal> meals = new ArrayList<>();
        MealsRepository mealsRepository = new MealsRepository();
        meals = mealsRepository.readFromFile();
        Meal meal;
        meal = meals.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        return meal;

    }

    @Override
    public List<Meal> findAll() {
        return List.of();
    }

    @Override
    public List<Meal> readFromFile() {
        List<Meal> meals = new ArrayList<>();
        File file = new File(FileNames.MealsFileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            CategoriesRepository categoriesRepository = new CategoriesRepository();
            IngredientsRepository ingredientsRepository = new IngredientsRepository();
            while ((line = br.readLine()) != null) {
                Long id = Long.parseLong(line);
                String name = br.readLine();
                Long categoryId = Long.parseLong(br.readLine());
                String ingredientsIDsString = br.readLine();
                // Mapiranje ID-jeva sastojaka u HashSet pomoću lambde
                HashSet<Ingredient> ingredients = Arrays.stream(ingredientsIDsString.split(","))
                        .map(Long::parseLong) // Parsiranje ID-jeva
                        .map(ingredientsRepository::findById) // Pronalazak objekta Ingredient po ID-ju
                        .collect(Collectors.toCollection(HashSet::new)); // Kreiranje HashSet-a
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(br.readLine()));
                Category category = categoriesRepository.findById(categoryId);

                // Kreiranje i dodavanje obroka u listu
                meals.add(new Meal(id, name, category, ingredients, price));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return meals;
    }



    @Override
    public void save(List<Meal> meals) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FileNames.MealsFileName)))) {
            for (Meal meal : meals) {
                bw.write(meal.getId() + "\n");
                bw.write(meal.getName() + "\n");
                bw.write(meal.getCategory().getId() + "\n");
                String ingredientsString = meal.getIngredients().stream()
                        .map(ingredient -> String.valueOf(ingredient.getId())) // Convert ID to String
                        .collect(Collectors.joining(",")); // Join IDs with commas
                bw.write(ingredientsString + "\n");
                bw.write(ingredientsString + "\n");
                bw.write(meal.getPrice() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeListToBinaryFile(List<Meal> meals) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileNames.MealsBinaryFileName))){
            oos.writeObject(meals);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Meal> readListFromBinaryFile() {
        List<Meal> meals = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FileNames.MealsBinaryFileName))) {
            meals = (List<Meal>) ois.readObject();
            System.out.println("RADI");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return meals;
    }
}
