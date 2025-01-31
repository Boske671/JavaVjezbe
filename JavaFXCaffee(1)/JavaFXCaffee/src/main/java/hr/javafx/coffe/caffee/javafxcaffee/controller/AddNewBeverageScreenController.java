package hr.javafx.coffe.caffee.javafxcaffee.controller;

import hr.javafx.coffe.caffee.javafxcaffee.exception.RepositoryAccessException;
import hr.javafx.coffe.caffee.javafxcaffee.model.Beverage;
import hr.javafx.coffe.caffee.javafxcaffee.model.Origin;
import hr.javafx.coffe.caffee.javafxcaffee.repository.BeveragesDatabaseRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

public class AddNewBeverageScreenController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField alcoholPercentageTextField;

    @FXML
    private ComboBox<Origin> originComboBox;

    public void initialize() {
        originComboBox.getItems().addAll(Origin.values());
    }

    public void addNewBeverage() {

        StringBuilder errorMessages = new StringBuilder();

        String name = nameTextField.getText();
        if(name.isEmpty()) {
            errorMessages.append("Unos naziva pića je obvezan!\n");
        }

        BigDecimal price = BigDecimal.ZERO;

        String priceString = priceTextField.getText();
        if(priceString.isEmpty()) {
            errorMessages.append("Unos cijene pića je obvezan!\n");
        }
        else {
            if(!priceString.matches("^[0-9]{1,12}(?:\\.[0-9]{1,4})?$")) {
                errorMessages.append("Unesena cijena pića mora biti pozitivna i u formatu, npr. 10.00!\n");
            }
            else if (Double.parseDouble(priceString) == 0) {
                errorMessages.append("Unesena cijena pića ne smije biti 0.00!\n");
            }
            else {
                price = new BigDecimal(priceString);
            }
        }

        String alcoholPercentageString = alcoholPercentageTextField.getText();

        BigDecimal alcoholPercentage = BigDecimal.ZERO;

        if(alcoholPercentageString.isEmpty()) {
            errorMessages.append("Unos postotka alkohola je obvezan!\n");
        }
        else {
            if(!alcoholPercentageString.matches("^[0-9]{1,12}(?:\\.[0-9]{1,4})?$")) {
                errorMessages.append("Unesen postotak alkohola ne smije biti negativna i mora biti u formatu, npr. 10.00!\n");
            }
            else if (Double.parseDouble(priceString) > 100) {
                errorMessages.append("Unesen postotak alkohola ne smije biti veći od 100.00!\n");
            }
            else {
                alcoholPercentage = new BigDecimal(alcoholPercentageString);
            }
        }

        if(errorMessages.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreške kod unosa novog pića");
            alert.setHeaderText("Piće " + name + " nije spremljeno zbog pogrešaka kod unosa!");
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        }
        else {
            Beverage beverage = new Beverage(name, price, alcoholPercentage, originComboBox.getValue());

            //BeveragesFileRepository<Beverage> repository = new BeveragesFileRepository<>();
            BeveragesDatabaseRepository<Beverage> repository = new BeveragesDatabaseRepository();

            try {
                repository.save(beverage);
            }
            catch (RepositoryAccessException e) {

            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješno spremanje novog pića");
            alert.setHeaderText("Piće " + name + " je uspješno spremljeno!");
            StringBuilder sb = new StringBuilder();
            sb.append("Piće: ")
                    .append("Naziv: ")
                    .append(name)
                    .append("\n")
                    .append("Cijena: ")
                    .append(price)
                    .append("\n")
                    .append("Postotak alkohola:")
                    .append(alcoholPercentage)
                    .append("\n");
            alert.setContentText(sb.toString());
            alert.showAndWait();

            nameTextField.clear();
            priceTextField.clear();
            alcoholPercentageTextField.clear();
        }

    }
}
