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
import scheduler.objects.Division;
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
 * Contains methods for editing a selected customer and writing the changes to the database.
 */
public class ModifyCustomerWindowController implements Initializable {

    MasterList masterList = new MasterList();
    Fetcher fetcher = new Fetcher();
    Alerts alerts = new Alerts();
    DateAndTime dateAndTime = new DateAndTime();
    EditDatabase editDatabase = new EditDatabase();
    private Customer customer = new Customer(0, "", "", 0,  0, "", "", "", "", 0);

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

    public ModifyCustomerWindowController(){}


    /**
     * Initialize the window.
     * @param url A URL.
     * @param resourceBundle A ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Populates the TextFields/ComboBoxes with data obtained from the selected Customer in the table from the previous
     * scene.
     * @param customer is the selected Customer from the customerTable in the CustomerWindowController.
     * @throws Exception Throws Exception if there is an error in Fetcher.
     */
    public void populateFields(Customer customer) throws Exception {

        //This sets the local customer to the same values as the customer that was passed in by the caller
        setCustomer(customer);

        setFullNameText(customer.getCustomerName());
        setStreetAddressText(customer.getCustomerAddress());
        setPostalCodeText(customer.getCustomerPostalCode());
        setPhoneNumberText(customer.getCustomerPhone());

        //Given a division ID from a customer object, gets the associated Division Name, Country ID, and Country Name, then
        //stores it in a Division object so the information can be extracted and set to the TextFields
        Division divisionDetails = fetcher.fetchDivisionDetails(customer.getCustomerDivId());

        setCountry(divisionDetails.getCountryName());
        setDivision(divisionDetails.getDivisionName(), getCountry());

    }

    /**
     * When a country is selected from the country ComboBox, the division ComboBox is set to a list of all divisions
     * that are associated with the chosen country in the database.
     * Uses a Try/Catch block in case there is an error in Fethcer.
     */
    public void countryComboBoxOnAction() {


        try {
            masterList.setAllDivisions(fetcher.fetchDivisions(country.getSelectionModel().getSelectedItem()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        division.setItems(masterList.getAllDivisions());

    }

    /**
     * Sets the fullNameTextField text.
     * @param fullNameText A string containing the Full Name text.
     */
    public void setFullNameText(String fullNameText){
        this.fullName.setText(fullNameText);
    }

    /**
     * Gets the fullNameTextField text.
     * @return Returns a string containing the text from the fullNameTextField.
     */
    public String getFullNameText(){
        return this.fullName.getText();
    }

    /**
     * Sets the streetAddressTextField text.
     * @param streetAddressText A string containing the Address text.
     */
    public void setStreetAddressText(String streetAddressText){
        this.streetAddress.setText(streetAddressText);
    }

    /**
     * Gets the streetAddressTextField text.
     * @return Returns a string containing the text from the streetAddressTextField.
     */
    public String getStreetAddressText(){
        return this.streetAddress.getText();
    }

    /**
     * Sets the postalCodeTextField text.
     * @param postalCodeText An integer containing the Postal Code.
     */
    public void setPostalCodeText(int postalCodeText){
        this.postalCode.setText(Integer.toString(postalCodeText));
    }

    /**
     * Gets the postalCodeTextFieldText.
     * @return Returns a string containing the text from the postalCodeTextField.
     */
    public String getPostalCodeText(){
        return this.postalCode.getText();
    }

    /**
     * Sets the phoneNumberTextField text.
     * @param phoneNumberText A long containnig the Phone Number.
     */
    public void setPhoneNumberText(long phoneNumberText){
        this.phoneNumber.setText(Long.toString(phoneNumberText));
    }

    /**
     * Gets the phoneNumber text.
     * @return Returns a string containing the text from the phoneNumber.
     */
    public String getPhoneNumberText(){
        return this.phoneNumber.getText();
    }

    /**
     * Sets the country ComboBox to an ObservableList of strings.
     * @param country A string containing a valid country name.
     * @throws Exception Throws Exception if there is an error in Fetcher.
     */
    public void setCountry(String country) throws Exception {
        this.country.setItems(fetcher.fetchCountries());
        this.country.getSelectionModel().select(country);
    }

    /**
     * Gets the selected string from the country ComboBox.
     * @return Returns a string from the selected item in the country ComboBox.
     */
    public String getCountry(){
        return this.country.getSelectionModel().getSelectedItem();
    }

    /**
     * Sets the division ComboBox to a list of strings containing the division names.
     * @param division A ComboBox containing a list of strings.
     * @param country A ComboBox containing a list of strings.
     * @throws Exception Throws Exception if there is an error in Fetcher.
     */
    public void setDivision(String division, String country) throws Exception {
        this.division.setItems(fetcher.fetchDivisions(country));
        this.division.getSelectionModel().select(division);
    }

    /**
     * Gets the selected string from the division ComboBox.
     * @return Returns a string containing the division name.
     */
    public String getDivision(){
        return this.division.getSelectionModel().getSelectedItem();
    }

    /**
     * Sets the Customer.
     * @param customer A Customer.
     */
    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    /**
     * Gets the Customer.
     * @return Returns a Customer.
     */
    public Customer getCustomer(){
        return this.customer;
    }

    /**
     * Validates the input entered by the user. All fields must not be empty and non String type fields must only
     * contain whole numbers. If invalid data is entered the alertBox method is called and displays the error message
     * in an alert window.
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
     * If valid input has been entered, a customer object is created from the provided input and written to the database
     * @throws Exception Throws Exception if there is an error in Fetcher or in cancelButtonClicked().
     */
    public void okButtonClicked() throws Exception{

        if(validateInput()){

            this.customer.setCustomerName(getFullNameText());
            this.customer.setCustomerAddress(getStreetAddressText());
            this.customer.setCustomerPostalCode(Integer.parseInt(getPostalCodeText()));
            this.customer.setCustomerPhone(Long.parseLong(getPhoneNumberText()));
            this.customer.setCustomerLastUpdated(dateAndTime.getUtcFromLocal(dateAndTime.getCurrentDateAndTime()));
            this.customer.setCustomerLastUpdatedBy(currentUser.getName());
            this.customer.setCustomerDivId(fetcher.fetchDivisionId(getDivision()));
            editDatabase.modifyCustomer(getCustomer());

            cancelButtonClicked();
        }

    }

    /**
     * Sets the scene back to the Customer window, which also reloads the table view with the data from the
     * database
     * @throws IOException Throws IOException if there is an error loading the FXML file.
     */
    public void cancelButtonClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view_control/CustomerWindowUI.fxml"));
        Parent customer = loader.load();

        Scene customerWindow = new Scene(customer, 1000, 500);
        Main.window.setScene(customerWindow);

    }

}
