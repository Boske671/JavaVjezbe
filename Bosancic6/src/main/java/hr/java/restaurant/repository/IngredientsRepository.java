package hr.java.restaurant.repository;

import hr.java.restaurant.model.Category;
import hr.java.restaurant.model.Deliverer;
import hr.java.restaurant.model.Entity;
import hr.java.restaurant.model.Ingredient;
import hr.java.utils.FileNames;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IngredientsRepository extends AbstractRepository<Ingredient> {
    @Override
    public Ingredient findById(long id) {
        List<Ingredient> ingredients = new ArrayList<>();
        IngredientsRepository ingredientsRepository = new IngredientsRepository();
        ingredients = ingredientsRepository.readFromFile();
        Ingredient ingredient;
        ingredient = ingredients.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        return ingredient;
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @Override
    public List<Ingredient> readFromFile() {
        List<Ingredient> ingredients = new ArrayList<>();
        File file = new File(FileNames.IngredientsFileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            CategoriesRepository categoriesRepository = new CategoriesRepository();
            while ((line = br.readLine()) != null) {
                Long id = Long.parseLong(line);
                String name = br.readLine();
                Long categoryId = Long.parseLong(br.readLine());
                BigDecimal kcal = BigDecimal.valueOf(Double.parseDouble(br.readLine()));
                String prepMethod = br.readLine();
                Category category = categoriesRepository.findById(categoryId);
                ingredients.add(new Ingredient(id, name, category, kcal, prepMethod));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    @Override
    public void save(List<Ingredient> ingredients) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FileNames.IngredientsFileName)))) {
            for (Ingredient ingredient : ingredients) {
                bw.write(ingredient.getId() + "\n");
                bw.write(ingredient.getName() + "\n");
                bw.write(ingredient.getCategory().getId() + "\n");
                bw.write(ingredient.getKcal() + "\n");
                bw.write(ingredient.getPreparationMethod() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeListToBinaryFile(List<Ingredient> ingredients) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileNames.IngredientsBinaryFileName))){
            oos.writeObject(ingredients);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ingredient> readListFromBinaryFile() {
        List<Ingredient> ingredients = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FileNames.IngredientsBinaryFileName))) {
            ingredients = (List<Ingredient>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }
}
