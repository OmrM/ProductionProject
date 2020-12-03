import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
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
    static final ObservableList<Product> productLine = FXCollections.observableArrayList();
    static final ObservableList<ProductionRecord> productRecList = FXCollections.observableArrayList();


    private int countAU = 0 ;
    private int countVI = 0;
    private int countAM = 0 ;
    private int countVM = 0;


    /**
     * Initialize method
     *
     */
    public void initialize() throws SQLException {

        comboBoxQty.setEditable(true);
        comboBoxQty.getSelectionModel().selectFirst(); //this is to select and display the first thing on the list on the combobox. located in the produce tab
        for(int count = 1; count <= 10; count ++){
            comboBoxQty.getItems().add(String.valueOf(count +1));
        }

        for(ItemType id: ItemType.values()){
            productTypeBox.getItems().add(id.getItemType());
        }
        productTypeBox.getSelectionModel().selectFirst();//this is to select and display the first thing on the list on the choiceBox. in product line tab
        setupProductLineTable();
        //setupProductionLog();
        //outputProdLogTxt();
        //productionRecordLog.setText(ProductionRecord.)

    }



    /**
     * adds a product to the database
     */
    public void addProduct(ActionEvent event) {
        System.out.println("Add Product");
        updateProductsDB();
    }

    /**
     * records to the production log and outputs it to the text area
     *
     */
    public void recordProduction(ActionEvent event) {

       // updateProdRecordDB();
       // outputProdLogTxt();
        Product selectedProduct = listViewProds.getSelectionModel().getSelectedItem();
        // int id = Integer.parseInt(listViewProds.getId());

        int numProduced = Integer.parseInt(comboBoxQty.getValue());
        ItemType type = selectedProduct.getType();
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        String manufacturer = selectedProduct.getManufacturer();
        String serialNumber = "";

        if (type.equals(ItemType.AUDIO)) {
            countAU += numProduced;

            for (int productionRunProduct = 0; productionRunProduct < countAU; productionRunProduct++) {//
                ProductionRecord pr = new ProductionRecord(selectedProduct, productionRunProduct);

                productionRecordLog.appendText(pr.toString() + "\n");
            }

        } else if (type.equals(ItemType.VISUAL)) {
            countVI += numProduced;
                for (int productionRunProduct = 0; productionRunProduct < countVI; productionRunProduct++) {//
                    ProductionRecord pr = new ProductionRecord(selectedProduct, productionRunProduct);

                    productionRecordLog.appendText(pr.toString() + "\n");

                }

        } else if (type.equals(ItemType.AUDIO_MOBILE)) {
            countAM += numProduced;
            for (int productionRunProduct = 0; productionRunProduct < countAM; productionRunProduct++) {//
                ProductionRecord pr = new ProductionRecord(selectedProduct, productionRunProduct);

                productionRecordLog.appendText(pr.toString() + "\n");
            }

        } else{
            countVM+= numProduced;
            for (int productionRunProduct = 0; productionRunProduct < countVM; productionRunProduct++) {//
                ProductionRecord pr = new ProductionRecord(selectedProduct, productionRunProduct);

                productionRecordLog.appendText(pr.toString() + "\n");
            }
        }
/*        for (int productionRunProduct = 0; productionRunProduct < numProduced; productionRunProduct++) {//

            ProductionRecord pr = new ProductionRecord(selectedProduct, productionRunProduct);

            System.out.println(pr.toString());

        }*/
    }




    public void setupProductionLog(){//displays log database into the text area
    }


    /**
     * Gets list of products in database. puts them into the tableview*
     *
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
            //System.out.println(dbProduct.getId());
            //System.out.println(dbProduct.getManufacturer());

            productLine.add(dbProduct);//save widget/product objects to observable list
            productLine.addAll();
            columnID.setCellValueFactory(new PropertyValueFactory("id"));
            columnName.setCellValueFactory(new PropertyValueFactory("name"));
            columnManufacturer.setCellValueFactory(new PropertyValueFactory("manufacturer"));
            columnType.setCellValueFactory(new PropertyValueFactory("type"));

            //ListView listView = new ListView();
        }
        listViewProds.setItems(productLine);
/*        VBox vbox = new VBox(tableViewProducts);
        Scene scene = new Scene(vbox);*/
        rs.close();

        stmt.close();
        conn.close();
    }




    /**This method will be called when you press the add product button.
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


    /**
     * creates a connection to the database
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










