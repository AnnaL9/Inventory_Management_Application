package model;

/**
 * model for an outsourced part
 * @author Anna Lyons
 */
public class Outsourced extends Part {
    /**
     * The company name of where the part is outsourced from
     */
    private String companyName;

    /**
     * Constructor
     * @param id the part id
     * @param name the part name
     * @param price the part price
     * @param stock the part inventory/stock level
     * @param min the minimum level for part
     * @param max the maximum level for part
     * @param companyName - the company name of where the part is outsourced from
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Setter
     * @param companyName - the company name of where the part is outsourced from
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * Getter
     * @return companyName - the company name of where the part is outsourced from
     */
    public String getCompanyName(){
        return companyName;
    }
}
