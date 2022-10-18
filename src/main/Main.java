package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 * Inventory management program application that manages lists of parts and products.
 * @author Anna Lyons
 *
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 800, 400 ));
        stage.show();

        Inventory.addPart(new InHouse(
            1, "Brakes", 15.00, 10, 1, 50, 1
        ));

        Inventory.addPart(new Outsourced(
                2, "Rim", 20.00, 15, 1, 50, "Joe's parts"
        ));

        Inventory.addProduct(new Product(
                1000, "Giant Bike", 299.99, 5, 1, 50
        ));

        Inventory.addProduct(new Product(
                1001, "Tricycle", 99.99, 3, 1, 50
        ));
    }

    public static void main(String[] args){
        launch(args);
    }
}
