package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for modify part screen
 * @author Anna Lyons
 */
public class ModifyPart implements Initializable{

    public RadioButton outsourcedRadioButton; // Outsourced radio button
    public RadioButton inHouseRadioButton; // In-House radio button
    public Label machineIDCompanyNameLabel; // Machine ID Label
    public ToggleGroup tgPartType; // Toggle group for part type
    public TextField partIdText; // Text field for ID
    public TextField partNameText; // Text field for Name
    public TextField partInvText; // Text field for Inv
    public TextField partPriceText; // Text field for Price
    public TextField partMaxText; // Text field for Max
    public TextField partMachineCompanyText; // Text field for Machine ID/Company Name
    public TextField partMinText; // Text field for Min

    private Stage stage;
    private Scene scene;
    private Parent root;

    int selectedIndex; //index of selected part
    Part selectedPart; //selected part

    /**
     * Initializes and populates the text fields with the selected part from the main screen.
     * @param url Location used to resolve relative paths for the root object, or null if the location is unknown.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
      */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = Inventory.lookupPart(MainForm.getSelectedPartID());
        selectedIndex = Inventory.getAllParts().indexOf(selectedPart);

        Inventory.lookupPart(MainForm.getSelectedPartID());
        partIdText.setText(String.valueOf(selectedPart.getId())); //populates part ID
        partNameText.setText(String.valueOf(selectedPart.getName())); //populates part name
        partInvText.setText(String.valueOf(selectedPart.getStock())); //populates part inventory
        partPriceText.setText(String.valueOf(selectedPart.getPrice())); //populates part price
        partMaxText.setText(String.valueOf(selectedPart.getMax())); //populates part maximum
        partMinText.setText(String.valueOf(selectedPart.getMin())); //populates part Min

        String machineCompanyText = ""; //variable that will hold value of Machine ID or Company Name depending on part type
        //assign machineCompanyText variable value to machine ID if In House part - else assign company name if outsourced part
        if (Inventory.lookupPart(MainForm.getSelectedPartID()) instanceof InHouse){
            machineCompanyText = String.valueOf(((InHouse) Inventory.lookupPart(MainForm.getSelectedPartID())).getMachineId());
            inHouseRadioButton.setSelected(true);
            inHouseRadioButtonAction(null);
        } else {
            machineCompanyText = String.valueOf(((Outsourced) Inventory.lookupPart(MainForm.getSelectedPartID())).getCompanyName());
            outsourcedRadioButton.setSelected(true);
            outsourcedRadioButtonAction(null);
        }
        partMachineCompanyText.setText(machineCompanyText); //populates Machine ID or Company Name field
    }

    /**
     * Set MachineID/Company Label to Machine ID.
     * @param event in-house radio button action.
     */
    @FXML
    public void inHouseRadioButtonAction(ActionEvent event) {

        machineIDCompanyNameLabel.setText("Machine ID");
    }

    /**
     * Set MachineID/Company Name Label to Company Name.
     * @param event outsourced radio button action.
     */
    @FXML
    public void outsourcedRadioButtonAction(ActionEvent event) {

        machineIDCompanyNameLabel.setText("Company Name");
    }

    /**
     * Saves part/updates part
     * @param event save button action
     * an alert will appear if the field is empty/an invalid data type is entered or if invalid min, max, or stock values are entered.
     * Upon successful save, the user will be brought back to the main form.
      */
    @FXML
    public void saveButtonAction(ActionEvent event) {
        try {
            int id = selectedPart.getId();
            String name = partNameText.getText();
            Double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partInvText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int machineId = 0;
            String companyName = "";
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

            if (outsourcedRadioButton.isSelected()){
                String companyNameText =  partMachineCompanyText.getText();
                if (companyNameText.isEmpty()){
                    /**
                     * field cannot be empty or invalid error message
                     */
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Form has a field that is empty or invalid.");
                    alert.showAndWait();
                    saveSuccessful = false;
                } else {
                    companyName = companyNameText;
                }
            }

            if (inHouseRadioButton.isSelected()) {
                try {
                    machineId = Integer.parseInt(partMachineCompanyText.getText());
                } catch (Exception e) {
                    /**
                     * field cannot be empty or invalid error message
                     */
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Form has a field that is empty or invalid.");
                    alert.showAndWait();
                    saveSuccessful = false;
                }
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
                if (inHouseRadioButton.isSelected()) {
                    InHouse inHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(selectedIndex, inHousePart);
                } else {
                    Outsourced outsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(selectedIndex, outsourcedPart);
                }
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
     * Cancels modifying part and redirects to main form.
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

