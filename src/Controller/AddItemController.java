package Controller;

import Model.Item;
import Model.User;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddItemController implements Initializable {
    
    @FXML private TextField codeTf;
    @FXML private TextField nameTf;
    @FXML private ComboBox<String> typeBox;
    @FXML private TextField costTf;
    
    private User userLoggedIn;
    Feature feature = new Feature();
    
    public void getUserLoggedIn(User user)
    {
        userLoggedIn = user;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Item> items = feature.loadItems();
        boolean checker = false;
        int code = 0;
        
        while (!checker)
        {
            code = (int)(Math.random()*((1000000-1) + 1)) + 1;
            for (Item i : items)
            {
                if (i.getCode() != code)
                {
                    checker = true; // This is to ensure no code duplicates can be made
                }
            }
        }
        codeTf.setText(code + ""); // Easy to convert number into a String
        codeTf.setEditable(false); // So the user cannot edit the code that was produced
    }
    
    @FXML
    public void confirmAction(ActionEvent event) throws IOException
    {
        try
        {
            Integer code = Integer.parseInt(codeTf.getText());
            String name = nameTf.getText();
            String type = typeBox.getValue();
            Double cost = Double.parseDouble(costTf.getText());
            
            if (name.isEmpty() || type.isEmpty())
            {
                feature.alert("Error", "Input all information required");
            }
            else
            {
                File file = new File(feature.filePathData("items"));
                FileWriter writer = new FileWriter(file, true);
                writer.write(name + "," + type + "," + code + "," + cost + "," + true + "," + "library" + System.lineSeparator());
                writer.close();
                feature.alert("Information", "Item has been created");
                
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/View/libraryMain.fxml"));
                Parent parent = loader.load();
                
                Scene libraryScene = new Scene(parent);
                LibraryMainController controller = loader.getController(); 
                controller.userMessage(userLoggedIn);
                
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(libraryScene);
                window.show();
            }
        }
        catch(NumberFormatException e) // This is to check the Cost
        {
            feature.alert("Error", "Please enter a number");
        }
        catch(NullPointerException n)
        {
            feature.alert("Error", "Please pick a type");
        }
    }
    
    @FXML
    public void backAction(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/libraryMain.fxml"));
            Parent parent = loader.load();
            
            Scene libraryScene = new Scene(parent);
            
            LibraryMainController controller = loader.getController();
            controller.userMessage(userLoggedIn);
            
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            window.setScene(libraryScene);
            window.show();
    } 
}