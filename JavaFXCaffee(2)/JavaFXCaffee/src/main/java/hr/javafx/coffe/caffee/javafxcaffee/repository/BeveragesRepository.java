package hr.javafx.coffe.caffee.javafxcaffee.repository;

import hr.javafx.coffe.caffee.javafxcaffee.model.Beverage;
import hr.javafx.coffe.caffee.javafxcaffee.model.Origin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeveragesRepository<T extends Beverage> extends AbstractRepository<T> {

    private static final String BEVERAGES_FILE_PATH = "dat/beverages.txt";
    private static final Integer NUMBER_OF_ROWS_PER_BEVERAGE = 5;

    @Override
    public T findById(Long id) {
        return findAll().stream().filter(entity -> entity.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<T> findAll() {
        List<T> beverages = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Path.of(BEVERAGES_FILE_PATH))) {
            List<String> fileRows = stream.collect(Collectors.toList());

            for(Integer recordNumber = 0;
                recordNumber < (fileRows.size() / NUMBER_OF_ROWS_PER_BEVERAGE);
                recordNumber++)
            {
                Long id = Long.parseLong(fileRows.get(recordNumber * NUMBER_OF_ROWS_PER_BEVERAGE));
                String name = fileRows.get(recordNumber * NUMBER_OF_ROWS_PER_BEVERAGE + 1);
                BigDecimal price = BigDecimal.valueOf(Float.parseFloat(
                        fileRows.get(recordNumber * NUMBER_OF_ROWS_PER_BEVERAGE + 2)));
                BigDecimal alcoholPercentage = BigDecimal.valueOf(
                        Float.parseFloat(fileRows.get(recordNumber * NUMBER_OF_ROWS_PER_BEVERAGE + 3)));
                Origin origin = Origin.valueOf(fileRows.get(recordNumber * NUMBER_OF_ROWS_PER_BEVERAGE + 4));
                Beverage beverage = new Beverage(id, name, price, alcoholPercentage, origin);
                beverages.add((T) beverage);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return beverages;
    }

    @Override
    public void save(List<T> entities) {
        try(PrintWriter writer = new PrintWriter(BEVERAGES_FILE_PATH)) {
            for (T entity : entities) {
                writer.println(entity.getId());
                writer.println(entity.getName());
                writer.println(entity.getPrice());
                writer.println(entity.calculatePercentage());
                writer.println(entity.getOrigin());
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(T entity) {
        List<T> entities = findAll();
        if(Optional.ofNullable(entity.getId()).isEmpty()) {
            entity.setId(generateNewId());
        }
        entities.add(entity);
        save(entities);
    }

    private Long generateNewId() {
        return findAll().stream().map(b -> b.getId())
                .max((i1, i2) -> i1.compareTo(i2)).orElse(0l) + 1;
    }
}
