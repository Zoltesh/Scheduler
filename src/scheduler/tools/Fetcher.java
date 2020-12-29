package scheduler.tools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scheduler.DBConnection;
import scheduler.objects.Appointment;
import scheduler.objects.Contact;
import scheduler.objects.Customer;
import scheduler.objects.Division;
import java.sql.*;


/**
 * @author Brayden McArthur
 * @version 1.0
 * Contains methods to query/fetch data from the database. It is not used for modifying/deleting the database.
 * @see scheduler.tools.EditDatabase for methods to modify/delete in the database.
 */
public class Fetcher {

    DateAndTime dateAndTime = new DateAndTime();

    public Fetcher(){}

    /**
     * Gets the number of records in a table.
     * @param table A string representing a table in the database.
     * @return Returns the number of records in the specified table as an integer.
     * @throws Exception Throws Exception if there is an error with the database.
     */
    public int fetchTableSize(String table) throws Exception {

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery("select * from " + table);

        int rows = 0;
        while (resultSet.next()) {
            resultSet.last();
            rows = resultSet.getRow();
        }
        DBConnection.closeConnection();
        return rows;
    }

    /**
     * Used in conjunction with the customer object to create an ObservableList of all current Customers in the
     * database. Reads in the customers from the database and creates a Customer object from each row. The
     * Customer objects are then appended to an ObservableList of Customers provided by the caller function
     * @param customerList An ObservableList of Customers where the set of customers from the database will be stored.
     * @throws Exception Throws Exception if there is an error with the database.
     */
    public void fetchCustomers(ObservableList<Customer> customerList) throws Exception{

        customerList.clear();

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select * from customers");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){

            int id = resultSet.getInt("Customer_ID");
            String name = resultSet.getString("Customer_Name");
            String address = resultSet.getString("Address");
            int post = resultSet.getInt("Postal_Code");
            long phone = resultSet.getLong("Phone");
            String createDate = dateAndTime.getLocalFromUtc(resultSet.getString("Create_Date"));
            String createdBy = resultSet.getString("Created_By");
            String lastUpdate = dateAndTime.getLocalFromUtc(resultSet.getString("Last_Update"));
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int divId = resultSet.getInt("Division_ID");
            Customer customer = new Customer(id, name, address, post, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divId);
            customerList.add(customer);

        }
        DBConnection.closeConnection();

    }

    /**
     * Creates ObservableList of all current Appointments in the database. Reads in the Appointments from the database
     * and creates an Appointment object from each row. The Appointment objects are then appended to an ObservableList
     * of Appointments provided by the caller function.
     * @param appointmentList An ObservableList of Appointments where the set of appointments from the database will be
     * stored.
     * @throws Exception Throws Exception if there is an error with the database.
     */
    public void fetchAppointments(ObservableList<Appointment> appointmentList) throws Exception{

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select * from appointments");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){

            int appointmentId = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            String start = dateAndTime.getLocalFromUtc(resultSet.getString("Start"));
            String end = dateAndTime.getLocalFromUtc(resultSet.getString("End"));
            String createDate = dateAndTime.getLocalFromUtc(resultSet.getString("Create_Date"));
            String createdBy = resultSet.getString("Created_By");
            String lastUpdate = dateAndTime.getLocalFromUtc(resultSet.getString("Last_Update"));
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int customerId = resultSet.getInt("Customer_ID");
            int userId = resultSet.getInt("User_ID");
            int contactId = resultSet.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
            appointmentList.add(appointment);

        }

        DBConnection.closeConnection();

    }

    /**
     * Creates an ObservableList of all Appointments in the database that have Start times occurring within 1 week from
     * today's date. The Appointments are then appended to an ObservableList of Appointments provided by the caller
     * function.
     * @param appointmentList An ObservableList of Appointments where the set of appointments from the database will be
     * stored.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public void fetchAppointmentsWeek(ObservableList<Appointment> appointmentList) throws Exception {

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select * from appointments");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){

            String start = dateAndTime.getLocalFromUtc(resultSet.getString("Start"));
            DateAndTime dateAndTime = new DateAndTime();
            if(dateAndTime.compareWeek(start)){

                int appointmentId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");

                String end = dateAndTime.getLocalFromUtc(resultSet.getString("End"));
                String createDate = dateAndTime.getLocalFromUtc(resultSet.getString("Create_Date"));
                String createdBy = resultSet.getString("Created_By");
                String lastUpdate = dateAndTime.getLocalFromUtc(resultSet.getString("Last_Update"));
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");
                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end,
                        createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
                appointmentList.add(appointment);

            }

        }

        DBConnection.closeConnection();

    }

    /**
     * Creates an ObservableList of all Appointments in the database that have Start times occurring within 1 month from
     * today's date. The Appointments are then appended to an ObservableList of Appointments provided by the caller
     * function.
     * @param appointmentList An ObservableList of Appointments where the set of appointments from the database will be
     * stored.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public void fetchAppointmentsMonth(ObservableList<Appointment> appointmentList) throws Exception {

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select * from appointments");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){

            String start = dateAndTime.getLocalFromUtc(resultSet.getString("Start"));
            DateAndTime dateAndTime = new DateAndTime();
            if(dateAndTime.compareMonth(start)){

                int appointmentId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");

                String end = dateAndTime.getLocalFromUtc(resultSet.getString("End"));
                String createDate = dateAndTime.getLocalFromUtc(resultSet.getString("Create_Date"));
                String createdBy = resultSet.getString("Created_By");
                String lastUpdate = dateAndTime.getLocalFromUtc(resultSet.getString("Last_Update"));
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");
                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end,
                        createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
                appointmentList.add(appointment);

            }

        }

        DBConnection.closeConnection();

    }

    /**
     * Fetches the next available ID from a given table by sorting the list of all Customer_ID's and looping through to
     * find the next available ID.
     * @param table A string containing the table name.
     * @param column A string containing the column name.
     * @return Returns the next available ID as an integer.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public ObservableList<Integer> fetchAllIds(String table, String column) throws Exception{

        ObservableList<Integer> allIds = FXCollections.observableArrayList();

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select " + column + " from " + table);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            allIds.add(resultSet.getInt(column));
        }

        DBConnection.closeConnection();

        return allIds;
    }

    /**
     * Creates an ObservableList of strings containing all country names from the database.
     * @return Returns an ObservableList of strings. The values are the names of all countries in the database
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public ObservableList<String> fetchCountries() throws Exception{

        ObservableList<String> allCountries = FXCollections.observableArrayList();

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select Country from countries");
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            allCountries.add(resultSet.getString("Country"));
        }

        DBConnection.closeConnection();
        return allCountries;
    }

    /**
     * Provided a country name as a String value, finds the associated country ID, which can then be used to
     * find a specific first level division since the country ID is a Foreign key in the first_level_divisions table.
     * @param country A string representing a country name.
     * @return Returns an ObservableList of strings of all first level divisions associated with the given country name.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public ObservableList<String> fetchDivisions(String country) throws Exception {

        ObservableList<String> allDivisions = FXCollections.observableArrayList();
        int countryId = 0;

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select Country_ID from countries where Country='" + country + "'");
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            countryId = resultSet.getInt("Country_ID");
        }

        PreparedStatement statement2 = connection.prepareStatement("select Division from first_level_divisions where COUNTRY_ID='" + countryId + "'");
        ResultSet resultSet2 = statement2.executeQuery();

        while(resultSet2.next()){
            allDivisions.add(resultSet2.getString("Division"));
        }

        DBConnection.closeConnection();
        return allDivisions;
    }

    /**
     * Gets the First Level Division ID given a Division Name.
     * @param division A string representing a Division Name.
     * @return Returns an integer representing the associated Division ID.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public int fetchDivisionId(String division) throws Exception{

        int divisionId = 0;
        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select Division_ID from first_level_divisions where Division='" + division + "'");
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            divisionId = resultSet.getInt("Division_ID");
        }

        DBConnection.closeConnection();
        return divisionId;
    }


    /**
     * Given a Division ID, creates a Division object and sets the attributes to the corresponding attributes found in
     * the database.
     * @param divisionId A Division ID from a Customer object.
     * @return Returns a Division object.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public Division fetchDivisionDetails(int divisionId) throws Exception{

        int id = divisionId;
        String divisionName = null;
        int countryId = 0;
        String countryName = null;

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        Statement statement = connection.createStatement(ResultSet.CONCUR_READ_ONLY, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery("select Division_ID, Division, COUNTRY_ID from first_level_divisions where Division_ID='" + divisionId + "'");

        while(resultSet.next()){

            divisionName = resultSet.getString("Division");
            countryId = resultSet.getInt("COUNTRY_ID");
        }

        Statement statement2 = connection.createStatement(ResultSet.CONCUR_READ_ONLY, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet2 = statement2.executeQuery("select Country from countries where Country_ID=" + countryId);
        
        while(resultSet2.next()){
            countryName = resultSet2.getString("Country");
        }
        DBConnection.closeConnection();

        Division division = new Division(id, divisionName, countryId, countryName);

        return division;
    }

    /**
     * Creates an ObservableList of all Contacts in the database.
     * @return Returns an ObservableList of Contacts.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public ObservableList<Contact> fetchAllContacts() throws Exception{
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select * from contacts");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){

            int id = resultSet.getInt("contact_ID");
            String name = resultSet.getString("Contact_Name");
            String email = resultSet.getString("Email");

            Contact contact = new Contact(id, name, email);
            contactList.add(contact);

        }

        DBConnection.closeConnection();
        return contactList;
    }

    /**
     * Gets a Contact Name given a Contact ID.
     * @param id An integer representing a Contact ID.
     * @return Returns a string representing the associated Contact Name.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public String fetchContactName(int id) throws Exception{

        String contactName = "";
        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select Contact_Name from contacts where Contact_ID=" + id);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){

            contactName = resultSet.getString("Contact_Name");

        }

        DBConnection.closeConnection();
        return contactName;
    }

    /**
     * Checks whether a Customer ID is in the database.
     * @param id An integer representing a Customer ID.
     * @return Returns True if the Customer ID exists in the associated table and column. False if not.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public boolean validateCustomerId(int id) throws Exception{
        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select * from customers where customer_ID=" + id);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            int checkId = resultSet.getInt("Customer_ID");
            if(id == checkId){
                DBConnection.closeConnection();
                return true;
            }
        }

        DBConnection.closeConnection();
        return false;
    }

    /**
     * Validates the user id provided by the user
     * @param id a whole number provided by the user
     * @return true if the id exists in the associated table and column. False if not.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public boolean validateUserId(int id) throws Exception{
        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select * from users where user_ID=" + id);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            int checkId = resultSet.getInt("user_ID");
            if(id == checkId){
                DBConnection.closeConnection();
                return true;
            }
        }

        DBConnection.closeConnection();
        return false;
    }

    /**
     * Gets the total number of Appointments for a given Appointment Type.
     * @param type A string representing an Appointment Type.
     * @return Returns an integer representing the total number of Appointments of the given Type.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public int fetchTotalByType(String type) throws Exception{

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery("select * from appointments where Type='" + type + "'");

        if(!(resultSet == null)){
            resultSet.last();

            return resultSet.getRow();
        }

        DBConnection.closeConnection();
        return -1;
    }

    /**
     * If the username and password are correct, this method is called and sets the User object in this program to the
     * user id, username, and password found in the corresponding row of the database. The purpose of storing this info
     * into a User object in the program is to be able to track what user from the database is responsible for creating
     * and updating customers/appointments.
     * @param dbUsername A string value containing a username.
     * @param dbPassword A string value containing the password that goes with the provided username.
     * @return Returns an integer containing the User ID associated with the username and password.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public int getCurrentUserId(String dbUsername, String dbPassword) throws Exception{

        scheduler.DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select User_ID from users where User_Name='" + dbUsername + "' and Password='" + dbPassword + "'");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            int dbId = resultSet.getInt("User_ID");
            scheduler.DBConnection.closeConnection();
            return dbId;
        }

        return 0;
    }

    /**
     * Compares the username provided by the user in the TextField to the list of usernames in the database. If the
     * username is found in the database, the username has been validated.
     * @param username A string containing a username.
     * @return Returns a string containing the username from the database.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public String validateUsername(String username) throws Exception {

        DBConnection.makeConnection();
        Connection connection = scheduler.DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select User_Name from users");
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            String dbUsername = resultSet.getString("User_Name");
            if (dbUsername.equals(username)){
                DBConnection.closeConnection();
                return dbUsername;
            }
        }

        DBConnection.closeConnection();
        return "";
    }

    /**
     * Checks that the user provided username/password is a match in the database. If it is, the user can be logged in to
     * use the program. If not, an alert box is displayed.
     * @param username A string containing a username.
     * @return Returns a string containing the password from the database.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public String validatePassword(String username) throws Exception{

        DBConnection.makeConnection();
        Connection connection = scheduler.DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("select Password from users where User_Name='" + username + "'");
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
            String dbPassword = resultSet.getString("Password");
            DBConnection.closeConnection();
            return dbPassword;
        }

        DBConnection.closeConnection();
        return "";
    }

}
