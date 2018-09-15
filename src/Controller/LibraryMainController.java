package Controller;

import Model.Item;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class LibraryMainController implements Initializable {
    
    @FXML private Label messageLabel;
    @FXML private TableView<Item> libraryTv;
    @FXML private TableView<Item> userTv;
    @FXML private Button toLibraryBtn;
    @FXML private Button toUserBtn;
    @FXML private Button deleteItemBtn;
    
    Feature feature = new Feature();
    private User userLoggedIn;
    private URL url123;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        TableColumn<Item, Integer> codeItem = new TableColumn("Code");
        TableColumn<Item, String> nameItem = new TableColumn("Name");
        TableColumn<Item, String> typeItem = new TableColumn("Type");
        TableColumn<Item, Double> costItem = new TableColumn("Cost");
        
        codeItem.setCellValueFactory(new PropertyValueFactory<Item, Integer>("code"));
        nameItem.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        typeItem.setCellValueFactory(new PropertyValueFactory<Item, String>("type"));
        costItem.setCellValueFactory(new PropertyValueFactory<Item, Double>("cost"));
        
        libraryTv.getColumns().addAll(codeItem, nameItem, typeItem, costItem);
        
        TableColumn<Item, Integer> codeItem1 = new TableColumn("Code");
        TableColumn<Item, String> nameItem1 = new TableColumn("Name");
        TableColumn<Item, String> typeItem1 = new TableColumn("Type");
        TableColumn<Item, Double> costItem1 = new TableColumn("Cost");
        
        codeItem1.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameItem1.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeItem1.setCellValueFactory(new PropertyValueFactory<>("type"));
        costItem1.setCellValueFactory(new PropertyValueFactory<>("cost"));
        
        userTv.getColumns().addAll(codeItem1, nameItem1, typeItem1, costItem1);
        
        toLibraryBtn.disableProperty().bind(Bindings.isEmpty(userTv.getSelectionModel().getSelectedItems()));
        toUserBtn.disableProperty().bind(Bindings.isEmpty(libraryTv.getSelectionModel().getSelectedItems()));
        deleteItemBtn.disableProperty().bind(Bindings.isEmpty(libraryTv.getSelectionModel().getSelectedItems()));
        
        url123 = url;
    }
    
    @FXML
    public void userMessage(User user)
    {
        DecimalFormat df2 = new DecimalFormat("0.00"); //As currency are in 2dp 
        userLoggedIn = user;
        messageLabel.setText("You are logged in as: " + userLoggedIn.getForename() + " " + userLoggedIn.getSurname() 
                + ", your current balance is: £" + df2.format(userLoggedIn.getBalance()));
        
        loadLibrary();
        loadUserTable();
    }
    
    public void loadLibrary()
    {
        ArrayList<Item> itemsForLibrary = feature.loadItemsForLibrary();
        ObservableList<Item> items = FXCollections.observableArrayList(itemsForLibrary);
        libraryTv.setItems(items);
    }
    
    public void loadUserTable()
    {
        ArrayList<Item> itemsForUser = feature.loadItemsFromUser(userLoggedIn);
        ObservableList<Item> items1 = FXCollections.observableArrayList(itemsForUser);
        userTv.setItems(items1);
    }
    
    @FXML
    public void toUserTv(ActionEvent event) throws IOException
    {
        Item item = libraryTv.getSelectionModel().getSelectedItem();
        item.setAvailability(false);
        item.setUsername(userLoggedIn.getUsername());
        feature.saveItem(item);
        
        loadLibrary();
        loadUserTable();
    }
    
    @FXML
    public void toLibrary(ActionEvent event) throws IOException
    {
        Item item = userTv.getSelectionModel().getSelectedItem();
        item.setAvailability(true);
        item.setUsername("library");
        feature.saveItem(item);
        
        loadLibrary();
        loadUserTable();
    }
    
    @FXML
    public void addBalanceAction(ActionEvent event) throws IOException, NumberFormatException
    {
    
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Balance Input");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter the balance you want to add on your account: ");
        
        double input = Double.parseDouble(dialog.showAndWait().get());
        //System.out.println(input);
        
        feature.addBalance(userLoggedIn, input);
        
        feature.alert("Information", "Balance has been added!");
        
        userMessage(userLoggedIn); // To update the balance automatically
    }
    
    @FXML
    public void addItemAction(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/addItem.fxml"));
        Parent parent = loader.load();
        
        Scene libraryScene = new Scene(parent);
        
        AddItemController controller = loader.getController();
        controller.getUserLoggedIn(userLoggedIn);
        
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(libraryScene);
        window.show();
    }
   
    
    @FXML
    public void deleteItemAction(ActionEvent event) throws IOException
    {
        Item itemChosen = libraryTv.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm!");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete: " + itemChosen.getName() + "?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK)
        {
            feature.deleteItem(itemChosen);
            
            loadLibrary();
            loadUserTable();
        } 
        else 
        {
            // Empty - Should not do anything 
        }
    }
    
    
    @FXML
    public void logOffAction(ActionEvent event) throws IOException
    {
        userLoggedIn = null; // Is this really needed?
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/View/loginPage.fxml"));
        Scene scene = new Scene(tableViewParent);
        
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
}