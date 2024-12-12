package hr.java.utils;

import hr.java.production.main.Main;
import hr.java.restaurant.exception.AlreadyExistsException;
import hr.java.restaurant.exception.InvalidPriceOrSalaryException;
import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * Klasa "InputValidator" koja služi za validaciju (provjeru) podataka raznih vrsta
 * Sadrži različite metode za provjeru različitih vrsti podataka
 */
public class InputValidator {


    /**
     * Metoda integerValidator koja provjerava da li je upis Negativan / upis nije vrste integer te na temelju toga baca / ne baca exception
     * @param sc Objekt klase Scanner (služi za upis)
     * @return vraća int kada su zadovoljeni svi parametri
     */
    public static int integerValidator(Scanner sc) {
        Main.log.info("Pozvana metoda integerValidator.");
        int number = 0;
        Boolean isValid;
        do {
            isValid = true;
            System.out.println("Upisi broj: ");
            try {
                number = sc.nextInt();
                if (number < 0) {
                    isValid = false;
                    System.out.println(Messages.NEGATIVE_INTEGER_INPUT);
                }

            } catch (InputMismatchException e) {
                System.out.println(Messages.INVALID_INTEGER_INPUT_ERROR);
                isValid = false;
                Main.log.error(e.getMessage());
                sc.nextLine();
            }
        } while (!isValid);
        return number;
    }


    /**
     * Metoda bigDecimalValidator koja provjerava da li je upis Negativan / upis nije vrste BigDecimal te na temelju toga baca / ne baca exception
     * @param sc Objekt klase Scanner (služi za upis)
     * @return  vraća BigDecimal kada su zadovoljeni svi parametri
     */
    public static BigDecimal bigDecimalValidator(Scanner sc) {
        Main.log.info("Pozvana metoda bigDecimalValidator.");
        BigDecimal number = new BigDecimal(0);
        Boolean isValid;
        do {
            isValid = true;
            System.out.println("Upisi broj: ");
            try {
                number = sc.nextBigDecimal();
                if (number.compareTo(BigDecimal.ZERO) < 0) {
                    isValid = false;
                    System.out.println(Messages.NEGATIVE_INTEGER_INPUT);
                    Main.log.error(Messages.NEGATIVE_INTEGER_INPUT);
                }

            } catch (InputMismatchException e) {
                System.out.println(Messages.INVALID_INTEGER_INPUT_ERROR);
                isValid = false;
                Main.log.error(e.getMessage(), Messages.INVALID_INTEGER_INPUT_ERROR);
                sc.nextLine();
            }
        } while (!isValid);
        return number;
    }


    /**
     * Metoda repetitionOfNamesValidator provjerava postoji li već upisano ime iste vrste objekta. Ako postoji throw-a se AlreadyExistsException sa odgovarajućom porukom ovisno o objektu
     * @param name proslijeđeni parametar koji uspoređujemo sa ostalim objektima
     * @param entities proslijeđeno polje objekata potrebno za usporedbu
     * @param n Broj objekata koji su korišteni za usporedbu
     * @throws AlreadyExistsException Exception bačen ako već postoji ime iste vrste objekta
     */
    public static void repetitionOfNamesValidator(String name, Entity[] entities, int n) throws AlreadyExistsException {
        Main.log.info("Pozvana metoda repetitionOfNamesValidator.");
        for (int i = 0; i < n; i++) {
            Entity entity = entities[i];

            if (entity instanceof Chef) {
                Chef chef = (Chef) entity;
                if (chef.getFirstName().equals(name) || chef.getLastName().equals(name)) {
                    throw new AlreadyExistsException(Messages.INVALID_CHEF_NAME_ERROR);
                }
            } else if (entity instanceof Waiter) {
                Waiter waiter = (Waiter) entity;
                if (waiter.getFirstName().equals(name) || waiter.getLastName().equals(name)) {
                    throw new AlreadyExistsException(Messages.INVALID_WAITER_NAME_ERROR);
                }
            } else if (entity instanceof Deliverer) {
                Deliverer deliverer = (Deliverer) entity;
                if (deliverer.getFirstName().equals(name) || deliverer.getLastName().equals(name)) {
                    throw new AlreadyExistsException(Messages.INVALID_DELIVERER_NAME_ERROR);
                }
            } else if (entity instanceof Meal) {
                Meal meal = (Meal) entity;
                if (meal.getName().equals(name)) {
                    throw new AlreadyExistsException(Messages.INVALID_MEAL_NAME_ERROR);
                }
            } else if (entity instanceof Restaurant) {
                Restaurant restaurant = (Restaurant) entity;
                if (restaurant.getName().equals(name)) {
                    throw new AlreadyExistsException(Messages.INVALID_RESTAURANT_NAME_ERROR);
                }
            } else if (entity instanceof Category) {
                Category category = (Category) entity;
                if (category.getName().equals(name)) {
                    throw new AlreadyExistsException(Messages.INVALID_CATEGORY_NAME_ERROR);
                }
            }
        }
    }


    public static void cijenaInputValidator(BigDecimal broj) throws InvalidPriceOrSalaryException {
        Main.log.info("Pozvana metoda cijenaInputValidator.");
        if (broj.compareTo(Main.MINIMALNA_PLACA) < 0) {
            throw new InvalidPriceOrSalaryException(Messages.INVALID_PRICE_ERROR);
        }
    }

    public static void placaInputValidator(BigDecimal broj) throws InvalidPriceOrSalaryException {
        Main.log.info("Pozvana metoda placaInputValidator.");
        if (broj.compareTo(Main.MINIMALNA_PLACA) < 0) {
            throw new InvalidPriceOrSalaryException(Messages.INVALID_SALARY_ERROR);
        }
    }
}
