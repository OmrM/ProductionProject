import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnect {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./res/Production";
    private static Connection conn;
    static final ObservableList<Product> productLine = FXCollections.observableArrayList();
    public static List<Product> getProductsDB() {
        //  Database credentials
        final String USER = "";
        final String PASS = "";
        Connection conn = null;
        Statement stmt = null;


        List<Product> products = new ArrayList<>();

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //Class.forName(new org.h2.Driver());

            //STEP 2: Open a connection
            //conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();

            String sqlOut = "SELECT * FROM PRODUCT";

            ResultSet rs = stmt.executeQuery(sqlOut); //executeQuery grabs info from the db
            rs.next();
/*            System.out.println(sqlOut);
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getString(3));*/

            //tableColumn1.setText(rs.getString(1)); //how????
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String manufacturer = rs.getString("manufacturer");
                String type = String.valueOf(rs.getType());
            }

            // STEP 4: Clean-up environment

           // ps.close();
            rs.close();

            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }
}
