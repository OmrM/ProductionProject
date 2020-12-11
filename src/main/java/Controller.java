import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**contains an initialize method and event handlers for the action events
 * @author Omar Muniz
 */
public class Controller {
    @FXML
    private TextArea taOutput;
    @FXML
    private TextField name;
    @FXML
    private TextField manufacturer;
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
    private ComboBox<String> comboBoxQty;
    @FXML
    private TextArea productionRecordLog;

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/Production";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;
    //ObservableList and Arraylist for Product and ProductionRecord objects
    static final ObservableList<Product> productLine = FXCollections.observableArrayList();
    ArrayList<ProductionRecord> productionRun = new ArrayList<>();

    /**
     * Initialize method
     */
    public void initialize() throws SQLException {

        comboBoxQty.setEditable(true);
        for (int count = 1; count <= 10; count++) {                  //displays numbers 1 through ten in combo box
            comboBoxQty.getItems().add(String.valueOf(count));
        }
        comboBoxQty.getSelectionModel().selectFirst();              //select and display the first thing on the list on the combobox. located in the produce tab
        for (ItemType id : ItemType.values()) {
            productTypeBox.getItems().add(id.getItemType());
        }
        productTypeBox.getSelectionModel().selectFirst();           //select and display the first thing on the list on the choiceBox. in product line tab
        setupProductLineTable();                                    //displays current Product database info into tableview
        loadProductionLog();                                        //displays current ProductionRecord database info into text area
    }

    /**
     * Add Product Button
     * adds a product to the database
     */
    public void addProduct(ActionEvent event) {
        System.out.println("Add Product");
        tableViewProducts.getItems().clear(); //clears the tableview. otherwise, it would just append a copy of what's in the db
        updateProductsDB();
    }


    /**
     * Record Production Button
     * records to the production log and outputs it to the text area
     * <p>
     * Get the selected product from the Product Line ListView and the quantity from the
     * comboBox.
     * Create an ArrayList of ProductionRecord objects named productionRun. *****************
     * Send the productionRun arraylist to an addToProductionDB method.
     */
    public void recordProduction(ActionEvent event) throws SQLException {

        try {
            productionRecordLog.clear();                                                    //need to clear text area before outputting more text onto it
            try{
                Product selectedProduct = listViewProds.getSelectionModel().getSelectedItem();  //getting selected product from the listview

                int numProduced = Integer.parseInt(comboBoxQty.getValue());                     //getting quantity of product from comboBox

                System.out.println("number of items produced: " + numProduced);
                ProductionRecord productionRecordObj = new ProductionRecord(selectedProduct, numProduced); //using the appropriate constructor, which takes a product and quantity

                for (int numCount = 0; numCount < numProduced; numCount++) {
                    productionRun.add(productionRecordObj);                                  //adding productionRecord objects to productionRun arraylist
                    addToProductionDB(productionRecordObj);                                 //Send the productionRun arraylist to an addToProductionDB method.
                }
                loadProductionLog();                                                        //call loadProductionLog

            }catch (NullPointerException NPE){
                System.out.println("You Must First Select a Product, Then a Quantity From The Dropdown Box");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }


    /**
     * Loop through the productionRun, inserting productionRecord object information into the ProductionRecord database table
     *
     * @param productionRecordObj productionRecord object passed from recordProduction()
     * @throws SQLException exception
     */
    public void addToProductionDB(ProductionRecord productionRecordObj) throws SQLException {
        conn = connectToDB();
        stmt = conn.createStatement();

        String sqlIn = "INSERT INTO PRODUCTIONRECORD(PRODUCT_ID,SERIAL_NUM,DATE_PRODUCED) VALUES(" + productionRecordObj.getProductID() + ",'" + productionRecordObj.getSerialNum() + "'," + "CURRENT_TIMESTAMP()" + ")";
        System.out.println("logging production record to database");
        System.out.println(sqlIn);
        stmt.executeUpdate(sqlIn);

        productionRun.add(productionRecordObj);

        stmt.close();
        conn.close();
    }

    /**
     * Create ProductionRecord objects from the records in the ProductionRecord database table.
     * gets the info from the PRODUCTION RECORD database. displays it onto the log text area
     * I combined the loadProductionLog and showProduction  methods into one
     */
    public void loadProductionLog() throws SQLException {
        conn = connectToDB();
        stmt = conn.createStatement();

        String sqlOut = "SELECT * FROM PRODUCTIONRECORD";
        //PreparedStatement stmt = conn.prepareStatement(sqlOut);
        System.out.println(sqlOut);
        ResultSet rs = stmt.executeQuery(sqlOut);

        while (rs.next()) {
            int id = rs.getInt(1);
            int productionNum = rs.getRow();
            String serialNum = rs.getString(3);
            Date date = rs.getDate(4);
            ProductionRecord pr = new ProductionRecord(id, productionNum, serialNum, date);
            productionRun.add(pr);                              //adds production record objects to the arraylist named productionRun
            productionRecordLog.appendText(pr.toString());      //displays the production log onto the text area in the gui
        }
        listViewProds.setItems(productLine);
        rs.close();

        stmt.close();
        conn.close();

    }

    /**
     * Gets list of products in database. puts them into the tableview
     */
    private void setupProductLineTable() throws SQLException {
        conn = connectToDB();
        stmt = conn.createStatement();

        tableViewProducts.setItems(productLine);
        TableView tableViewProducts = new TableView();
        String sqlOut = "SELECT * FROM PRODUCT";
        //PreparedStatement stmt = conn.prepareStatement(sqlOut);
        System.out.println(sqlOut);
        ResultSet rs = stmt.executeQuery(sqlOut);

        while (rs.next()) {
            ItemType tempType = null;
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String typeString = rs.getString(3);
            String manufacturer = rs.getString(4);
            if (typeString.equals("AU")) {

                tempType = ItemType.AUDIO;

            } else if (typeString.equals("VI")) {

                tempType = ItemType.VISUAL;

            } else if (typeString.equals("AM")) {

                tempType = ItemType.AUDIO_MOBILE;

            } else if (typeString.equals("VM")) {

                tempType = ItemType.VISUAL_MOBILE;

            }

            Product dbProduct = new Widget(id, name, manufacturer, tempType);// create widget object from database
            productLine.add(dbProduct);                                     //save widget/product objects to observable list
            productLine.addAll();
            columnID.setCellValueFactory(new PropertyValueFactory("id"));
            columnName.setCellValueFactory(new PropertyValueFactory("name"));
            columnManufacturer.setCellValueFactory(new PropertyValueFactory("manufacturer"));
            columnType.setCellValueFactory(new PropertyValueFactory("type"));
        }
        listViewProds.setItems(productLine);

        rs.close();
        stmt.close();
        conn.close();
    }

    /**
     * This method will be called when you press the add product button.
     * it should take what's in the text fields and put it into the db table
     * it should also call the function to output that info into the tableview
     */
    public void updateProductsDB() {

        try {
            // STEP 1: Register JDBC driver
            //STEP 2: Open a connection
            conn = connectToDB();
            stmt = conn.createStatement();
            //STEP 3: Execute a query
            //use this syntax to pull info from text fields: String varName = fxIdName.getText();
            String productName = name.getText();
            String productManufacturer = manufacturer.getText();
            String productType = productTypeBox.getValue();

            //Other way to set up sql statement:
            // String sqlIn = "INSERT INTO PRODUCT(ID, NAME, MANUFACTURER, TYPE) VALUES((SELECT MAX(ID) FROM PRODUCT) +1 " + ", '" + productName + "', '" + productManufacturer + "', '" + productType + "')";
            final String sqlIn = "INSERT INTO PRODUCT(NAME, MANUFACTURER, TYPE) VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sqlIn); //creating object named ps from class PreparedStatement
            ps.setString(1, productName);
            ps.setString(2, productManufacturer);
            ps.setString(3, productType);
            System.out.println(sqlIn);                          //console output to monitor the syntax of the sql statement
            ps.executeUpdate();


            //prints out the newest record into the console:
            String sqlOut = "SELECT ID, NAME, MANUFACTURER, TYPE FROM PRODUCT WHERE ID= (SELECT MAX(ID) FROM PRODUCT)";

            ResultSet rs = stmt.executeQuery(sqlOut);           //executeQuery grabs info from the db
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

    /**
     * creates a connection to the database
     *
     * @return conn connection
     */
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









