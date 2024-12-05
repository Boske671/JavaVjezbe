package hr.java.utils;

import hr.java.production.main.Main;
import hr.java.restaurant.exception.AlreadyExistsException;
import hr.java.restaurant.exception.InvalidPriceOrSalaryException;
import hr.java.restaurant.model.Entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Klasa "DataInput" koja služi za unos podataka raznih vrsta
 * Sadrži različite metode za upis različitih vrsti podataka
 */
public class DataInput {

    /**
     *  Metoda nameInput koja služi za upis stringa i koristi InputValidator te na temelju njega baca/ne baca exception
     * @param sc Objekt klase Scanner (služi za upis)
     * @param entities Polje klase "Entity", prosljeđuje se "InputValidator.repetitionOfNamesValidator"-u
     * @param n parametar prosljeđuje se "InputValidator.repetitionOfNamesValidator"-u
     * @param message Console output s obzirom na proslijedeni parametar (string)
     * @return Vraća String kada prođe kroz while petlju kada je sve zadovoljeno
     */
    public static String nameInput(Scanner sc, Entity[] entities, int n, String message ) {
        Main.log.info("Pozvana metoda nameInput.");
        String validString = "";
        Boolean isValid;
        do {
            isValid = true;
            System.out.println(message);
            try {
                validString = sc.nextLine();
                InputValidator.repetitionOfNamesValidator(validString, entities, n);
            } catch (AlreadyExistsException e) {
                System.out.println(e.getMessage());
                Main.log.error(message, e.getMessage());
                isValid = false;
            }
        } while (!isValid);
        return validString;
    }


    /**
     * Metoda placaInput koja služi za upis BigDecimala (plaće) i koristi InputValidator te na temelju njega baca /ne baca exception
     * @param sc Objekt klase Scanner (služi za upis)
     * @return Vraća BigDecimal kada prođe kroz while petlju kada je sve zadovoljeno
     */
    public static BigDecimal placaInput(Scanner sc) {
        Main.log.info("Pozvana metoda placaInput.");
        BigDecimal number = new BigDecimal(0);
        Boolean isValid;
        do {
            isValid = true;
            System.out.println("Upisi placu: ");
            try {
                number = sc.nextBigDecimal();
                InputValidator.placaInputValidator(number);
            }
            catch (InvalidPriceOrSalaryException e) {
                System.out.println(e.getMessage());
                Main.log.error(e.getMessage());
                isValid = false;
            }
        }
        while (!isValid);
        return number;
    }


    /**
     * Metoda cijenaInput koja služi za upis BigDecimala (cijene) i koristi InputValidator te na temelju njega baca / ne baca exception
     * @param sc Objekt klase Scanner (služi za upis)
     * @return Vraća BigDecimal kada prođe kroz while petlju kada je sve zadovoljeno
     */
    public static BigDecimal cijenaInput(Scanner sc) {
        Main.log.info("Pozvana metoda cijenaInput.");
        BigDecimal number = new BigDecimal(0);
        Boolean isValid;
        do {
            isValid = true;
            System.out.println("Upisi cijenu: ");
            try {
                number = sc.nextBigDecimal();
                InputValidator.cijenaInputValidator(number);
            }
            catch (InvalidPriceOrSalaryException e) {
                System.out.println(e.getMessage());
                Main.log.error(e.getMessage());
                isValid = false;
            }
        }
        while (!isValid);
        return number;
    }
}

