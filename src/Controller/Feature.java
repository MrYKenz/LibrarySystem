package Controller;

import Model.Item;
import Model.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.control.Alert;

public class Feature 
{
    //This class was created to make my code tidier, not sure where to put it so I put it in the controller to stick to MVC
    
    public String filePathData(String fileName)
    {
        String path = "src" + File.separator + "Data" + File.separator + fileName + ".csv";
        return path;
    }
    
    public String capitalise(String word)
    {
        if (word == null || word.equals(""))
        {
            return word;
        }
        else {
        String word2 = word.trim();
        String output = word2.substring(0, 1).toUpperCase() + word2.substring(1).toLowerCase();
        return output;
        }
    }
    
    public void alert(String title, String text)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // No need for a header for this alert
        alert.setContentText(text);
        
        alert.showAndWait();
    }
    
    public boolean checkUsername(String input) throws FileNotFoundException
    {
        //This method has been put here due to both login and registration will need this
        
        boolean exist = false;
        
        File file = new File(filePathData("users"));
        Scanner scanner = new Scanner(file);
        
        while(scanner.hasNextLine())
        {
            String[] line = scanner.nextLine().split(",");
            
            if (line[0].equals(input))
            {
                exist = true;
            }
            else if (exist)
            {
                exist = true;
            }
            else
            {
                // Should not do anything
            }
        }
        
        return exist;
        // If true, duplicate has been found. If false, no duplicates
    }
    
    public ArrayList<User> loadUsers() throws FileNotFoundException
    {
        ArrayList<User> usersFromFile = new ArrayList();
        
            File file = new File(filePathData("users"));
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNextLine())
            {
                String[] line = scanner.nextLine().split(",");
                User user = new User(line[0], line[1], line[2], line[3], line[4], Double.parseDouble(line[5]), line[6]);
                usersFromFile.add(user);
            }
            
        return usersFromFile;
    }
    
    // This method will be used to load all items from the file
    public ArrayList<Item> loadItems()
    {
        ArrayList<Item> itemsFromFile = new ArrayList();
        try 
        {
            File file = new File(filePathData("items"));
            Scanner scanner = new Scanner(file);
            
            scanner.nextLine(); // Skips the first line 
            
            while (scanner.hasNextLine())
            {
                String[] line = scanner.nextLine().split(",");
                Item item = new Item(line[0], line[1], Integer.parseInt(line[2]), Double.parseDouble(line[3]), Boolean.parseBoolean(line[4]), line[5]);
                itemsFromFile.add(item);
            }
        }
        catch (FileNotFoundException e) // If the file does not exist
        {
            try 
            {
                File file = new File(filePathData("items"));
                FileWriter writer = new FileWriter(file, true);
                writer.write("name,type,code,cost,availability,username" + System.lineSeparator());
                writer.close();
            }
            catch (IOException io)
            {
                System.out.println(io.toString());
            }
        }

        // As the list will be empty if catch happens, it will only return as a empty list
        return itemsFromFile;
    }
    
    public ArrayList<Item> loadItemsForLibrary()
    {
        ArrayList<Item> returnList = new ArrayList();
        ArrayList<Item> allItems = loadItems();
        
        for (Item i : allItems)
        {
            if (i.getUsername().equals("library"))
            {
                returnList.add(i);
            }
        }
        
        return returnList;
    }
    
    public ArrayList<Item> loadItemsFromUser(User user)
    {
         ArrayList<Item> returnList = new ArrayList();
         ArrayList<Item> allItems = loadItems();
         
        try
        {
            for (Item i : allItems)
            {
                if (i.getUsername().equals(user.getUsername()))
                {
                    returnList.add(i);
//                    System.out.println(user.getUsername());
                }
            }
        }
        catch (NullPointerException e)
        {
            //Should not do anything, just return an empty list
        }
        
        return returnList;
    }
    
    public void saveItem(Item updatedItem) throws IOException
    {
        ArrayList<Item> itemsFromFile = loadItems();
        Item itemToRemove = new Item();
        
        for (Item i : itemsFromFile)
        {
            if (updatedItem.getCode().equals(i.getCode()))
            {
                itemToRemove = i;
            }
        }
        
        ArrayList<Item> updatedList = new ArrayList();
        updatedList.addAll(itemsFromFile);
        updatedList.remove(itemToRemove);
        updatedList.add(updatedItem);
        
        
        File file = new File(filePathData("items"));
        FileWriter writer = new FileWriter(file, false); // False because we want to override 
        writer.write("name,type,code,cost,availability,username" + System.lineSeparator()); //This is for the first line
        
        for (Item i : updatedList)
        {
            writer.write(i.getName() + "," + i.getType() + "," + i.getCode() + "," + i.getCost() + ","
                    + i.isAvailability() + "," + i.getUsername() + System.lineSeparator());
        }
        
        writer.close();
    }
    
    public void addBalance(User userUpdated, double amount) throws IOException
    {
        ArrayList<User> usersFromFile = loadUsers();
        User userToRemove = new User();
        userUpdated.setBalance(userUpdated.getBalance() + amount);
        
        File file = new File(filePathData("users"));
        FileWriter writer = new FileWriter(file, false); // False because we want to override 
        
        for (User u : usersFromFile)
        {
            if (u.getUsername().equals(userUpdated.getUsername()))
            {
                userToRemove = u;
            }
        }
        usersFromFile.remove(userToRemove);
        usersFromFile.add(userUpdated);
        
        // writer.write(username + "," + forename + "," + surname + "," + address + "," + postcode + "," + "0," + password + System.lineSeparator());
        
        for (User u : usersFromFile)
        {
            writer.write(u.getUsername() + "," + u.getForename() + "," + u.getSurname() + "," + u.getAddress() + "," + 
                    u.getPostcode() + "," + u.getBalance() + "," + u.getPassword() + System.lineSeparator());
        }
        
        writer.close();
    }
    
    public void deleteItem(Item itemToDelete) throws IOException
    {
        ArrayList<Item> itemsFromFile = loadItems();
        Item itemToRemove = new Item();
        
        for (Item i : itemsFromFile)
        {
            if (itemToDelete.getCode().equals(i.getCode()))
            {
                itemToRemove = i;
            }
        }
        
        ArrayList<Item> updatedList = new ArrayList();
        updatedList.addAll(itemsFromFile);
        updatedList.remove(itemToRemove);
        
        File file = new File(filePathData("items"));
        FileWriter writer = new FileWriter(file, false); // False because we want to override 
        writer.write("name,type,code,cost,availability,username" + System.lineSeparator()); //This is for the first line
        
        for (Item i : updatedList)
        {
            writer.write(i.getName() + "," + i.getType() + "," + i.getCode() + "," + i.getCost() + ","
                    + i.isAvailability() + "," + i.getUsername() + System.lineSeparator());
        }
        
        writer.close();
    }
}