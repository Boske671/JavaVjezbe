package hr.java.restaurant.repository;

import hr.java.restaurant.model.Category;
import hr.java.restaurant.model.Entity;
import hr.java.utils.FileNames;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriesRepository extends AbstractRepository<Category> {
    @Override
    public Category findById(long id) {
        List<Category> categories = new ArrayList<>();
        CategoriesRepository categoriesRepository = new CategoriesRepository();
        categories = categoriesRepository.readFromFile();
        Category category;
        category = categories.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        return category;
    }

    @Override
    public List<Category> findAll() {
        return List.of();
    }

    @Override
    public List<Category> readFromFile() {
        List<Category> categories = new ArrayList<>();
        File file = new File(FileNames.CategoriesFileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Long id = Long.parseLong(line);
                String name = br.readLine();
                String description = br.readLine();
                categories.add(new Category(id, name, description));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public void save(List<Category> categories) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FileNames.CategoriesFileName)))){
            for (Category category : categories) {
                bw.write(category.getId() + "\n");
                bw.write(category.getName() + "\n");
                bw.write(category.getDescription() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeListToBinaryFile(List<Category> categories) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileNames.CategoriesBinaryFileName))){
            oos.writeObject(categories);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Category> readListFromBinaryFile() {
        List<Category> categories = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FileNames.CategoriesBinaryFileName))) {
            categories = (List<Category>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
}
