package model;

/**
 * model for an in-house part
 * @author Anna Lyons
 */
public class InHouse extends Part{
    /**
     * Machine ID for part
     */
    private int machineId;

    /**
     * Constructor
     * @param id the part id
     * @param name the part name
     * @param price the part price
     * @param stock the part inventory/stock level
     * @param min the minimum level for part
     * @param max the maximum level for part
     * @param machineId the machine ID of the part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Setter
     * @param machineId the machine ID of the part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Getter
     * @return the machine ID of the part
     */
    public int getMachineId() {
        return machineId;
    }
}
