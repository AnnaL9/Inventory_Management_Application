package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inventory;
import model.InHouse;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;


/**
 * Controller class for the add part screen
 * @author Anna Lyons
 */
public class AddPart implements Initializable{

    public RadioButton outsourcedRadioButton; // Outsourced radio button
    public RadioButton inHouseRadioButton; // In-House radio button
    public Label machineIDCompanyNameLabel; // Machine ID Label
    public ToggleGroup tgPartType;
    public TextField partIdText; // Text field for ID
    public TextField partNameText; // Text field for Name
    public TextField partInvText; // Text field for Inv
    public TextField partPriceText; // Text field for Price
    public TextField partMaxText; // Text field for Max
    public TextField partMachineCompanyText; // Text field for Machine ID/Company Name
    public TextField partMinText; // Text field for Min

    private Stage stage;
    private Scene scene;

    /**
     **Initializes and sets default to in-house radio button
     * @param url URL used to resolve relative paths for the root object, or null if the location is unknown.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseRadioButton.setSelected(true);
    }

    /**
     * Set MachineID/Company Label to Machine ID
     * @param event in-house radio button action
     */
    @FXML
    public void inHouseRadioButtonAction(ActionEvent event) {

        machineIDCompanyNameLabel.setText("Machine ID");
    }

    /**
     * Set MachineID/Company Name Label to Company Name
     * @param event outsourced radio button action
     */
    @FXML
    public void outsourcedRadioButtonAction(ActionEvent event) {

        machineIDCompanyNameLabel.setText("Company Name");
    }

    /**
     * Saves new part
     * @param event save button action
     * @throws IOException
     * an alert will appear if the field is empty/an invalid data type is entered or if invalid min, max, or stock values are entered.
     * Upon successful save, the user will be brought back to the main form.
     */
    @FXML
    public void saveButtonAction(ActionEvent event) throws IOException {
        try {
            int id = 0;
            String name = partNameText.getText();
            Double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partInvText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int machineId = 0;
            String companyName = "";
            boolean saveSuccessful = true;

            //field cannot be empty or invalid error message
            if (name.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Form has a field that is empty or invalid.");
                alert.showAndWait();
                saveSuccessful = false;
            }

            if (outsourcedRadioButton.isSelected()){
                String companyNameText =  partMachineCompanyText.getText();

                //field cannot be empty or invalid error message
                if (companyNameText.isEmpty()){
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
                    //field cannot be empty or invalid error message
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Form has a field that is empty or invalid.");
                    alert.showAndWait();
                    saveSuccessful = false;
                }
            }

            if (min < 0 || min >= max || stock > max || stock < min) {
                //Min should be less than Max; and Inv should be between those two values - Error message
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Invalid min, max, or stock values.");
                alert.showAndWait();
                saveSuccessful = false;
            }

            if (saveSuccessful) {
                if (inHouseRadioButton.isSelected()) {
                    InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.addPart(newInHousePart);
                } else {
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(newOutsourcedPart);
                }
                //Brings us back to main screen after successful save
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e){
             //field cannot be empty or invalid error message
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Form has a field that is empty or invalid.");
            alert.showAndWait();
        }
    }

    /**
     * Cancels adding new part and redirects to main form
     * @param event cancel button action
     * @throws IOException
     */
    @FXML
    public void cancelButtonAction(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }
}
