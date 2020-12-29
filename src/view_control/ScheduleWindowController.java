package view_control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import scheduler.Main;
import scheduler.objects.Appointment;
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
 * Contains methods for viewing the current list of Appointments in the database and switching to the add/modify appointments
 * windows.
 */
public class ScheduleWindowController implements Initializable {

    MasterList masterList = new MasterList();
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    ObservableList<Appointment> sameWeekAppointments = FXCollections.observableArrayList();
    ObservableList<Appointment> sameMonthAppointments = FXCollections.observableArrayList();
    Fetcher fetcher = new Fetcher();
    EditDatabase editDatabase = new EditDatabase();
    DateAndTime dateAndTime = new DateAndTime();
    Alerts alerts = new Alerts();

    @FXML
    private ToggleGroup appointmentToggleGroup = new ToggleGroup();
    @FXML
    private RadioButton weekRadioButton = new RadioButton();
    @FXML
    private RadioButton monthRadioButton = new RadioButton();
    @FXML
    private RadioButton allRadioButton = new RadioButton();

    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableView<Appointment> appointmentTableWeek;
    @FXML
    private TableView<Appointment> appointmentTableMonth;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;
//    @FXML
//    private TableColumn<Appointment, Integer> userIdColumn;
    @FXML
    private TableColumn<Appointment, String> contactColumn;

    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, String> startColumn;
    @FXML
    private TableColumn<Appointment, String> endColumn;
//    @FXML
//    private TableColumn<Appointment, String> createDateColumn;
//    @FXML
//    private TableColumn<Appointment, String> createdByColumn;
//    @FXML
//    private TableColumn<Appointment, String> lastUpdateColumn;
//    @FXML
//    private TableColumn<Appointment, String> lastUpdatedByColumn;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumnWeek;
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumnWeek;
//    @FXML
//    private TableColumn<Appointment, Integer> userIdColumnWeek;
    @FXML
    private TableColumn<Appointment, String> contactColumnWeek;

    @FXML
    private TableColumn<Appointment, String> titleColumnWeek;
    @FXML
    private TableColumn<Appointment, String> descriptionColumnWeek;
    @FXML
    private TableColumn<Appointment, String> locationColumnWeek;
    @FXML
    private TableColumn<Appointment, String> typeColumnWeek;
    @FXML
    private TableColumn<Appointment, String> startColumnWeek;
    @FXML
    private TableColumn<Appointment, String> endColumnWeek;
//    @FXML
//    private TableColumn<Appointment, String> createDateColumnWeek;
//    @FXML
//    private TableColumn<Appointment, String> createdByColumnWeek;
//    @FXML
//    private TableColumn<Appointment, String> lastUpdateColumnWeek;
//    @FXML
//    private TableColumn<Appointment, String> lastUpdatedByColumnWeek;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumnMonth;
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumnMonth;
//    @FXML
//    private TableColumn<Appointment, Integer> userIdColumnMonth;
    @FXML
    private TableColumn<Appointment, String> contactColumnMonth;

    @FXML
    private TableColumn<Appointment, String> titleColumnMonth;
    @FXML
    private TableColumn<Appointment, String> descriptionColumnMonth;
    @FXML
    private TableColumn<Appointment, String> locationColumnMonth;
    @FXML
    private TableColumn<Appointment, String> typeColumnMonth;
    @FXML
    private TableColumn<Appointment, String> startColumnMonth;
    @FXML
    private TableColumn<Appointment, String> endColumnMonth;
//    @FXML
//    private TableColumn<Appointment, String> createDateColumnMonth;
//    @FXML
//    private TableColumn<Appointment, String> createdByColumnMonth;
//    @FXML
//    private TableColumn<Appointment, String> lastUpdateColumnMonth;
//    @FXML
//    private TableColumn<Appointment, String> lastUpdatedByColumnMonth;


    public ScheduleWindowController(){}

    /**
     * Initializes the tables and fetches all Appointments in the database.
     * @param url A URL.
     * @param resourceBundle A ResourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        this.customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        //this.userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        this.contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        this.titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        this.typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        //this.createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        //this.lastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        this.startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        this.endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        //this.createDateColumn.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        //this.lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));

        this.appointmentIdColumnWeek.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        this.customerIdColumnWeek.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        //this.userIdColumnWeek.setCellValueFactory(new PropertyValueFactory<>("userId"));
        this.contactColumnWeek.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        this.titleColumnWeek.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.descriptionColumnWeek.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.locationColumnWeek.setCellValueFactory(new PropertyValueFactory<>("location"));
        this.typeColumnWeek.setCellValueFactory(new PropertyValueFactory<>("type"));
        //this.createdByColumnWeek.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        //this.lastUpdatedByColumnWeek.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        this.startColumnWeek.setCellValueFactory(new PropertyValueFactory<>("start"));
        this.endColumnWeek.setCellValueFactory(new PropertyValueFactory<>("end"));
        //this.createDateColumnWeek.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        //this.lastUpdateColumnWeek.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));

        this.appointmentIdColumnMonth.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        this.customerIdColumnMonth.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        //this.userIdColumnMonth.setCellValueFactory(new PropertyValueFactory<>("userId"));
        this.contactColumnMonth.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        this.titleColumnMonth.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.descriptionColumnMonth.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.locationColumnMonth.setCellValueFactory(new PropertyValueFactory<>("location"));
        this.typeColumnMonth.setCellValueFactory(new PropertyValueFactory<>("type"));
        //this.createdByColumnMonth.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        //this.lastUpdatedByColumnMonth.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        this.startColumnMonth.setCellValueFactory(new PropertyValueFactory<>("start"));
        this.endColumnMonth.setCellValueFactory(new PropertyValueFactory<>("end"));
        //this.createDateColumnMonth.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        //this.lastUpdateColumnMonth.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));

        try {
            this.fetcher.fetchAppointments(this.appointments);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.appointmentTableWeek.setVisible(false);
        this.appointmentTableMonth.setVisible(false);

        this.masterList.setAllAppointments(this.appointments);
        this.appointmentTable.setItems(this.masterList.getAllAppointments());
        this.appointmentTable.getSelectionModel().selectFirst();

        this.weekRadioButton.setToggleGroup(appointmentToggleGroup);
        this.monthRadioButton.setToggleGroup(appointmentToggleGroup);
        this.allRadioButton.setToggleGroup(appointmentToggleGroup);
        this.allRadioButton.setSelected(true);

    }

    /**
     * Sets the scene to the AddAppointmentWindowUI.
     * @throws IOException Throws IOException if there is an error loading the FXML file.
     */
    public void addButtonClicked() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view_control/AddAppointmentWindowUI.fxml"));
        Parent window = loader.load();
        Scene addAppointmentWindow = new Scene(window, 500, 600);
        Main.window.setScene(addAppointmentWindow);
    }

    /**
     * Sets the scene to the ModifyAppointmentWindowUI.
     * @throws Exception Throws Exception if there is an error loading the FXML file or in the ModifyAppointmentWindowController.
     */
    public void modifyButtonClicked() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view_control/ModifyAppointmentWindowUI.fxml"));
        Parent modifyAppointment = loader.load();
        Scene modifyAppointmentWindow = new Scene(modifyAppointment, 500, 600);

        ModifyAppointmentWindowController modifyAppointmentWindowController = loader.getController();

        Appointment appointment = getSelectedAppointment();
        modifyAppointmentWindowController.populateFields(appointment);
        Main.window.setScene(modifyAppointmentWindow);
    }

    /**
     * Removes the selected Appointment from the database.
     * @throws Exception Throws Exception if there is an error loading the FXML file.
     */
    public void deleteButtonClicked() throws Exception {

        //Get ID and Type fields for the appointment that will be deleted
        int id = getSelectedAppointment().getAppointmentId();
        String type = getSelectedAppointment().getType();

        try{
            editDatabase.deleteAppointment(getSelectedAppointment());
        } catch (Exception e) {
            alerts.alertBox("No appointment selected!", "OK");
            return;
        }

        alerts.alertBoxSuccess("Successfully Appointment!\nID: " + id + "\nType: " + type);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view_control/ScheduleWindowUI.fxml"));
        Parent schedule = loader.load();
        Scene scheduleWindow = new Scene(schedule, 1200, 500);
        Main.window.setScene(scheduleWindow);

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

    /**
     * Sets the appointmentTable and appointmentTableMonth to invisible and sets the appointmentTableWeek to visible. Sets
     * the appointmentTableWeek to a list of all Appointments with start dates occurring within the next week.
     * @throws Exception Throws Exception if there is an error in Fetcher.
     */
    public void weekButtonSelected() throws Exception{

        this.sameWeekAppointments.clear();
        fetcher.fetchAppointmentsWeek(this.sameWeekAppointments);

        this.appointmentTableWeek.setItems(this.sameWeekAppointments);
        this.appointmentTableWeek.setVisible(true);
        this.appointmentTable.setVisible(false);
        this.appointmentTableMonth.setVisible(false);

    }

    /**
     * Sets the appointmentTable and appointmentTableWeek to invisible and sets the appointmentTableMonth to visible. Sets
     * the appointmentTableMonth to a list of all Appointments with start dates occurring within the next month.
     * @throws Exception Throws Exception if there is an error in Fetcher.
     */
    public void monthButtonSelected() throws Exception{

        this.sameMonthAppointments.clear();
        fetcher.fetchAppointmentsMonth(this.sameMonthAppointments);

        this.appointmentTableMonth.setItems(this.sameMonthAppointments);
        this.appointmentTableMonth.setVisible(true);
        this.appointmentTable.setVisible(false);
        this.appointmentTableWeek.setVisible(false);
    }

    /**
     * Sets the appointmentTableWeek and appointmentTable Month to invisible and sets the appointmentTable to visible.
     */
    public void allButtonSelected() {

        this.appointmentTable.setVisible(true);
        this.appointmentTableWeek.setVisible(false);
        this.appointmentTableMonth.setVisible(false);
    }

    /**
     * Gets the selected Appointment from whichever table is currently visible.
     * @return Returns an Appointment.
     */
    public Appointment getSelectedAppointment(){


        if(this.appointmentTableWeek.isVisible()){
            return this.appointmentTableWeek.getSelectionModel().getSelectedItem();
        }
        if(this.appointmentTableMonth.isVisible()){
            return this.appointmentTableMonth.getSelectionModel().getSelectedItem();
        }

        return this.appointmentTable.getSelectionModel().getSelectedItem();

    }

}
