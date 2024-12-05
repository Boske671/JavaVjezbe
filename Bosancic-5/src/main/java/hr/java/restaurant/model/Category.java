package hr.java.restaurant.model;

import hr.java.production.main.Main;
import hr.java.utils.DataInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Klasa Category koja sadrzi razna svojstva za objekt
 * Sadrži metode inputCategories, ispisCategory
 */
public class Category extends Entity {
    private String name;
    private String description;

    public Category(Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Builder klasa za Category klasu
     */
    public static class Builder {
        private Long id;
        private String name;
        private String description;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Category build() {
            return new Category(id, name, description);
        }
    }

    /**
     * Metoda inputCategories za upis kategorija (upis svojstava)
     * @param n kolicinu kategorija koje zelimo upisati
     * @param sc Objekt klase Scanner za upis
     * @return vraća polje vrste Category[]
     */
    public static List<Category> inputCategories(int n, Scanner sc) {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + ". kategorija: ");
            System.out.println("Upisi ime kategorije: ");
            String name = sc.nextLine();
            System.out.println("Upiši opis: ");
            String description = sc.nextLine();
            categories.add(new Category((long) i, name, description));
        }
        return categories;
    }

    /**
     * Metoda za ispis svojstava objekta klase Category
     */
    public void ispisCategory() {
        System.out.println("Ime kategorije: " + getName() + " - Opis kategorije: " + getDescription());
    }
}
