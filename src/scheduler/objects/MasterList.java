package scheduler.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Contains methods for storing information into ObservableLists with different data types so that the information can
 * be shared across the methods in the caller class. The methods are made for collecting information that is used many
 * times throughout the program.
 */
public class MasterList {

    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private ObservableList<String> allCountries = FXCollections.observableArrayList();
    private ObservableList<String> allDivisions = FXCollections.observableArrayList();
    private ObservableList<Integer> allIds = FXCollections.observableArrayList();
    private ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    public MasterList(){}

    /**
     * Sets the ObservableList of All Customers.
     * @param customerList An ObservableList of Customers.
     */
    public void setAllCustomers(ObservableList<Customer> customerList){
        this.allCustomers = customerList;
    }
    /**
     * Gets the AllCustomers ObservableList of Customers.
     * @return Returns an ObservableList of Customers.
     */
    public ObservableList<Customer> getAllCustomers(){
        return this.allCustomers;
    }

    /**
     * Sets the ObservableList of All Appointments.
     * @param appointmentList An ObservableList of Appointments.
     */
    public void setAllAppointments(ObservableList<Appointment> appointmentList){
        this.allAppointments = appointmentList;
    }

    /**
     * Gets the AllAppointments ObservableList.
     * @return Returns an ObservableList of Appointments.
     */
    public ObservableList<Appointment> getAllAppointments(){
        return this.allAppointments;
    }

    /**
     * Sets the ObservableList of AllCountries.
     * @param countries An ObservableList of Strings.
     */
    public void setAllCountries (ObservableList<String> countries){
        this.allCountries = countries;
    }

    /**
     * Gets the ObservableList of AllCountries.
     * @return Returns an ObservableList of Strings.
     */
    public ObservableList<String> getAllCountries(){
        return this.allCountries;
    }

    /**
     * Sets the ObservableList of AllDivisions.
     * @param divisions An ObservableList of Strings.
     */
    public void setAllDivisions(ObservableList<String> divisions){
        this.allDivisions = divisions;
    }

    /**
     * Gets the ObservableList of AllDivisions.
     * @return Returns an ObservableList of Strings.
     */
    public ObservableList<String> getAllDivisions(){
        return this.allDivisions;
    }

    /**
     * Sets the ObservableList of AllIds.
     * @param ids An ObservableList of integers.
     */
    public void setAllIds(ObservableList<Integer> ids){
        this.allIds = ids;
    }

    /**
     * Gets the ObservableList of AllIds.
     * @return Returns an ObservableList of integers.
     */
    public ObservableList<Integer> getAllIds(){
        return this.allIds;
    }

    /**
     * Sets the ObservableList of AllContacts.
     * @param contacts An ObservableList of Contacts.
     */
    public void setAllContacts(ObservableList<Contact> contacts){
        this.allContacts = contacts;
    }

    /**
     * Gets the ObservableList of AllContacts.
     * @return Returns an ObservableList of Contacts.
     */
    public ObservableList<Contact> getAllContacts(){
        return this.allContacts;
    }

}
