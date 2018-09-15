package Model;

public class Item 
{
    private String name;
    private String type; //This can be either book or journal
    private Integer code;
    private double cost; //For every one day missed, this cost will add up to user's account
    private boolean availability;
    private String username;
    
    //Constructor is made to make the Item easier

    public Item(String name, String type, Integer code, double cost, boolean availability, String username) {
        this.name = name;
        this.type = type;
        this.code = code;
        this.cost = cost;
        this.availability = availability; //Should be set true as default 
        this.username = username; // By default it will be library, but if someone borrows it, it will become their username
    }
    
    public Item()
    {
        // Should not do anything, making this incase in the future I will need to initilize something
    }
    
    //Getters and setters are made below by the IDE

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}