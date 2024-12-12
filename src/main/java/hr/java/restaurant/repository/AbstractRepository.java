package hr.java.restaurant.repository;

import hr.java.restaurant.model.Category;
import hr.java.restaurant.model.Entity;

import java.util.List;

public abstract class AbstractRepository<T extends Entity> {
    public abstract T findById(long id);
    public abstract List<T> findAll();
    public abstract List<T> readFromFile();
    public abstract void save(List<T> entity);
    public abstract void writeListToBinaryFile(List<T> entities);
    public abstract List<T> readListFromBinaryFile();
}
