package Model;

import java.text.DecimalFormat;

public class User 
{
    private String username; // This will act as the identifier for each user instead of ID
    private String forename;
    private String surname;
    private String address; //First line Address
    private String postcode; // To keep things simple on terms of address
    private double balance;
    private String password;

    //Constructor to make it easy to get a user
    public User(String username, String forename, String surname, String address, String postcode, double balance, String password) 
    {
        this.username = username;
        this.forename = forename;
        this.surname = surname;
        this.address = address;
        this.postcode = postcode;
        this.balance = balance;
        this.password = password;
    }
    
    public User()
    {
        // This is made in case in the future we will need to initilise 
    }

    //Using getters and setters from the IDE
    
    public String getUsername() {
        return this.username;
        
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
