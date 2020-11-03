
public abstract class Product implements Item {

    private int Id;



    private ItemType Type;
    private String Manufacturer;
    private String Name;


    //constructor for product
    Product(String name, String manufacturer, ItemType type) {
        this.Name = name;
        this.Manufacturer = manufacturer;
        this.Type = type;
    }

    public Product(int id, String name, String manufacturer, ItemType type) {
        this.Id = id;
        this.Manufacturer = manufacturer;
        this.Name = name;
        this.Type = type;
    }

    //toString method for product
    public String toString() {
        return "Name: " + Name + "\n" + "Manufacturer: " + Manufacturer + "\n" + "Type: "
                + Type;
    }

    public int getId() {
        return Id;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ItemType getType() {
        return Type;
    }

    public void setType(ItemType type) {
        Type = type;
    }


}

