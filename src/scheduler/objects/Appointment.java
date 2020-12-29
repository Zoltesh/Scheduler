package scheduler.objects;

import scheduler.tools.Fetcher;

/**
 * @author Brayden McArthur
 * Version 1.0
 * Create an Appointment object with appointment data pulled from the database.
 */
public class Appointment {

    Fetcher fetcher = new Fetcher();

    private int appointmentId, customerId, userId, contactId;
    private String title, description, location, type, createdBy, lastUpdatedBy, start, contactName, end, createDate, lastUpdate;

    /**
     * Constructor for creating a valid appointment.
     * @param appointmentId An integer pulled from the Appointment_ID field from the database
     * @param title A string pulled from the Title field from the database.
     * @param description A string pulled from the Description from the database.
     * @param location A string pulled from the Location field from the database.
     * @param type A string pulled from the Type field from the database.
     * @param start A string representing a date/time pulled from the Start field from the database.
     * @param end A string representing a date/time pulled from the End field from the database.
     * @param createDate A string representing a date/time pulled from the Create_Date field from the database.
     * @param createdBy A string representing a user pulled from the Created_By field from the database.
     * @param lastUpdate A string representing a date/time pulled from the Last_Update field from the database.
     * @param lastUpdatedBy A string representing a user pulled from the Last_Updated_By field from the database.
     * @param customerId An integer pulled from the Customer_ID field from the database.
     * @param userId An integer pulled from the User_ID field from the database.
     * @param contactId An integer pulled from the Contact_ID field from the database.
     * @throws Exception Throws Exception if there is a problem in the Fetcher class.
     */
    public Appointment(int appointmentId, String title, String description, String location, String type,
                       String start, String end, String createDate, String createdBy, String lastUpdate,
                       String lastUpdatedBy, int customerId, int userId, int contactId) throws Exception {

        setAppointmentId(appointmentId);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setType(type);
        setStart(start);
        setEnd(end);
        setCreateDate(createDate);
        setCreatedBy(createdBy);
        setLastUpdate(lastUpdate);
        setLastUpdatedBy(lastUpdatedBy);
        setCustomerId(customerId);
        setUserId(userId);
        setContactId(contactId);
        //Given a Contact ID, fetches the Contact Name as a string.
        setContactName(fetcher.fetchContactName(getContactId()));

    }

    /**
     * Sets the Appointment ID.
     * @param appointmentId An integer to store the Appointment ID.
     */
    public void setAppointmentId(int appointmentId){
        this.appointmentId = appointmentId;
    }

    /**
     * Gets the Appointment ID.
     * @return Returns the Appointment ID as an integer.
     */
    public int getAppointmentId(){
        return this.appointmentId;
    }

    /**
     * Sets the Customer ID.
     * @param customerId An integer to store the Customer ID.
     */
    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }

    /**
     * Gets the Customer ID.
     * @return Returns the Customer ID as an integer.
     */
    public int getCustomerId(){
        return this.customerId;
    }

    /**
     * Sets the User ID.
     * @param userId An integer to store the User ID.
     */
    public void setUserId(int userId){
        this.userId = userId;
    }

    /**
     * Gets the User ID.
     * @return Returns the User ID as an integer.
     */
    public int getUserId(){
        return this.userId;
    }

    /**
     * Sets the Contact ID.
     * @param contactId An integer to store the Contact ID.
     */
    public void setContactId(int contactId){
        this.contactId = contactId;
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
     * @param contactName A string to store the Contact Name.
     */
    public void setContactName(String contactName){
        this.contactName = contactName;
    }

    /**
     * Gets the Contact Name.
     * @return Returns the Contact Name as a string.
     */
    public String getContactName(){
        return this.contactName;
    }

    /**
     * Sets the Title.
     * @param title A string to store the Title.
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Gets the Title.
     * @return Returns the Title as a string.
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Sets the Description.
     * @param description A string to store the description.
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Gets the Description.
     * @return Returns the Description as a string.
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Sets the Location.
     * @param location A string to store the Location.
     */
    public void setLocation(String location){
        this.location = location;
    }

    /**
     * Gets the Location.
     * @return Returns the Location as a string.
     */
    public String getLocation(){
        return this.location;
    }

    /**
     * Sets the Type.
     * @param type A string to store the Type.
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Gets the Type.
     * @return Returns the Type as a string.
     */
    public String getType(){
        return this.type;
    }

    /**
     * Sets the Created By.
     * @param createdBy A string to store the Created By.
     */
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    /**
     * Gets the Created By.
     * @return Returns the Created By as a string.
     */
    public String getCreatedBy(){
        return this.createdBy;
    }

    /**
     * Sets the Last Updated By.
     * @param lastUpdatedBy A string representing a date/time.
     */
    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the Last Updated By.
     * @return Returns a string representing a date/time.
     */
    public String getLastUpdatedBy(){
        return this.lastUpdatedBy;
    }

    /**
     * Sets the Start.
     * @param start A string representing a date/time.
     */
    public void setStart(String start){
        this.start = start;
    }

    /**
     * Gets the Start.
     * @return Returns a string representing a date/time.
     */
    public String getStart(){
        return this.start;
    }

    /**
     * Sets the End.
     * @param end A string representing a date/time.
     */
    public void setEnd(String end){
        this.end = end;
    }

    /**
     * Gets the End.
     * @return Returns a string representing a date/time.
     */
    public String getEnd(){
        return this.end;
    }

    /**
     * Sets the Create Date.
     * @param createDate A string representing a date/time.
     */
    public void setCreateDate(String createDate){
        this.createDate = createDate;
    }

    /**
     * Gets the Create Date.
     * @return Returns a string representing a date/time.
     */
    public String getCreateDate(){
        return this.createDate;
    }

    /**
     * Sets the Last Update.
     * @param lastUpdate A string representing a date/time.
     */
    public void setLastUpdate(String lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the Last Update.
     * @return Returns a string representing a date/time.
     */
    public String getLastUpdate(){
        return this.lastUpdate;
    }

}
