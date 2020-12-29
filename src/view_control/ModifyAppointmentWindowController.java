package view_control;

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
import static view_control.LoginWindowController.currentUser;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Contains methods for modifying an existing Appointment record in the database.
 */
public class ModifyAppointmentWindowController implements Initializable {

    MasterList masterList = new MasterList();
    Fetcher fetcher = new Fetcher();
    Alerts alerts = new Alerts();
    DateAndTime dateAndTime = new DateAndTime();
    EditDatabase editDatabase = new EditDatabase();

    private Appointment appointment = new Appointment(0, "", "", "", "", "", "",
            "", "", "", "", 0, 0, 0);

    private ObservableList<String> allContactNames = FXCollections.observableArrayList();

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

    public ModifyAppointmentWindowController() throws Exception {}

    /**
     * Initializes data.
     * @param url A URL.
     * @param resourceBundle A ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    /**
     * Populates the TextFields/ComboBoxes with data obtained from the table. Lambda is used to set the allContactNames
     * list to a list of strings comprised of the contactName of the contact list.
     * @param appointment The selected Appointment from the appointmentTable in the AppointmentWindowController.
     * @throws Exception Throws Exception if there is an error in Fetcher.
     */
    public void populateFields(Appointment appointment) throws Exception {

        setAppointment(appointment);

        masterList.setAllContacts(fetcher.fetchAllContacts());

        //Lambda for each loop to get all contact names and put them in a local list of strings
        masterList.getAllContacts().forEach((n) -> {this.allContactNames.add(n.getContactName());});

        setAppointmentIdText(Integer.toString(appointment.getAppointmentId()));
        setTitleText(appointment.getTitle());
        setDescriptionText(appointment.getDescription());
        setLocationText(appointment.getLocation());
        setContact(appointment.getContactId());
        setTypeText(appointment.getType());
        setStartText(appointment.getStart());
        setEndText(appointment.getEnd());
        setCustomerIdText(appointment.getCustomerId());
        setUserIdText(appointment.getUserId());

    }

    /**
     * Calls the validateInput method to ensure valid data has been entered. If so, the data is matched with the
     * corresponding record in the database and updated with the new values.
     * @throws Exception Throws Exception if there is an error in EditDatabase or cancelButtonClicked().
     */
    public void okButtonClicked() throws Exception {

        if(validateInput()){

            this.appointment.setTitle(getTitleText());
            this.appointment.setDescription(getDescriptionText());
            this.appointment.setLocation(getLocationText());
            this.appointment.setType(getTypeText());
            this.appointment.setStart(dateAndTime.getUtcFromLocal(getStartText()));
            this.appointment.setEnd(dateAndTime.getUtcFromLocal(getEndText()));
            this.appointment.setLastUpdate(dateAndTime.getUtcFromLocal(dateAndTime.getCurrentDateAndTime()));
            this.appointment.setLastUpdatedBy(currentUser.getName());
            this.appointment.setCustomerId(Integer.parseInt(getCustomerIdText()));
            this.appointment.setUserId(Integer.parseInt(getUserIdText()));
            this.appointment.setContactId(masterList.getAllContacts().get(contactComboBox.getSelectionModel().getSelectedIndex()).getContactId());

            editDatabase.modifyAppointment(this.appointment);
            cancelButtonClicked();
        }
    }

    /**
     * Sets the scene back to the MainWindowUI.
     * @throws IOException Throws IOException if there is an error loading the FXML file.
     */
    public void cancelButtonClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view_control/ScheduleWindowUI.fxml"));
        Parent window = loader.load();
        Scene scheduleWindow = new Scene(window, 1200, 500);
        Main.window.setScene(scheduleWindow);

    }

    /**
     * Sets the text in the appointmentIdText field
     * @param idText A string containing the Appointment ID text.
     */
    public void setAppointmentIdText(String idText){
        this.appointmentIdTextField.setText(idText);
    }

    /**
     * Gets the appointmentIdText field.
     * @return Returns a string containing the text from the appointmentIdTextField.
     */
    public String getAppointmentIdText(){
        return this.appointmentIdTextField.getText();
    }

    /**
     * Sets the text in the titleTextField.
     * @param titleText A string containing the Title text.
     */
    public void setTitleText(String titleText){
        this.titleTextField.setText(titleText);
    }

    /**
     * Gets the titleTextField text.
     * @return Returns a string containing the text from the titleTextField.
     */
    public String getTitleText(){
        return this.titleTextField.getText();
    }

    /**
     * Sets the text in the descriptionTextField.
     * @param descriptionText A string containing the Description text.
     */
    public void setDescriptionText(String descriptionText){
        this.descriptionTextField.setText(descriptionText);
    }

    /**
     * Gets the descriptionTextField text.
     * @return Returns a string containing the text from the descriptionTextField.
     */
    public String getDescriptionText(){
        return this.descriptionTextField.getText();
    }

    /**
     * Sets the text in the locationTextField.
     * @param locationText A string containing the Location text.
     */
    public void setLocationText(String locationText){
        this.locationTextField.setText(locationText);
    }

    /**
     * Gets the locationTextField text.
     * @return Returns a string containing the text from the locationTextField.
     */
    public String getLocationText(){
        return this.locationTextField.getText();
    }

    /**
     * Sets the contactComboBox list.
     * @param id An integer containing the Contact ID so that the associated Contact Name can be extracted.
     */
    public void setContact(int id) {
        this.contactComboBox.setItems(getAllContactNames());
        this.contactComboBox.getSelectionModel().select(getAllContactNames().get(id - 1));

    }

    /**
     * Sets the text in the typeTextField.
     * @param typeText A string containing the Type text.
     */
    public void setTypeText(String typeText){
        this.typeTextField.setText(typeText);
    }

    /**
     * Gets the typeTextField text.
     * @return Returns a string containing the text from the typeTextField.
     */
    public String getTypeText(){
        return this.typeTextField.getText();
    }

    /**
     * Sets the text in the startTextField.
     * @param startText A string containing the Start text.
     */
    public void setStartText(String startText){
        this.startTextField.setText(startText);
    }

    /**
     * Gets the startTextField text.
     * @return Returns a string containing the text from the startTextField.
     */
    public String getStartText(){
        return this.startTextField.getText();
    }

    /**
     * Sets the endTextField text.
     * @param endText A string containing the End text.
     */
    public void setEndText(String endText){
        this.endTextField.setText(endText);
    }

    /**
     * Gets the endTextField text.
     * @return Returns a string containing the text from the endTextField.
     */
    public String getEndText(){
        return this.endTextField.getText();
    }

    /**
     * Sets the text in the customerIdTextField.
     * @param customerId An integer containing the customerId.
     */
    public void setCustomerIdText(int customerId){
        this.customerIdTextField.setText(Integer.toString(customerId));
    }

    /**
     * Gets the customerIdTextField text.
     * @return Returns a string containing the text from the customerIdTextField.
     */
    public String getCustomerIdText(){
        return this.customerIdTextField.getText();
    }

    /**
     * Sets the text in the UserIdTextField.
     * @param userId An integer containing the userId.
     */
    public void setUserIdText(int userId){
        this.userIdTextField.setText(Integer.toString(userId));
    }

    /**
     * Gets the userIdTextField text.
     * @return Returns a string containing the text from the userIdTextField.
     */
    public String getUserIdText(){
        return this.userIdTextField.getText();
    }

    /**
     * Gets the ObservableList of strings containing the Contact Names.
     * @return Returns an ObservableList of strings containing Contact Names.
     */
    public ObservableList<String> getAllContactNames(){
        return this.allContactNames;
    }

    /**
     * Sets the current Appointment.
     * @param appointment An Appointment.
     */
    public void setAppointment(Appointment appointment){
        this.appointment = appointment;
    }

    /**
     * Gets the current Appointment.
     * @return Returns an Appointment.
     */
    public Appointment getAppointment(){
        return this.appointment;
    }

    /**
     * Validates the input provided by the user. Editable fields cannot be empty/unselected. The appropriate data types
     * must be given or an error window will be generated.
     * @return Returns True if all if statements are false.
     * @throws Exception Throws Exception if there is an error in validateCustomerId(), validateUserId(), or isOverlapping().
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

        if(dateAndTime.isOverlapping(Integer.parseInt(appointmentIdTextField.getText()), startTextField.getText(), endTextField.getText())){
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

}
