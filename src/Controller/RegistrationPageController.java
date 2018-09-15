package Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RegistrationPageController implements Initializable {
    
    Feature feature = new Feature();
    
    @FXML private TextField usernameTf;
    @FXML private TextField forenameTf;
    @FXML private TextField surnameTf;
    @FXML private TextField addressTf;
    @FXML private TextField postcodeTf;
    @FXML private PasswordField passwordPf;
    @FXML private PasswordField password2Pf;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void openLoginPage(MouseEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/loginPage.fxml"));
        Scene scene = new Scene(tableViewParent);
        
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    public void registerAction(ActionEvent event) throws IOException
    {
        String username = usernameTf.getText().trim().toLowerCase(); // Username are not case senstive 
        String forename = feature.capitalise(forenameTf.getText());
        String surname = feature.capitalise(surnameTf.getText());
        String address = addressTf.getText().trim();
        String postcode = postcodeTf.getText().trim();
        String password = passwordPf.getText();
        String password2 = password2Pf.getText();
        
        if (username.isEmpty() || forename.isEmpty() || surname.isEmpty() 
                || address.isEmpty() || postcode.isEmpty() || password.isEmpty() || password2.isEmpty())
        {
            feature.alert("Error", "Please enter all information");
        }
        else if (!password.equals(password2))
        {
            feature.alert("Error", "Password does not match, please try again!");
        }
        else if (feature.checkUsername(username))
        {
            feature.alert("Error", "Username already exists! Please try another or login!");
        }
        else
        {
            FileWriter writer = new FileWriter(feature.filePathData("users"), true);// Boolean is included to make sure the file is not overridden.
            writer.write(username + "," + forename + "," + surname + "," + address + "," + postcode + "," + "0," + password + System.lineSeparator());
            // Balance is set to zero as it is a new account
            writer.close();
            feature.alert("Success!", "You have successfully registered, please login!");
        }
    }
}
