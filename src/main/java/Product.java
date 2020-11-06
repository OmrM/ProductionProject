
public abstract class Product implements Item {

    private int id;



    private String name;
    private String manufacturer;
    private ItemType type;


    //constructor for product
    Product(String name, String manufacturer, ItemType type) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.type = type;
    }

    public Product(int id, String name, String manufacturer, ItemType type) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.name = name;
        this.type = type;
    }

    //toString method for product
    public String toString() {
        return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: "
                + type;
    }

    public int getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setType(ItemType type) {
        this.type = type;
    }
    public ItemType getType() {
        return type;
    }


}

