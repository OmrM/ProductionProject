import java.util.*;
public class ProductionRecord {

    private int productionNumber;
    private int productID;
    private String serialNumber;
    private Date dateProduced;

    /**
     * ProductionRecord class can create a timestamp, serial number
     * allows us to choose the item count
     * @author Omar Muniz
     */


    // another way to get date:
    //Date today = Calendar.getInstance().getTime();

    public void setProductionNum(int productionNumber) {
        this.productionNumber = productionNumber;
    }
    public int getProductionNum(){
        return productionNumber;
    }


    public void setSerialNum(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNum() {
        return serialNumber;
    }



    public void setProdDate(Date dateProduced){
        this.dateProduced  = dateProduced;
    }

    public Date getProdDate() {
        return dateProduced;
    }

    public void setProductID(int productID){
        this.productID = productID;
    }
    public int getProductID() {
        return productID;
    }

    /**
     * Make one constructor that just has a parameter for the productID.
     * This will be the constructor called when the user records production from the user interface.
     * @param productID product id
     */
    public ProductionRecord(int productID){
        productionNumber  = 0;
        serialNumber = "0";
        dateProduced = new Date();
        //return productionNumber;
    }

    /**
     * Create an overloaded constructor to use when creating ProductionRecord objects from the database.
     * This constructor needs parameters for all fields.
     * @param productionNumber production number
     * @param productID  product id
     * @param serialNumber serial num
     * @param dateProduced date
     */
    public ProductionRecord(int productionNumber, int productID, String serialNumber,Date dateProduced) {

        this.productionNumber = productionNumber;
        this.productID = productID;
        this.serialNumber = serialNumber;
        // dateProduced = new Date();
        this.dateProduced = dateProduced;
        //  return String.valueOf(productionNumber);

    }


    /**
     * Overload the ProductionRecord constructor to accept a Product and an int which holds the count
     *  of the number of items of its type that have been created.
     * @param product product object
     * @param itemCount number of items selected
     */
    public ProductionRecord(Product product, int itemCount){
        this.serialNumber = createSerialNumber(product.getManufacturer(),product.getType(), itemCount);
        this.dateProduced = new Date();

    }


    /**
     * generates a serial number
     * @param manufacturer manufacturer
     * @param type type
     * @param itemCount number of items
     * @return serialNumber  string
     */
    public String createSerialNumber(String manufacturer, ItemType type, int itemCount) {
        //String serialNumber = manufacturer.substring(0, 3) + type.getItemType()+ "0000"+ itemCount;
        return manufacturer.substring(0, 3) + type.getItemType()+ String.format("%05d",itemCount);

    }


    /**
     * to string method to display the production number,id, serial number, and date
     */
    public String toString(){
        return "Prod. Num: " + productionNumber + " Product ID: " + productID + " Serial Num: " + serialNumber + " Date: " + dateProduced + "\n";
    }
}

