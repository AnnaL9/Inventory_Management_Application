package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.security.cert.PolicyNode;
import java.util.ResourceBundle;

/**
 * Controller class for the main form screen
 * @author Anna Lyons
 */
public class MainForm implements Initializable {

    public AnchorPane appPane; // Anchor pane for application window
    public TextField searchPartText; // Text field for searching parts
    public Button addPartButton; // Add part button
    public Button modifyPartButton; // Modify part button
    public Button deletePartButton; // Delete part button
    public TextField searchProductText; // Text field for searching products
    public Button addProductButton; // Add product button
    public Button modifyProductButton; // Modify product button
    public Button deleteProductButton; // Delete product button
    public Button exitButton; // Exit Main Form screen button
    public Pane partsPane; // Parts pane
    public Pane productsPane; // Products pane
    public TableView partsTable; // Parts tableview
    public TableView productsTable; // Products tableview
    public TableColumn partsPartIdCol; // Parts Table - Part ID Column
    public TableColumn partsPartNameCol; // Parts Table - Part Name Column
    public TableColumn partsInvLevelCol; // Parts Table - Inventory Level Column
    public TableColumn partsPriceCol; // Parts Table - Price/Cost per Unit Column
    public TableColumn productsProductIdCol; // Products Table - Product ID Column
    public TableColumn productsProductNameCol; // Products Table - Product Name Column
    public TableColumn productsInvLevelCol; // Products Table - Inventory Level Column
    public TableColumn productsPriceCol; // Products Table - Price/Cost per Unit Column

    private Stage stage;
    private Scene scene;

    private static int selectedPartId; // Selected part ID
    private static int selectedProductId; //Selected product ID

    private ObservableList<Part> partsList = Inventory.getAllParts();
    private ObservableList<Product> productsList = Inventory.getAllProducts();

    /**
     * Initializes and assigns table views to the lists and attributes to populate data.
     * @param url Location used to resolve relative paths for the root object, or null if the location is unknown.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //assigns the lists to tables
        partsTable.setItems(partsList);
        productsTable.setItems(productsList);

        // assigns the attributes to columns (tied to getters)
        partsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * @return current selected part ID
     */
    public static int getSelectedPartID (){
        return selectedPartId;
    }

    /**
     * @return current selected product ID
     */
    public static int getSelectedProductID(){
        return selectedProductId;
    }

    /**
     * Loads Add Part screen
     * @param event add part action
     * @throws IOException
     */
    @FXML
    private void addPartAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads Modify Part screen
     * @param event modify part action
     * @throws IOException
     * Alert given if no part is selected.
     */
    @FXML
    private void modifyPartAction(ActionEvent event) throws IOException {
        try {
            if (partsTable.getSelectionModel().getSelectedItems().get(0) instanceof InHouse) {
                InHouse part = (InHouse) partsTable.getSelectionModel().getSelectedItems().get(0);
                selectedPartId = part.getId();
            } else {
                Outsourced part = (Outsourced) partsTable.getSelectionModel().getSelectedItems().get(0);
                selectedPartId = part.getId();
            }
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            //Generates error if no part is selected
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please select a part to modify.");
            alert.showAndWait();
        }
    }

    /**
     * Loads Add Product screen
     * @param event add product action
     * @throws IOException
     */
    @FXML
    private void addProductAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads Modify Product screen
     * @param event modify product action
     * @throws IOException
     * Alert given if no product is selected.
     */
    @FXML
    private void modifyProductAction(ActionEvent event) throws IOException {
        try {
            productsTable.getSelectionModel().getSelectedItems().get(0);
            Product product = (Product) productsTable.getSelectionModel().getSelectedItems().get(0);
            selectedProductId = product.getId();
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            //Generates error if no product is selected
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Please select a product to modify.");
            alert.showAndWait();
        }
    }

    /**
     * Deletes the selected part
     * @param event delete part action
     * Alert given to confirm delete. Returns to main screen if canceled.
     * Error given if deletion not successful.
     */
    @FXML
    public void deletePartAction(ActionEvent event) {
        try {
            //Alert to confirm delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this part?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                Inventory.deletePart((Part) partsTable.getSelectionModel().getSelectedItems().get(0)); //deletes selected part
            } else {
                return; //returns to main screen without deleting selected part upon cancel
            }
        } catch (Exception e){
            //Generates error if deletion is not successful
            Alert alert2 = new Alert(Alert.AlertType.ERROR,
                    "Part not deleted.");
            alert2.showAndWait();
        }
    }

    /**
     * Deletes the selected product
     * @param event delete product action
     * Alert given to confirm delete. Returns to main screen if canceled.
     * Checks if product has an associated part and gives error if so.
     * Error given if deletion not successful.
     */
    @FXML
    public void deleteProductAction(ActionEvent event) {
        try {
            Product product = (Product) productsTable.getSelectionModel().getSelectedItem();
            //Alert to confirm delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this part?", ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                if (product.getAllAssociatedParts().isEmpty()) {
                    Inventory.deleteProduct(product); //deletes selected part
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR,
                            "You can't delete a product that has an associated part. Please remove associated part from the product first.");
                    alert2.showAndWait();
                }
            } else {
                return; //returns to main screen without deleting selected part upon cancel
            }
        } catch (Exception e){
            //Generates error if deletion is not successful
            Alert alert2 = new Alert(Alert.AlertType.ERROR,
                    "Product not deleted.");
            alert2.showAndWait();
        }
    }

    /**
     * Exits the application
     * @param event exit button event
     */
    @FXML
    public void exitAppAction(ActionEvent event) {
        stage = (Stage) appPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Searches parts by name/ID.
     * @param event search part action upon hitting enter key.
     * alert given if no results are found.
     */
    @FXML
    public void searchPartAction(ActionEvent event){
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partResults = FXCollections.observableArrayList();
        String searched = searchPartText.getText();

        for (Part part : allParts){
            if (String.valueOf(part.getId()).contains(searched) || part.getName().contains(searched)) {
                partResults.add(part);
            }
        }
        partsTable.setItems(partResults);

        if (partResults.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "No results found. Search results are case-sensitive.");
            alert.showAndWait();
        }

        if (searchPartText.getText().isEmpty()){
            partsTable.setItems((Inventory.getAllParts()));
        }
    }


    /**
     * Searches products by name/ID.
     * @param event search product action upon hitting enter key.
     * alert given if no results are found.
     */
    @FXML
    public void searchProductAction(ActionEvent event) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> productResults = FXCollections.observableArrayList();
        String searched = searchProductText.getText();

        for (Product product : allProducts){
            if (String.valueOf(product.getId()).contains(searched) || product.getName().contains(searched)) {
                productResults.add(product);
            }
        }
        productsTable.setItems(productResults);

        if (productResults.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "No results found. Search results are case-sensitive.");
            alert.showAndWait();
        }

        if (searchProductText.getText().isEmpty()){
            productsTable.setItems((Inventory.getAllProducts()));
        }
    }
}
