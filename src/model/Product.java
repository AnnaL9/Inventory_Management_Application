package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * model for a product - may contain associated parts
 * @author Anna Lyons
 */
public class Product {
    /**
     * product id
     */
    private int id;

    /**
     * product name
     */
    private String name;

    /**
     * product price
     */
    private double price;

    /**
     * product stock/inventory level
     */
        private int stock;

    /**
     * minimum stock of product
     */
    private int min;

    /**
     * maximum stock of product
     */
    private int max;

    /**
     * list of associated parts for product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Constructor
     * @param id product id
     * @param name product name
     * @param price product price
     * @param stock product stock
     * @param min product minimum stock
     * @param max product maximum stock
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * adds part to associated parts list
     * @param part the part to be added
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * deletes part from associated parts list
     * @param selectedAssociatedPart the part to delete
     * @return boolean to confirm if deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /**
     * @return list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
