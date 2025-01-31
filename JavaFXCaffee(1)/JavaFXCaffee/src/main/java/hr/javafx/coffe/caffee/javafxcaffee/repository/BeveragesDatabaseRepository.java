package hr.javafx.coffe.caffee.javafxcaffee.repository;

import hr.javafx.coffe.caffee.javafxcaffee.exception.EmptyRepositoryResultException;
import hr.javafx.coffe.caffee.javafxcaffee.exception.RepositoryAccessException;
import hr.javafx.coffe.caffee.javafxcaffee.model.Beverage;
import hr.javafx.coffe.caffee.javafxcaffee.model.Origin;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class BeveragesDatabaseRepository<T extends Beverage> extends AbstractRepository<T> {

    private static Boolean DATABASE_ACCESS_IN_PROGRESS = false;

    private Connection connectToDatabase() throws IOException, SQLException {
        Properties props = new Properties();
        props.load(new FileReader("database.properties"));
        return DriverManager.getConnection(
                props.getProperty("databaseUrl"),
                props.getProperty("username"),
                props.getProperty("password"));
    }

    private void disconnectFromDatabase(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public synchronized T findById(Long id) {

        while(DATABASE_ACCESS_IN_PROGRESS) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        DATABASE_ACCESS_IN_PROGRESS = true;

        try (Connection connection = connectToDatabase()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM BEVERAGE WHERE ID = ?");
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return (T) extractBeverageFromResultSet(resultSet);
            } else {
                throw new EmptyRepositoryResultException("Beverage with id " + id + " not found!");
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        } finally {
            DATABASE_ACCESS_IN_PROGRESS = false;
            notifyAll();
        }


    }

    @Override
    public List<T> findAll() {

        List<T> beverages = new ArrayList<>();

        try (Connection connection = connectToDatabase()) {

            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM BEVERAGE");

            while (resultSet.next()) {
                Beverage beverage = extractBeverageFromResultSet(resultSet);
                beverages.add((T) beverage);
            }

            return beverages;
        } catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        }
    }

    private T extractBeverageFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        BigDecimal price = resultSet.getBigDecimal("price");
        BigDecimal alcoholPercentage = resultSet.getBigDecimal("alcohol_percentage");
        String origin = resultSet.getString("origin");

        Beverage beverage = new Beverage(id, name, price, alcoholPercentage, Origin.valueOf(origin));
        return (T) beverage;
    }

    /*
    @Override
    public void save(List<T> entities) {

        try (Connection connection = connectToDatabase()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO BEVERAGE(NAME, PRICE, ALCOHOL_PERCENTAGE, ORIGIN)"
                            + " VALUES(?, ?, ?, ?)");

            for (T entity : entities) {
                stmt.setString(1, entity.getName());
                stmt.setBigDecimal(2, entity.getPrice());
                stmt.setBigDecimal(3, entity.calculatePercentage());
                stmt.setString(4, entity.getOrigin().toString());
                stmt.executeUpdate();
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        }
    }
     */

    @Override
    public synchronized void save(T entity) {

        while(DATABASE_ACCESS_IN_PROGRESS) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        DATABASE_ACCESS_IN_PROGRESS = true;

        try (Connection connection = connectToDatabase()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO BEVERAGE(NAME, PRICE, ALCOHOL_PERCENTAGE, ORIGIN)"
                            + " VALUES(?, ?, ?, ?)");
            stmt.setString(1, entity.getName());
            stmt.setBigDecimal(2, entity.getPrice());
            stmt.setBigDecimal(3, entity.calculatePercentage());
            stmt.setString(4, entity.getOrigin().toString());
            stmt.executeUpdate();
        } catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        } finally {
            DATABASE_ACCESS_IN_PROGRESS = false;
            notifyAll();
        }
    }

    public synchronized Optional<T> findCheapestBeverage() {

        while(DATABASE_ACCESS_IN_PROGRESS) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        DATABASE_ACCESS_IN_PROGRESS = true;

        try (Connection connection = connectToDatabase()) {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(
                    "SELECT * FROM BEVERAGE WHERE PRICE = (SELECT MIN(PRICE) FROM BEVERAGE);");
            if (resultSet.next()) {
                return Optional.of(extractBeverageFromResultSet(resultSet));
            }
            else {
                return Optional.empty();
            }
        }
        catch(SQLException | IOException ex) {
            throw new RepositoryAccessException("An error occured while fetch the cheapest beverage from database!", ex);
        } finally {
            DATABASE_ACCESS_IN_PROGRESS = false;
            notifyAll();
        }
    }
}
