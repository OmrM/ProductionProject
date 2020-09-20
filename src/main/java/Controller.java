import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.xml.transform.Result;
import java.sql.*;

public class Controller {
    @FXML
    private Label lblOutput;
    @FXML
    private TextArea taOutput;
    @FXML
    private TextField nameOne;
    @FXML
    private TextField nameTwo;
    @FXML
    private TextField manufacturerOne;
    @FXML
    private TextField manufacturerTwo;
    @FXML
    private ComboBox<String> quantityBox;
    @FXML
    private ChoiceBox choiceBox1;
    @FXML
    private ChoiceBox choiceBox2;

    public void recordProduction(ActionEvent event) {
        System.out.println("Record Production");
    }

    public void addProduct(ActionEvent event) {
        System.out.println("Add Product");


        connectToDbUpdate();
    }


    public void initialize() {
        //I should probably update the table view inside of this, so it will refresh
        quantityBox.getSelectionModel().selectFirst(); //this is to select the first thing on the list.
        for(int count = 1; count <= 10; count ++){
            quantityBox.getItems().add(String.valueOf(count));
           choiceBox1.getItems().add("Type " + count);
           choiceBox2.getItems().add("Type " + count);

        }
        choiceBox1.getSelectionModel().selectFirst();
        choiceBox2.getSelectionModel().selectFirst();




    }


    public void connectToDbUpdate() { //I want this function to be called when you press the add product button.
        //it should take what's in the textfields and put it into the table
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
            //STEP 3: Execute a query

            //for now i'm just hardcoding the values, but I need to read the text field values into these variables.
            //String productName = fxIdname.getText(); user this to pull info from text fields
            // String productName =  "egg";
            // int productId = 1;
            // String productType = "Food";
            // String productManufacturer = "walmart";

            String productName = nameOne.getText();
            String productType = "type1";
            String productManufacturer = manufacturerOne.getText();

            String productName2 = nameTwo.getText();
            String productType2 = "type2";
            String productManufacturer2 = manufacturerTwo.getText();

            stmt = conn.createStatement();


            //i'm so sorry for committing these sins. I'l rewrite this later. i'm just excited that I can get this to communicate with the database.

            //String sqlIn = "INSERT INTO PRODUCT(ID, NAME, TYPE, MANUFACTURER) VALUES($productId, '$productName', '$productType', '$productManufacturer')";
            //String sqlIn = "INSERT INTO PRODUCT(ID, NAME, TYPE, MANUFACTURER) VALUES(1, 'EGG', 'FOOD', 'WALMART')";


            String sqlIn = "UPDATE PRODUCT SET NAME = '" + productName + "', TYPE = '" + productType + "', MANUFACTURER = '" + productManufacturer + "' WHERE ID = 1";
            String sqlIn2 = "UPDATE PRODUCT SET NAME = '" + productName2 + "', TYPE = '" + productType2 + "', MANUFACTURER = '" + productManufacturer2 + "' WHERE ID = 2";

            System.out.println(sqlIn); //just putting this here to monitor the syntax of the sql statement
            stmt.executeUpdate(sqlIn); //
            stmt.executeUpdate(sqlIn2);


            String sqlOut = "SELECT * FROM PRODUCT WHERE ID = 2";



            ResultSet rs = stmt.executeQuery(sqlOut); //executeQuery grabs info from db
            rs.next();
            System.out.println(sqlOut);
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getString(3));
            System.out.println(rs.getString(4));


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