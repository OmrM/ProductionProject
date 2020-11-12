import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class Controller {
    @FXML
    private Label lblOutput;
    @FXML
    private TextArea taOutput;

    @FXML
    private TextField name;

    @FXML
    private TextField manufacturer;

//
    @FXML
    private ChoiceBox<String> productTypeBox;
    @FXML
    private TableView<Product> tableViewProducts;

    @FXML
    private TableColumn<?, ?> columnID;

    @FXML
    private TableColumn<?, ?> columnName;

    @FXML
    private TableColumn<?, ?> columnManufacturer;

    @FXML
    private TableColumn<?, ?> columnType;


    @FXML
    private ListView<Product> listViewProds;

    @FXML
    private ComboBox<?> comboBoxQty;

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Production";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;
    static final ObservableList<Product> productLine = FXCollections.observableArrayList();



//productionLog arraylist
    //productionrun arraylist


    public void initialize() throws SQLException {
        //I should probably update the table view inside of this, so it will refresh
        comboBoxQty.setEditable(true);
        comboBoxQty.getSelectionModel().selectFirst(); //this is to select and display the first thing on the list on the combobox. located in the produce tab

        for(ItemType id: ItemType.values()){
            productTypeBox.getItems().add(id.getItemType());
        }
        productTypeBox.getSelectionModel().selectFirst();//this is to select and display the first thing on the list on the choiceBox. in product line tab
        setupProductLineTable();


    }




    public void recordProduction(ActionEvent event) {
        System.out.println("Record Production");
    }

    public void addProduct(ActionEvent event) {
        System.out.println("Add Product");
        updateProductsDB();
    }








/*Gets list of products in database*/
    private void setupProductLineTable() throws SQLException {
        conn = connectToDB();
        stmt = conn.createStatement();

        tableViewProducts.setItems(productLine);
        TableView tableViewProducts = new TableView();
        String sqlOut = "SELECT * FROM PRODUCT";
        //PreparedStatement stmt = conn.prepareStatement(sqlOut);
        System.out.println(sqlOut);
        ResultSet rs = stmt.executeQuery(sqlOut);
        while(rs.next()){
            ItemType tempType = null;
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String typeString  = rs.getString(3);
            String manufacturer = rs.getString(4);


            if (typeString.equals("AU")) {

                tempType = ItemType.AUDIO;

            } else if (typeString.equals("VI")) {

                tempType = ItemType.VISUAL;

            } else if (typeString.equals("AM")) {

                tempType = ItemType.AUDIO_MOBILE;

            } else if (typeString.equals("VM")){

                tempType = ItemType.VISUAL_MOBILE;

            }
                //System.out.println(id+name+ manufacturer+type);
            Product dbProduct = new Widget(id, name, manufacturer, tempType);// create widget object from database
            System.out.println(dbProduct.getId());
            System.out.println(dbProduct.getManufacturer());

            productLine.add(dbProduct);//save widget/product objects to observable list
            // productLine.addAll();
            columnID.setCellValueFactory(new PropertyValueFactory("id"));
            columnName.setCellValueFactory(new PropertyValueFactory("name"));
            columnManufacturer.setCellValueFactory(new PropertyValueFactory("manufacturer"));
            columnType.setCellValueFactory(new PropertyValueFactory("type"));

            //ListView listView = new ListView();
            listViewProds.setItems(productLine);
            //listViewProds.getItems().add(dbProduct);


        }
/*        VBox vbox = new VBox(tableViewProducts);
        Scene scene = new Scene(vbox);*/
    }









        public void updateProductsDB() { //I want this function to be called when you press the add product button.
        //it should take what's in the text fields and put it into the db table
        // it should also call the function to output that info into the tableview

        try {
            // STEP 1: Register JDBC driver
            //STEP 2: Open a connection
            conn = connectToDB();
            stmt = conn.createStatement();
            //STEP 3: Execute a query

            //String varName = fxIdName.getText(); use this syntax to pull info from text fields
            String productName = name.getText();
            String productManufacturer = manufacturer.getText();
            String productType = productTypeBox.getValue();


            // String sqlIn = "INSERT INTO PRODUCT(ID, NAME, MANUFACTURER, TYPE) VALUES((SELECT MAX(ID) FROM PRODUCT) +1 " + ", '" + productName + "', '" + productManufacturer + "', '" + productType + "')";
            final String sqlIn = "INSERT INTO PRODUCT(NAME, MANUFACTURER, TYPE) VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sqlIn); //creating object named ps from class PreparedStatement
            ps.setString(1, productName);
            ps.setString(2, productManufacturer);
            ps.setString(3,productType);
            System.out.println(sqlIn); //just putting this here to monitor the syntax of the sql statement
            ps.executeUpdate();
            // stmt.executeUpdate(sqlIn); //


            //prints out the newest record into the console:
            String sqlOut = "SELECT ID, NAME, MANUFACTURER, TYPE FROM PRODUCT WHERE ID= (SELECT MAX(ID) FROM PRODUCT)";

            ResultSet rs = stmt.executeQuery(sqlOut); //executeQuery grabs info from the db
            rs.next();
            System.out.println(sqlOut);
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getString(3));

           //calls function that updates the table.
            setupProductLineTable();

            // STEP 4: Clean-up environment

            ps.close();
            rs.close();

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            taOutput.appendText(e.toString());
        }
        }





        //opens connection to database
//returns conn
    public Connection connectToDB() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL);


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}










/*

*/
/*        for(int count = 1; count <= 10; count ++){
            quantityBox.getItems().add(String.valueOf(count));

            productTypeBox.getItems().add("Type " + count);

        }*//*

        for(ItemType id: ItemType.values()){
                productTypeBox.getItems().add(id.getItemType());
                }*/
