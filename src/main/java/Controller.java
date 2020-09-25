import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.transform.Result;
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
    @FXML
    private ComboBox<String> quantityBox;

    @FXML
    private ChoiceBox productTypeBox;
    @FXML
    private TableColumn<?, ?> tableColumn1;

    @FXML
    private TableColumn<?, ?> tableColumn2;

    public void recordProduction(ActionEvent event) {
        System.out.println("Record Production");
    }

    public void addProduct(ActionEvent event) {
        System.out.println("Add Product");


        connectToDbUpdate();
    }


    public void initialize() {
        //I should probably update the table view inside of this, so it will refresh
        quantityBox.setEditable(true);
        quantityBox.getSelectionModel().selectFirst(); //this is to select the first thing on the list.
        for(int count = 1; count <= 10; count ++){
            quantityBox.getItems().add(String.valueOf(count));

            productTypeBox.getItems().add("Type " + count);

        }

        productTypeBox.getSelectionModel().selectFirst();




    }


    public void connectToDbUpdate() { //I want this function to be called when you press the add product button.
        //it should take what's in the text fields and put it into the table
        // it should also call the function to output that info into the tableview

        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./res/Production";

        //  Database credentials
        final String USER = "";
        final String PASS = "";
        Connection conn = null;
        Statement stmt = null;

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //Class.forName(new org.h2.Driver());

            //STEP 2: Open a connection
            //conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            //STEP 3: Execute a query

            //String varName = fxIdName.getText(); use this syntax to pull info from text fields
            String productName = name.getText();
            String productType = productTypeBox.getValue().toString();
            String productManufacturer = manufacturer.getText();






            //String sqlIn = "UPDATE PRODUCT SET NAME = '" + productName + "', TYPE = '" + productType + "', MANUFACTURER = '" + productManufacturer + "' WHERE ID = 2";
           // String sqlIn = "INSERT INTO PRODUCT(ID, NAME, MANUFACTURER, TYPE) VALUES((SELECT MAX(ID) FROM PRODUCT) +1 " + ", '" + productName + "', '" + productManufacturer + "', '" + productType + "')";
            //I wasted a lot of time trying to insert the next id; i didn't realize that the sql db already had auto_increment.

            String sqlIn = "INSERT INTO PRODUCT(NAME, MANUFACTURER, TYPE) VALUES("+ "'" + productName + "', '" + productManufacturer + "', '" + productType + "')";
            System.out.println(sqlIn); //just putting this here to monitor the syntax of the sql statement
            stmt.executeUpdate(sqlIn); //



            String sqlOut = "SELECT ID, NAME, MANUFACTURER, TYPE FROM PRODUCT";

            ResultSet rs = stmt.executeQuery(sqlOut); //executeQuery grabs info from the db
            rs.next();
            System.out.println(sqlOut);
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getString(3));

            //tableColumn1.setText(rs.getString(1)); //how????







            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            taOutput.appendText(e.toString());
        } catch (ClassNotFoundException e) {
            taOutput.appendText(e.toString());
        }
    }


}
