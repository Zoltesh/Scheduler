package scheduler.objects;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Create a Division object with data pulled from the database.
 */
public class Division {

    private int divisionId, countryId;
    private String divisionName, countryName;

    /**
     * Constructor for a valid Division object.
     * @param id An integer pulled from the Division_ID field from the database.
     * @param name A string pulled from the Division field from the database.
     * @param countryId An integer pulled from the COUNTRY_ID field from the database.
     * @param countryName A string pulled from the Country field from the database.
     */
    public Division(int id, String name, int countryId, String countryName){

        setDivisionId(id);
        setDivisionName(name);
        setCountryId(countryId);
        setCountryName(countryName);

    }

    /**
     * Sets the Division ID.
     * @param id An integer to store the Division ID.
     */
    public void setDivisionId(int id){
        this.divisionId = id;
    }

    /**
     * Gets the Division ID.
     * @return Returns the Division ID as an integer.
     */
    public int getDivisionId(){
        return this.divisionId;
    }

    /**
     * Sets the Country ID.
     * @param id An integer to store the Country ID.
     */
    public void setCountryId(int id){
        this.countryId = id;
    }

    /**
     * Gets the Country ID.
     * @return Returns the Country ID as an integer.
     */
    public int getCountryId(){
        return this.countryId;
    }

    /**
     * Sets the Division Name.
     * @param name A string to store the Division Name.
     */
    public void setDivisionName(String name){
        this.divisionName = name;
    }

    /**
     * Gets the Division Name.
     * @return Returns a Division Name as a string.
     */
    public String getDivisionName(){
        return this.divisionName;
    }

    /**
     * Sets the Country Name.
     * @param name A string to store the Country Name.
     */
    public void setCountryName(String name){
        this.countryName = name;
    }

    /**
     * Gets the Country Name.
     * @return Returns the Country Name as a string.
     */
    public String getCountryName(){
        return this.countryName;
    }

}
