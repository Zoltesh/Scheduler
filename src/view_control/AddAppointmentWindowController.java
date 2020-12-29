package view_control;

import static view_control.LoginWindowController.currentUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import scheduler.Main;
import scheduler.objects.Appointment;
import scheduler.objects.Contact;
import scheduler.objects.MasterList;
import scheduler.tools.Alerts;
import scheduler.tools.DateAndTime;
import scheduler.tools.EditDatabase;
import scheduler.tools.Fetcher;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Contains methods for the Add Appointment GUI.
 */
public class AddAppointmentWindowController implements Initializable {

    MasterList masterList = new MasterList();
    Fetcher fetcher = new Fetcher();
    Alerts alerts = new Alerts();
    DateAndTime dateAndTime = new DateAndTime();
    EditDatabase editDatabase = new EditDatabase();

    private ObservableList<String> allContactNames = FXCollections.observableArrayList();
    private int nextId;

    @FXML
    private TextField appointmentIdTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField locationTextField;
    @FXML
    private ComboBox<String> contactComboBox;
    @FXML
    private TextField typeTextField;
    @FXML
    private TextField startTextField;
    @FXML
    private TextField endTextField;
    @FXML
    private TextField customerIdTextField;
    @FXML
    private TextField userIdTextField;

    public AddAppointmentWindowController(){}


    /**
     * Initializes all ID's in the master list to be used in generating a new ID. Initializes all contacts in the masterList
     * from the database so that the names can be extracted. Lambda is used to set the allContactNames list to a list of
     * strings comprised of the contactName portion of the contact list.
     * @param url A Url.
     * @param resourceBundle A ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            masterList.setAllIds(fetcher.fetchAllIds("appointments", "Appointment_ID"));
        } catch(Exception e){
            e.printStackTrace();
        }
        try {
            masterList.setAllContacts(fetcher.fetchAllContacts());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Lambda for each loop to get all contact names and put them in a local list of strings
        masterList.getAllContacts().forEach((n) -> {this.allContactNames.add(n.getContactName());});

        //setAllContactNames(masterList.getAllContacts());
        contactComboBox.setItems(getAllContactNames());

    }

    /**
     * Calls the validateInput method so that the data provided by the user can be validated/checked for errors. If there
     * are no errors, the data is written to the database.
     * @throws Exception Throws Exception if there is an error in the EditDatabase class.
     * @see scheduler.tools.EditDatabase
     */
    public void okButtonClicked() throws Exception {

        if(validateInput()){

            setNextId(masterList.getAllIds());

            Appointment appointment = new Appointment(getNextId(), titleTextField.getText(), descriptionTextField.getText(),
                    locationTextField.getText(), typeTextField.getText(), dateAndTime.getUtcFromLocal(startTextField.getText()),
                    dateAndTime.getUtcFromLocal(endTextField.getText()), dateAndTime.getUtcFromLocal(dateAndTime.getCurrentDateAndTime()),
                    currentUser.getName(), dateAndTime.getUtcFromLocal(dateAndTime.getCurrentDateAndTime()), currentUser.getName(),
                    Integer.parseInt(customerIdTextField.getText()), Integer.parseInt(userIdTextField.getText()),
                    //This beauty gets the contact id by getting the index of the selected item in the combo box and
                    //matches it with the index in the list of contacts and uses its id getter method.
                    masterList.getAllContacts().get(contactComboBox.getSelectionModel().getSelectedIndex()).getContactId());

            editDatabase.addAppointment(appointment);
            cancelButtonClicked();

        }
    }

    /**
     * Sets the scene back to the main schedule window.
     * @throws IOException Throws IOException if there is an error loading the FXML file.
     */
    public void cancelButtonClicked() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view_control/ScheduleWindowUI.fxml"));
        Parent window = loader.load();
        Scene scheduleWindow = new Scene(window, 1200, 500);
        Main.window.setScene(scheduleWindow);
    }

    /**
     * Validates the input provided by the user. Editable fields cannot be empty/unselected. The appropriate data types
     * must be given or an Alert Box will be displayed.
     * @return Returns True if all if statements fail.
     * @throws Exception Throws Exception if there is an error in the DateAndTime or Fetcher classes.
     * @see scheduler.tools.DateAndTime
     * @see scheduler.tools.Fetcher
     * @see scheduler.tools.Alerts
     */
    public boolean validateInput() throws Exception{

        if(titleTextField.getText().isEmpty()){
            alerts.alertBox("Appointment must have a title!", "OK");
            return false;
        }

        if(descriptionTextField.getText().isEmpty()){
            alerts.alertBox("Appointment must have a description!", "OK");
            return false;
        }

        if(locationTextField.getText().isEmpty()){
            alerts.alertBox("Appointment must have a location!", "OK");
            return false;
        }

        if(contactComboBox.getSelectionModel().getSelectedItem() == null){
            alerts.alertBox("Appointment must have a contact!", "OK");
            return false;
        }

        if(typeTextField.getText().isEmpty()){
            alerts.alertBox("Appointment must have a type!", "OK");
            return false;
        }

        if(startTextField.getText().isEmpty()){
            alerts.alertBox("Appointment must have a start time with format: yyyy-MM-dd kk:mm:ss", "OK");
            return false;
        }

        try {
            dateAndTime.formatDateAndTime(startTextField.getText());
        } catch(Exception e){
            alerts.alertBox("Could not parse Start Date/Time. Please use format: yyyy-MM-dd kk:mm:ss", "OK");
            return false;
        }
        if(!dateAndTime.compareEasternTime(startTextField.getText())){
            alerts.alertBox("Cannot schedule appointment outside of business hours!\nHours are 8:00 - 22:00 EST", "OK");
            return false;
        }

        if(endTextField.getText().isEmpty()){
            alerts.alertBox("Appointment must have an end time with format: yyyy-MM-dd kk:mm:ss", "OK");
            return false;
        }
        try{
            dateAndTime.formatDateAndTime(endTextField.getText());
        } catch(Exception e){
            alerts.alertBox("Could not parse End Date/Time. Please use format: yyyy-MM-dd kk:mm:ss", "OK");
            return false;
        }
        if(dateAndTime.isBefore(startTextField.getText(), endTextField.getText())){
            alerts.alertBox("End Time must be after Start Time!", "OK");
            return false;
        }

        if(dateAndTime.isOverlapping(-1, startTextField.getText(), endTextField.getText())){
           alerts.alertBox("Start and/or End time overlapping another appointment!", "OK");
           return false;
        }

        if(customerIdTextField.getText().isEmpty()){
            alerts.alertBox("Appointment must have a Customer ID!", "OK");
            return false;
        }
        try{
            Integer.parseInt(customerIdTextField.getText());
        } catch(Exception e){
            alerts.alertBox("Customer ID must be a whole number!", "OK");
            return false;
        }
        if(!fetcher.validateCustomerId(Integer.parseInt(customerIdTextField.getText()))){
            alerts.alertBox("Customer ID does not exist!", "OK");
            return false;
        }

        if(userIdTextField.getText().isEmpty()){
            alerts.alertBox("Appointment must have a User ID!", "OK");
            return false;
        }
        try{
            Integer.parseInt(userIdTextField.getText());
        } catch(Exception e){
            alerts.alertBox("User ID must be a whole number!", "OK");
            return false;
        }
        if(!fetcher.validateUserId(Integer.parseInt(userIdTextField.getText()))){
            alerts.alertBox("User ID does not exist!", "OK");
            return false;
        }

        return true;

    }

    /**
     * Sets the next available ID given a list of ID's.
     * @param idList An ObservableList of integers.
     */
    public void setNextId(ObservableList<Integer> idList){

        this.nextId = 0;
        masterList.getAllIds().sort(Integer::compareTo);

//      Checks for the next available ID by adding one to the ith item and comparing it to the ith item. If they are
//      NOT equal, set the nextID to the ith item + 1.

        for(int i = 0; i < idList.size() - 1; i++){

            if (idList.get(0) > 1) {
                this.nextId = 1;
                return;

            }

            if(!((idList.get(i) + 1) == idList.get(i + 1))){
                this.nextId = idList.get(i) + 1;

                return;
            }
        }

    }

    /**
     * Gets the next available ID.
     * @return Returns the next available ID as an integer.
     */
    public int getNextId(){
        return this.nextId;
    }

    /**
     * Creates a list of contact names by iterating through a list of contacts and getting the names.
     * @param contacts a list of contacts to iterate through and extract names from.
     */
    //This function is currently not needed anymore, because I have replaced it with a lambda in the initialize method.
    //Will delete later after more testing is complete
    public void setAllContactNames(ObservableList<Contact> contacts){

        for(int i = 0; i < contacts.size(); i++){

            this.allContactNames.add(contacts.get(i).getContactName());

        }
    }

    /**
     * Gets all Contact Names.
     * @return Returns an ObservableList of Contact Names.
     */
    public ObservableList<String> getAllContactNames(){
        return this.allContactNames;
    }


}
