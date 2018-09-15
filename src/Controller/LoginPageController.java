package Controller;

import Model.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
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

public class LoginPageController implements Initializable {
    
    Feature feature = new Feature();
    
    @FXML private TextField usernameTf;
    @FXML private PasswordField passwordPf;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    public void openRegPage(MouseEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/registrationPage.fxml"));
        Scene scene = new Scene(tableViewParent);
        
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    
    @FXML
    public void loginAction(ActionEvent event) throws FileNotFoundException, IOException
    {
        File file = new File(feature.filePathData("users"));
        Scanner scanner = new Scanner(file);
        
        User chosenUser = null;
        
        while(scanner.hasNextLine())
        {
            String[] line = scanner.nextLine().split(",");
            
            if (line[0].equals(usernameTf.getText().toLowerCase()) && line[6].equals(passwordPf.getText()))
            {
                chosenUser = new User(line[0], line[1], line[2], line[3], line[4], Double.parseDouble(line[5]), line[6]);
            }
        }
        if (chosenUser == null)
        {
            feature.alert("Information", "Username or Password does not match, please try again!");
        }
        else
        {   
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/libraryMain.fxml"));
            Parent parent = loader.load();
            
            Scene libraryScene = new Scene(parent);
            
            LibraryMainController controller = loader.getController();
            controller.userMessage(chosenUser);
            
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            window.setScene(libraryScene);
            window.show();
            
        }

    }
}
