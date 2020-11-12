import java.util.*;
public class ProductionRecord {
    private int productionNumber;
    private int productID;
    private String serialNumber;
    private Date dateProduced = new Date();
    private Product product;
    int itemCount;


    // the correct way to get today's date
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

    //Make one constructor that just has a parameter for the productID. This will be the constructor called when the user records production from the user interface.
    public ProductionRecord(int productID){
        productionNumber  = 0;
        serialNumber = "0";
        dateProduced = new Date();
        //return productionNumber;
    }

    //Create an overloaded constructor to use when creating ProductionRecord objects from the database.
    // //This constructor needs parameters for all fields.
    public ProductionRecord(int productionNumber, int productID, String serialNumber,Date dateProduced) {

        this.productionNumber = productionNumber;
        this.productID = productID;
        this.serialNumber = serialNumber;
        // dateProduced = new Date();
        this.dateProduced = dateProduced;
        //  return String.valueOf(productionNumber);

    }
    //Overload the ProductionRecord constructor to accept a Product and an int which holds the count of the number of items of its type that have been created. (You can write the code to generate the count later.)
    public ProductionRecord(Product product, int itemCount){
        this.itemCount = itemCount; //need to write code to get number of items of its type****************************
        this.serialNumber = Widget.createSerialNumber(product.getManufacturer(),product.getType(), itemCount);
        this.product = product;
    }



    public String toString(){
        return "Prod. Num: " + productionNumber + " Product ID: " + productID + " Serial Num: " + serialNumber + " Date: " + dateProduced + "\n";
    }
}

