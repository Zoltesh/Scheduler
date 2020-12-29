package view_control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import scheduler.Main;
import scheduler.objects.Customer;
import scheduler.objects.MasterList;
import scheduler.tools.Alerts;
import scheduler.tools.DateAndTime;
import scheduler.tools.EditDatabase;
import scheduler.tools.Fetcher;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static view_control.LoginWindowController.currentUser;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Provides the methods necessary to add a Customer object to the database.
 */
public class AddCustomerWindowController implements Initializable {

    MasterList masterList = new MasterList();
    private int nextId;

    Fetcher fetcher = new Fetcher();
    Alerts alerts = new Alerts();
    DateAndTime dateAndTime = new DateAndTime();
    EditDatabase editDatabase = new EditDatabase();

    @FXML
    private TextField fullName;
    @FXML
    private TextField streetAddress;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField phoneNumber;
    @FXML
    private ComboBox<String> country;
    @FXML
    private ComboBox<String> division;

    public AddCustomerWindowController(){}


    /**
     * Initializes the country combo box to the list of all countries listed in the database.
     * @param url A URL.
     * @param resourceBundle A ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            masterList.setAllIds(fetcher.fetchAllIds("customers", "Customer_ID"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            masterList.setAllCountries(fetcher.fetchCountries());
        } catch (Exception e) {
            e.printStackTrace();
        }

        country.setItems(masterList.getAllCountries());

    }

    /**
     * When a country is selected from the country combo box, the division combo box is set to a list of all divisions
     * that are associated with the chosen country in the database
     * @throws Exception Throws Exception if there is an error in Fetcher.
     * @see scheduler.tools.Fetcher
     */
    public void countryComboBoxOnAction() throws Exception{


        try {
            masterList.setAllDivisions(fetcher.fetchDivisions(country.getSelectionModel().getSelectedItem()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        division.setItems(masterList.getAllDivisions());

    }

    /**
     * If valid input has been entered, a Customer object is created from the provided input and inserted into the database.
     * @throws Exception Throws Exception if there is an error in DateAndTime, EditDatabase, or Fetcher.
     * @see scheduler.tools.DateAndTime
     * @see scheduler.tools.EditDatabase
     * @see scheduler.tools.Fetcher
     */
    public void okButtonClicked() throws Exception{

        if(validateInput()){

            setNextId();

            Customer customer = new Customer(getNextId(), fullName.getText(), streetAddress.getText(),
                    Integer.parseInt(postalCode.getText()), Long.parseLong(phoneNumber.getText()),
                    dateAndTime.getUtcFromLocal(dateAndTime.getCurrentDateAndTime()), currentUser.getName(),
                    dateAndTime.getUtcFromLocal(dateAndTime.getCurrentDateAndTime()), currentUser.getName(),
                    fetcher.fetchDivisionId(division.getSelectionModel().getSelectedItem()));

            editDatabase.addCustomer(customer);
            cancelButtonClicked();
        }

    }

    /**
     * Sets the scene back to the Customer window, which also reloads the table view with the data from the
     * database.
     * @throws IOException Throws IOException if there is an error loading the FXML file.
     */
    public void cancelButtonClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view_control/CustomerWindowUI.fxml"));
        Parent customer = loader.load();

        Scene customerWindow = new Scene(customer, 1000, 500);
        Main.window.setScene(customerWindow);

    }

    /**
     * Validates the input entered by the user. All fields must not be empty and non String type fields must only
     * contain whole numbers. If invalid data is entered an Alert Box displays the error message in an alert window.
     * @return Returns True if all if statements are false.
     */
    public boolean validateInput(){

        if(fullName.getText().isEmpty()){
            alerts.alertBox("Name field cannot be empty!", "OK");
            return false;
        }

        if(streetAddress.getText().isEmpty()){
            alerts.alertBox("Address field cannot be empty!", "OK");
            return false;
        }

        if(postalCode.getText().isEmpty()){
            alerts.alertBox("Postal Code field cannot be empty!", "OK");
            return false;
        }else{
            try{
                Integer.parseInt(postalCode.getText());
            }catch(Exception e){
                alerts.alertBox("Postal Code must be a whole number!", "OK");
                return false;
            }
        }

        if(phoneNumber.getText().isEmpty()){
            alerts.alertBox("Phone Number field cannot be empty!", "OK");
            return false;
        }else{
            try{
                Long.parseLong(phoneNumber.getText());
            }catch(Exception e){
                alerts.alertBox("Phone Number field must contain a whole number!", "OK");
                return false;
            }
        }

        if(country.getSelectionModel().getSelectedItem() == null){
            alerts.alertBox("Country field cannot be blank!", "OK");
            return false;
        }
        if(division.getSelectionModel().getSelectedItem() == null){
            alerts.alertBox("Division field cannot be blank!", "OK");
            return false;
        }

        return true;
    }

    /**
     * Sets the next available ID for the ID column of a particular table.
     */
    public void setNextId(){


        masterList.getAllIds().sort(Integer::compareTo);


         //Checks for the next available ID by adding one to the ith item and comparing it to the ith item. If they are
         //NOT equal, set the nextID to the ith item + 1.

        for(int i = 0; i < masterList.getAllIds().size() - 1; i++){

            if (masterList.getAllIds().get(0) > 1) {
                this.nextId = 1;
                return;

            }

            if(!((masterList.getAllIds().get(i) + 1) == masterList.getAllIds().get(i + 1))){
                this.nextId = masterList.getAllIds().get(i) + 1;

                return;
            }
        }

    }

    /**
     * Gets the NextId
     * @return Returns the NextId as an integer.
     */
    public int getNextId(){
        return this.nextId;
    }

}
