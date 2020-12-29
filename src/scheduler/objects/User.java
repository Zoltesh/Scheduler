package scheduler.objects;

/**
 * @author Brayden McArthur
 * @version 1.0
 * A user object that has the ability to "login" to the program and is used to determine which user added/modified other
 * objects/data in the database.
 */
public class User {

    private int id;
    private String name;
    private String password;

    /**
     * Constructor for creating a valid User.
     * @param id An integer pulled from the User_ID field from the database.
     * @param name A string pulled from the User_Name field from the database.
     * @param password A string pulled from the Password field from the database.
     */
    public User(int id, String name, String password){
        setId(id);
        setName(name);
        setPassword(password);
    }

    /**
     * Sets the User ID.
     * @param id An integer to store the User ID.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Gets the User ID.
     * @return Returns the User Id as an integer.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Sets the User Name.
     * @param name A string to store the User Name.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the User Name.
     * @return Returns the User Name as a string.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Sets the Password.
     * @param password A string to store the Password.
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Gets the Password.
     * @return Returns the Password as a string.
     */
    public String getPassword(){
        return this.password;
    }

}
