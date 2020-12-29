package scheduler.tools;

import scheduler.DBConnection;
import scheduler.objects.Appointment;
import scheduler.objects.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Handles the updates to the database, including adding, modifying, and deleting customers and appointments.
 */
public class EditDatabase {

    public EditDatabase(){}

    /**
     * Inserts data from the provided customer object into the database. The data from the customer object has already
     * been formatted to match the corresponding column in the database.
     * @param customer A Customer object to be copied into the associated fields in the database.
     * @throws Exception Throws Exception if there is an error with the database.
     */
    public void addCustomer(Customer customer) throws Exception{

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("insert into customers values(" +
                customer.getCustomerId() + ",'" + customer.getCustomerName() + "'" + ",'" +
                customer.getCustomerAddress() + "'" + "," + customer.getCustomerPostalCode() + "," +
                customer.getCustomerPhone() + ",'" + customer.getCustomerCreateDate() + "'" + ",'" +
                customer.getCustomerCreatedBy() + "'" + ",'" + customer.getCustomerLastUpdated() + "'" + ",'" +
                customer.getCustomerLastUpdatedBy() + "'" + "," + customer.getCustomerDivId() + ")");

        try{
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println("Error inserting customer into database");
        }

        DBConnection.closeConnection();

    }

    /**
     * Given a customer object, updates the specified customer record in the database. Only some of the fields may be
     * updated. Customer ID, Create date, and Created By should not be changed and are thus omitted from the sql statement.
     * @param customer A Customer object that will be used to modify a corresponding/existing Customer record.
     * @throws Exception Throws Exception if there is an error with the database.
     */
    public void modifyCustomer(Customer customer) throws Exception{

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("update customers set Customer_Name='" +
                customer.getCustomerName() + "',Address='" + customer.getCustomerAddress() + "',Postal_Code=" +
                customer.getCustomerPostalCode() + ",Phone=" + customer.getCustomerPhone() + ",Last_Update='" +
                customer.getCustomerLastUpdated() + "',Last_Updated_By='" + customer.getCustomerLastUpdatedBy() +
                "',Division_ID=" + customer.getCustomerDivId() + " where Customer_ID=" + customer.getCustomerId());

        try{
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println("Error modifying customer in the database");
        }

        DBConnection.closeConnection();
    }

    /**
     * Given a Customer object, deletes the corresponding Customer record in the database by first removing all
     * associated Appointment records that contain the given Customer's ID. Then deletes the corresponding Customer record
     * in the database.
     * @param customer A customer object to be removed from the database.
     * @return Returns True if both sql statements complete successfully. Returns False if they fail.
     * @throws Exception Throws Exception if there is an error with the database.
     */
    public boolean deleteCustomer(Customer customer) throws Exception{

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;

        //Must delete associated appointments before the customer can be deleted due to foreign key constraints
        PreparedStatement statement1 = connection.prepareStatement("delete from appointments where Customer_ID=" +
                customer.getCustomerId());

        //Can delete customer now that associated appointments have been deleted first
        PreparedStatement statement2 = connection.prepareStatement("delete from customers where Customer_ID=" +
                customer.getCustomerId());

        try{
            statement1.executeUpdate();
        } catch(Exception e){
            System.out.println("Error deleting associated appointments. Customer not deleted!");
            return false;
        }

        try{
            statement2.executeUpdate();
        } catch(Exception e){
            System.out.println("Error: Associated appointments have been deleted, but customer could not be deleted!");
            return false;
        }

        DBConnection.closeConnection();
        return true;
    }

    /**
     * Inserts data from the provided Appointment object into the database. The data from the Appointment object has
     * already been formatted to match the corresponding column in the database.
     * @param appointment An Appointment object to be copied into the associated fields in the database.
     * @throws Exception Throws Exception if there is an error with the database.
     */
    public void addAppointment(Appointment appointment) throws Exception {

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("insert into appointments values(" +
                appointment.getAppointmentId() + ",'" + appointment.getTitle() + "','" + appointment.getDescription() +
                "','" + appointment.getLocation() + "','" + appointment.getType() + "','" + appointment.getStart() +
                "','" + appointment.getEnd() + "','" + appointment.getCreateDate() + "','" + appointment.getCreatedBy() +
                "','" + appointment.getLastUpdate() + "','" + appointment.getLastUpdatedBy() + "'," +
                appointment.getCustomerId() + "," + appointment.getUserId() + "," + appointment.getContactId() + ")");

        try{
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println("Error inserting appointment into database");
        }

        DBConnection.closeConnection();
    }

    /**
     * Given an Appointment object, updates the specified Appointment record in the database. Only some of the fields may
     * be updated. Appointment ID, Create Date, and Created By should not be changed are thus omitted from the sql
     * statement.
     * @param appointment An Appointment object that will be used to modify a corresponding/existing Appointment record.
     * @throws Exception Throws Exception if there is an error with the database.
     */
    public void modifyAppointment(Appointment appointment) throws Exception{

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("update appointments set Title='" +
                appointment.getTitle() + "',Description='" + appointment.getDescription() + "',Location='" +
                appointment.getLocation() + "',Type='" + appointment.getType() + "',Start='" + appointment.getStart() +
                "',End='" + appointment.getEnd() + "',Last_Update='" + appointment.getLastUpdate() + "',Last_Updated_By='" +
                appointment.getLastUpdatedBy() + "',Customer_ID=" + appointment.getCustomerId() + ",User_ID=" +
                appointment.getUserId() + ",Contact_ID=" + appointment.getContactId() + " where Appointment_ID=" +
                appointment.getAppointmentId());

        try{
            statement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }

        DBConnection.closeConnection();

    }

    /**
     * Given an Appointment object, deletes the corresponding Appointment record in the database.
     * @param appointment An Appointment object to be removed from the database.
     * @throws Exception Throws Exception if there is an error with the database.
     */
    public void deleteAppointment(Appointment appointment) throws Exception{

        DBConnection.makeConnection();
        Connection connection = DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("delete from appointments where Appointment_ID=" +
                appointment.getAppointmentId());

        try{
            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting selected appointment");
        }

        DBConnection.closeConnection();
    }

    /**
     * Creates the initial test user used for testing the program. If the test user is already in the database, print
     * a message.
     * @throws Exception Throws Exception if there is an error in the database.
     */
    public void createTestUser() throws Exception{

        scheduler.DBConnection.makeConnection();
        Connection connection = scheduler.DBConnection.connection;
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES (1,'test','test','2019-01-01 00:00:00','test','2019-01-01 00:00:00','test')");

        try{
            statement.executeUpdate();
        }catch (Exception e){
            System.out.println("test account already created");
        }

        scheduler.DBConnection.closeConnection();

    }

}
