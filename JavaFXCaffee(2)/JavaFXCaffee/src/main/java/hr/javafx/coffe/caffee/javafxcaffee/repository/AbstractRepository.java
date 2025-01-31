package hr.javafx.coffe.caffee.javafxcaffee.repository;

import hr.javafx.coffe.caffee.javafxcaffee.model.Entity;

import java.util.List;

public abstract class AbstractRepository<T extends Entity> {
    public abstract T findById(Long id);
    public abstract List<T> findAll();
    public abstract void save(List<T> entities);
    public abstract void save(T entity);
}
