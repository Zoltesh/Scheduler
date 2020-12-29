package view_control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import scheduler.objects.Appointment;
import scheduler.objects.MasterList;
import scheduler.tools.DateAndTime;
import scheduler.tools.Fetcher;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Contains methods for displaying reports.
 */
public class ReportsWindowController implements Initializable {

    Fetcher fetcher = new Fetcher();
    MasterList masterList = new MasterList();
    DateAndTime dateAndTime = new DateAndTime();

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private ObservableList<String> typeList = FXCollections.observableArrayList();
    private ObservableList<String> monthList = FXCollections.observableArrayList();
    private ObservableList<String> contactList = FXCollections.observableArrayList();
    private ObservableList<Appointment> contactAppointmentsList = FXCollections.observableArrayList();

    private double hoursScheduled = 0;

    @FXML
    private TableView<Appointment> contactAppointmentsTable;

    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private ComboBox<String> monthComboBox;
    @FXML
    private ComboBox<String> contactComboBox;
    @FXML
    private Label typeTotalLabel;
    @FXML
    private Label monthTotalLabel;
    @FXML
    private Label hoursTotalLabel;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> startColumn;
    @FXML
    private TableColumn<Appointment, String> endColumn;
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;

    public ReportsWindowController(){}

    /**
     * Initializes the list of Appointment objects to use in reporting. Lambda used to set the hoursScheduled to calculate
     * the total number of hours of scheduled meetings for the next month.
     * @param url A URL.
     * @param resourceBundle A ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            this.fetcher.fetchAppointments(this.appointments);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.masterList.setAllAppointments(this.appointments);

        try {
            setTypeList(masterList.getAllAppointments());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.monthList.addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        setContactList(this.appointments);

        this.typeComboBox.setItems(getTypeList());
        this.monthComboBox.setItems(this.monthList);
        this.contactComboBox.setItems(getContactList());

        this.appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        this.titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        this.endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        this.customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        this.appointments.forEach((n) -> {this.hoursScheduled += dateAndTime.hoursScheduled(n.getStart(), n.getEnd());});

        this.hoursTotalLabel.setText(Double.toString(getHoursScheduled()));

    }

    /**
     * Sets the typeTotalLabel to the number of meetings of the selected type as a string.
     * @throws Exception Throws Exception if there is an error in Fetcher.
     */
    public void typeComboBoxSelected() throws Exception {

        String selectedType = typeComboBox.getSelectionModel().getSelectedItem();
        String total = Integer.toString(fetcher.fetchTotalByType(selectedType));
        setTypeTotalLabel(total);

    }

    /**
     * Sets the monthTotalLabel to the number of meetings of the selected month as a string.
     */
    public void monthComboBoxSelected() {

        String selectedMonth = monthComboBox.getSelectionModel().getSelectedItem();
        String total = Integer.toString(getTotalByMonth(selectedMonth));
        setMonthTotalLabel(total);
    }

    /**
     * Sets the contactAppointmentsTable to a list of all Appointments associated with the selected Contact.
     */
    public void contactComboBoxSelected(){

        contactAppointmentsTable.getItems().clear();

        setContactAppointmentsList(this.appointments, contactComboBox.getSelectionModel().getSelectedItem());

        contactAppointmentsTable.setItems(getContactAppointmentsList());
        contactAppointmentsTable.refresh();

    }

    /**
     * Gets the total number of appointments from a specific month.
     * @param month A string containing the desired month as a number. Example January == 01, February == 02, so on.
     * @return Returns an integer containing the total number of Appointments for the provided month.
     */
    public int getTotalByMonth(String month){

        int total = 0;

        for(int i = 0; i < this.appointments.size(); i++){

            if(this.appointments.get(i).getStart().contains("-" + month + "-")){
                total++;
            }
        }

        return total;
    }

    /**
     * Sets the typeTotalLabel text.
     * @param total A string containing the typeTotalLabel text.
     */
    public void setTypeTotalLabel(String total){

        this.typeTotalLabel.setText(total);

    }

    /**
     * Sets the monthTotalLabel text.
     * @param total A string containing the monthTotalLabel text.
     */
    public void setMonthTotalLabel(String total) {

        this.monthTotalLabel.setText(total);

    }

    /**
     * Sets the typeList to an ObservableList of strings from an ObservableList of Appointments.
     * @param appointments An ObservableList of Appointments.
     */
    public void setTypeList(ObservableList<Appointment> appointments){

        for(int i = 0; i < appointments.size(); i++){
            if(this.typeList.contains(appointments.get(i).getType())){
                continue;
            }
            this.typeList.add(appointments.get(i).getType());
        }

    }

    /**
     * Gets the typeList.
     * @return Returns an ObservableList of strings containing the list of all Types.
     */
    public ObservableList<String> getTypeList(){
        return this.typeList;
    }

    /**
     * Sets the contactList to an ObservableList of strings.
     * @param appointments An ObservableList of Appointments from which the Appointment ID and Appointment Contact Name
     *                    will be extracted.
     */
    public void setContactList(ObservableList<Appointment> appointments){

        for(int i = 0; i < appointments.size(); i++){

            if(this.contactList.contains(Integer.toString(appointments.get(i).getContactId()) + " " + appointments.get(i).getContactName())){
                continue;
            }
            this.contactList.add(Integer.toString(appointments.get(i).getContactId()) + " " + appointments.get(i).getContactName());

        }
    }

    /**
     * Gets the contactList.
     * @return Returns an ObservableList of strings containing the list of all Contacts.
     */
    public ObservableList<String> getContactList(){
        return this.contactList;
    }

    /**
     * Sets the contactAppointmentsList to an ObservableList of Appointments.
     * @param appointments An ObservableList of Appointments.
     * @param contact A string containing a Contact ID and its associated Contact Name.
     */
    public void setContactAppointmentsList(ObservableList<Appointment> appointments, String contact){

        String[] split = contact.split(" ");
        int contactId = Integer.parseInt(split[0]);
        for(int i = 0; i < appointments.size(); i++){

            if(contactId == appointments.get(i).getContactId()){
                this.contactAppointmentsList.add(appointments.get(i));
            }
        }
    }

    /**
     * Gets the contactAppointmentsList.
     * @return Returns an ObservableList of Appointments containing all Appointments associated with a specific Contact.
     */
    public ObservableList<Appointment> getContactAppointmentsList(){
        return this.contactAppointmentsList;
    }

    /**
     * Gets the hoursScheduled.
     * @return Returns a double containing the number of hours worth of Appointments scheduled in a specific month.
     */
    public double getHoursScheduled(){
        return this.hoursScheduled;
    }

    /**
     * Sets the scene back to the MainWindowUI.
     * @throws IOException Throws IOException if there is an error loading the FXML file.
     */
    public void backButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view_control/MainWindowUI.fxml"));
        Parent window = loader.load();
        Scene mainWindow = new Scene(window, 270, 170);
        scheduler.Main.window.setScene(mainWindow);
    }

}
