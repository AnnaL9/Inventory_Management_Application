package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static java.util.Objects.isNull;

/**
 * model for inventory of parts and products
 * @author Anna Lyons
 */
public class Inventory {
    /**
     * List of all parts in the inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * List of all products in the inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * add a new part to the inventory - autoincrement & assign ID
     * @param newPart the part to add
     */
    public static void addPart (Part newPart){
        newPart.setId(allParts.size()+1);
        allParts.add(newPart);
    }

    /**
     * add a new part to the inventory - autoincrement & assign ID
     * @param newProduct the product to add
     */
    public static void addProduct (Product newProduct){
        newProduct.setId(allProducts.size()+1);
        allProducts.add(newProduct);
    }

    /**
     * searches the all parts list for a part by its ID
     * @param partId the part ID
     * @return the part by the part ID
     */
    public static Part lookupPart (int partId){
        Part partFound = null;
        for (Part part : allParts){
            if (part.getId() == partId){
                partFound = part;
            }
        }
        return partFound;
    }

    /**
     * searches the all products list for a product by its ID
     * @param productId the part ID
     * @return the product by the part ID
     */
    public static Product lookupProduct (int productId){
        Product productFound = null;
        for (Product product : allProducts){
            if (product.getId() == productId){
                productFound = product;
            }
        }
        return productFound;
    }

    /**
     * searches the all parts list for a part by its name
     * @param partName the part name
     * @return list of parts found by name
     */
    public static ObservableList<Part> lookupPart (String partName){
        ObservableList<Part> partFound = FXCollections.observableArrayList();
        for (Part part : allParts){
            if (part.getName().equals(partName)){
                partFound.add(part);
            }
        }
        return partFound;
    }

    /**
     * searches the all products list for a product by its name
     * @param productName the product name
     * @return list of products found by name
     */
    public static ObservableList<Product> lookupProduct (String productName){
        ObservableList<Product> productFound = FXCollections.observableArrayList();
        for (Product product : allProducts){
            if (product.getName().equals(productName)){
                productFound.add(product);
            }
        }
        return productFound;
    }

    /**
     * updates/modifies part in the list
     * @param index Index of part
     * @param selectedPart the selected part to be updated
     */
    public static void updatePart (int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * updates/modifies product in the list
     * @param index Index of product
     * @param selectedProduct the selected product to be updated
     */
    public static void updateProduct (int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    /**
     * deletes selected part
     * @param selectedPart selected part to be deleted
     * @return boolean to confirm if deleted
     */
    public static boolean deletePart (Part selectedPart){
            if (allParts.contains(selectedPart)){
                allParts.remove(selectedPart);
                return true;
            }
            else return false;
    }

    /**
     * deletes selected product
     * @param selectedProduct selected product to be deleted
     * @return boolean to confirm if deleted
     */
    public static boolean deleteProduct (Product selectedProduct){
        if (allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else return false;
    }

    /**
     * gets the list of all parts
     * @return a list of all parts
     */
    public static ObservableList<Part> getAllParts(){
            return allParts;
    }

    /**
     * gets the list of all products
     * @return a list of all products
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}

