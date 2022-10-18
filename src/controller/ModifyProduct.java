package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for modify product screen
 * @author Anna Lyons
 */
public class ModifyProduct implements Initializable {

    public Pane addProductPane;
    public TableView associatedPartsTableView;
    public TableColumn associatedPartsIdCol;
    public TableColumn associatedPartsNameCol;
    public TableColumn associatedPartsInventoryCol;
    public TableColumn associatedPartsPriceCol;
    public TableView partsTableView;
    public TableColumn partsIdCol;
    public TableColumn partsNameCol;
    public TableColumn partsInventoryCol;
    public TableColumn partsPriceCol;
    public TextField searchParts;
    public TextField productIdText;
    public TextField productNameText;
    public TextField productInvText;
    public TextField productPriceText;
    public TextField productMaxText;
    public TextField productMinText;

    private Stage stage;
    private Scene scene;
    private Parent root;

    int selectedIndex; //index of selected product
    Product selectedProduct; //selected product

    private ObservableList<Part> partsList = Inventory.getAllParts();
    private ObservableList<Product> productsList = Inventory.getAllProducts();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Initializes and populates the text fields with the selected product from the main screen and populates the tables appropriately.
     * @param url Location used to resolve relative paths for the root object, or null if the location is unknown.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = Inventory.lookupProduct(MainForm.getSelectedProductID());
        selectedIndex = Inventory.getAllProducts().indexOf(selectedProduct);

        Inventory.lookupProduct(MainForm.getSelectedProductID());
        productIdText.setText(String.valueOf(selectedProduct.getId())); //populates product ID
        productNameText.setText(String.valueOf(selectedProduct.getName())); //populates product name
        productInvText.setText(String.valueOf(selectedProduct.getStock())); //populates product inventory
        productPriceText.setText(String.valueOf(selectedProduct.getPrice())); //populates product price
        productMaxText.setText(String.valueOf(selectedProduct.getMax())); //populates product maximum
        productMinText.setText(String.valueOf(selectedProduct.getMin())); //populates product Min

        //assigns the parts list to the parts table
        partsTableView.setItems(partsList);

        associatedParts = selectedProduct.getAllAssociatedParts();
        //assigns the associated parts to the associated parts table
        associatedPartsTableView.setItems(associatedParts);

        // assigns the attributes to columns
        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Searches parts... If found, shows a single part or filters multiple parts. If not found, displays an error message in the UI or in a dialog box.
     * @param event search parts action event upon pressing enter key
     * alert given if no results are found.
      */
    @FXML
    public void searchPartsAction(ActionEvent event) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partResults = FXCollections.observableArrayList();
        String searched = searchParts.getText();

        for (Part part : allParts){
            if (String.valueOf(part.getId()).contains(searched) || part.getName().contains(searched)) {
                partResults.add(part);
            }
        }
        partsTableView.setItems(partResults);

        if (partResults.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "No results found. Search results are case-sensitive.");
            alert.showAndWait();
        }

        if (searchParts.getText().isEmpty()){
            partsTableView.setItems((Inventory.getAllParts()));
        }
    }

    /**
     * The part is copied to the bottom table
     * @param event add button action
     * alert given if no part is selected.
     */
    @FXML
    public void addButtonAction(ActionEvent event) {
        Part selectedPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
        //error message if no part is selected when trying to add
        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "No part selected. Please select a part.");
            alert.showAndWait();
        } else {
            associatedParts.add(selectedPart);
        }

    }

    /**
     * Removes a selected part from the bottom table
     * @param event remove button action
     * confirmation alert given to confirm removing the part or cancel action.
     */
    @FXML
    public void removeButtonAction(ActionEvent event) {
        //Alert to confirm delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to remove this associated part?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES){
            Part selectedPart = (Part) associatedPartsTableView.getSelectionModel().getSelectedItem();
            associatedParts.remove(selectedPart); //deletes selected part
        }
        else {
            return; //returns to main screen without deleting selected part upon cancel
        }
    }

    /**
     * Saves product and redirects to main form
     * @param event save button action
     * an alert will appear if the field is empty/an invalid data type is entered or if invalid min, max, or stock values are entered.
     * Upon successful save, the user will be brought back to the main form.
      */
    @FXML
    public void saveButtonAction(ActionEvent event) {
        try {
            int id = selectedProduct.getId();
            String name = productNameText.getText();
            Double price = Double.parseDouble(productPriceText.getText());
            int stock = Integer.parseInt(productInvText.getText());
            int min = Integer.parseInt(productMinText.getText());
            int max = Integer.parseInt(productMaxText.getText());
            boolean saveSuccessful = true;

            if (name.isEmpty()){
                /**
                 * field cannot be empty or invalid error message
                 */
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Form has a field that is empty or invalid.");
                alert.showAndWait();
                saveSuccessful = false;
            }

            if (min < 0 || min >= max || stock > max || stock < min) {
                /**
                 * Min should be less than Max; and Inv should be between those two values - Error message
                 */
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Invalid min, max, or stock values.");
                alert.showAndWait();
                saveSuccessful = false;
            }

            if (saveSuccessful) {
                Product modifiedProduct = new Product(id, name, price, stock, min, max);
                for (Part part : associatedParts){
                    modifiedProduct.addAssociatedPart(part);
                }
                Inventory.updateProduct(selectedIndex, modifiedProduct);
                /**
                 * Brings us back to main screen after successful save
                 */
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e){
            /**
             * field cannot be empty or invalid error message
             */
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Form has a field that is empty or invalid.");
            alert.showAndWait();
        }
    }

    /**
     * Cancels modifying product and redirects to main form.
     * @param event cancel button action
     * @throws IOException
     */
    @FXML
    public void cancelButtonAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

