package scheduler.objects;

/**
 * @author Brayden McArthur
 * Version 1.0
 * Create a Contact object with data pulled from the database.
 */
public class Contact {

    //Variables to store the contact information.
    private int contactId;
    private String contactName;
    private String contactEmail;

    /**
     * Constructor for creating a valid Contact.
     * @param id An integer pulled from the Contact_ID field from the database.
     * @param name A string pulled from the Contact_Name field from the database.
     * @param email A string pulled from the Email field from the database.
     */
    public Contact(int id, String name, String email){

        setContactId(id);
        setContactName(name);
        setContactEmail(email);

    }

    /**
     * Sets the Contact ID.
     * @param id An integer to store the Contact ID.
     */
    public void setContactId(int id){
        this.contactId = id;
    }

    /**
     * Gets the Contact ID.
     * @return Returns the Contact ID as an integer.
     */
    public int getContactId(){
        return this.contactId;
    }

    /**
     * Sets the Contact Name.
     * @param name A string to store the Contact Name.
     */
    public void setContactName(String name){
        this.contactName = name;
    }

    /**
     * Gets the Contact Name.
     * @return Returns the Contact Name as a string.
     */
    public String getContactName(){
        return this.contactName;
    }

    /**
     * Sets the Contact Email.
     * @param email A string to store the Contact Email.
     */
    public void setContactEmail(String email){
        this.contactEmail = email;
    }

    /**
     * Gets the Contact Email.
     * @return Returns the Contact Email as a string.
     */
    //This currently does not get used in the program, but is kept in case it is ever needed in the future.
    public String getContactEmail(){
        return this.contactEmail;
    }

}
