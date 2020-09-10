import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Label lblOutput;



    public void recordProduction(ActionEvent event){
        System.out.println("Record Production");
    }
    public void addProduct(ActionEvent event){
        System.out.println("Add Product");
    }

}