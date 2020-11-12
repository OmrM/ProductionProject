public class Widget extends Product {


/*
    public Widget(String id, String name, String manufacturer, ItemType type) {
        super(name, manufacturer, type);
    }
*/

    public Widget(int id, String name, String manufacturer, ItemType type){
        super(id, name, manufacturer,type);
    }

    public static String createSerialNumber(String manufacturer, ItemType type, int itemCount) {

        // return new StringBuilder().append(product.getManufacturer().substring(0, 3)).append(product.getType()).append("0000").append(itemCount).toString();

        String serialNumber = manufacturer.substring(0, 3) + type.getItemType()+ "0000"+ itemCount;
        return serialNumber;

    }
}

