package scheduler.objects;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Create a Customer object with data pulled from the database.
 */
public class Customer {

    private int customerId, customerPostalCode, customerDivId;
    private long customerPhone;
    private String customerName, customerAddress, customerCreateDate, customerCreatedBy, customerLastUpdated,
            customerLastUpdatedBy;

    /**
     * Constructor for a valid Customer object.
     * @param id An integer pulled from the Customer_ID field from the database.
     * @param name A string pulled from the Customer_Name field from the database.
     * @param address A string pulled from the Address field from the database.
     * @param post An integer pulled from the Postal_Code field from the database.
     * @param phone A Long pulled from the Phone field from the database.
     * @param createDate A string representing a date/time pulled from the Create_Date field from the database.
     * @param createdBy A string representing a user pulled from the Created_By field from the database.
     * @param lastUpdate A string representing a date/time pulled from the Last_Update field from the database.
     * @param lastUpdatedBy A string representing a user pulled from the Last_Updated_By field from the database.
     * @param divId An integer pulled from the Division_ID field from the database.
     */
    public Customer(int id, String name, String address, int post, long phone, String createDate, String createdBy,
                    String lastUpdate, String lastUpdatedBy, int divId){

        setCustomerId(id);
        setCustomerName(name);
        setCustomerAddress(address);
        setCustomerPostalCode(post);
        setCustomerPhone(phone);
        setCustomerCreateDate(createDate);
        setCustomerCreatedBy(createdBy);
        setCustomerLastUpdated(lastUpdate);
        setCustomerLastUpdatedBy(lastUpdatedBy);
        setCustomerDivId(divId);

    }

    /**
     * Sets the Customer ID.
     * @param id An integer to store the Customer ID.
     */
    public void setCustomerId(int id){
        this.customerId = id;
    }

    /**
     * Gets the Customer ID.
     * @return Returns the Customer ID as an integer.
     */
    public int getCustomerId(){
        return this.customerId;
    }

    /**
     * Sets the Postal Code.
     * @param postalCode An integer to store the Postal Code.
     */
    public void setCustomerPostalCode(int postalCode){
        this.customerPostalCode = postalCode;
    }

    /**
     * Gets the Postal Code.
     * @return Returns the Postal Code as an integer.
     */
    public int getCustomerPostalCode(){
        return this.customerPostalCode;
    }

    /**
     * Sets the Phone.
     * @param phone A long to store the Phone.
     */
    public void setCustomerPhone(long phone){
        this.customerPhone = phone;
    }

    /**
     * Gets the Phone.
     * @return Returns the Phone as a long.
     */
    public long getCustomerPhone(){
        return this.customerPhone;
    }

    /**
     * Sets the Division ID.
     * @param divisionId An integer to store the Division ID.
     */
    public void setCustomerDivId(int divisionId){
        this.customerDivId = divisionId;
    }

    /**
     * Gets the Division ID.
     * @return Returns the Division ID as an integer.
     */
    public int getCustomerDivId(){
        return this.customerDivId;
    }

    /**
     * Sets the Customer Name.
     * @param name A string to store the Customer Name.
     */
    public void setCustomerName(String name){
        this.customerName = name;
    }

    /**
     * Gets the Customer Name.
     * @return Returns the Customer Name as a string.
     */
    public String getCustomerName(){
        return this.customerName;
    }

    /**
     * Sets the Address.
     * @param address A string to store the Address.
     */
    public void setCustomerAddress(String address){
        this.customerAddress = address;
    }

    /**
     * Gets the Address.
     * @return Returns the Address as a string.
     */
    public String getCustomerAddress(){
        return this.customerAddress;
    }

    /**
     * Sets the Create Date.
     * @param createDate A string representing a date/time.
     */
    public void setCustomerCreateDate(String createDate){
        this.customerCreateDate = createDate;
    }

    /**
     * Gets the Create Date.
     * @return Returns the Create Date as a string representing a date/time.
     */
    public String getCustomerCreateDate(){
        return this.customerCreateDate;
    }

    /**
     * Sets the Created By.
     * @param createdBy A string to store the Created By.
     */
    public void setCustomerCreatedBy(String createdBy){
        this.customerCreatedBy = createdBy;
    }

    /**
     * Gets the Created By.
     * @return Returns the Created By as a string.
     */
    public String getCustomerCreatedBy(){
        return this.customerCreatedBy;
    }

    /**
     * Sets the Last Updated.
     * @param lastUpdated A string representing a date/time.
     */
    public void setCustomerLastUpdated(String lastUpdated){
        this.customerLastUpdated = lastUpdated;
    }

    /**
     * Gets the Last Updated.
     * @return Returns the Last Updated as a string representing a date/time.
     */
    public String getCustomerLastUpdated(){
        return this.customerLastUpdated;
    }

    /**
     * Sets the Last Updated By.
     * @param lastUpdatedBy A string to store the Last Updated By.
     */
    public void setCustomerLastUpdatedBy(String lastUpdatedBy){
        this.customerLastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets the Last Updated By.
     * @return Returns the Last Updated By.
     */
    public String getCustomerLastUpdatedBy(){
        return this.customerLastUpdatedBy;
    }

}
