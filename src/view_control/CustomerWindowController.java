package view_control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import scheduler.Main;
import scheduler.objects.Customer;
import scheduler.objects.MasterList;
import scheduler.tools.Alerts;
import scheduler.tools.EditDatabase;
import scheduler.tools.Fetcher;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Contains methods for the Customer Window GUI.
 */
public class CustomerWindowController implements Initializable {

    MasterList masterList = new MasterList();
    EditDatabase editDatabase = new EditDatabase();
    Alerts alerts = new Alerts();
    Fetcher fetcher = new Fetcher();

    ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn, customerPostColumn, customerPhoneColumn, customerDivisionIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn, customerAddressColumn, customerDateCreatedColumn,
            customerCreatedByColumn, customerLastUpdatedColumn, customerLastUpdatedByColumn;

    public CustomerWindowController(){
    }


    /**
     * Initializes the tableview when the scene is changed to this window. The tableview gets its data from the database
     * using getter functions from the Fetcher class.
     * Uses MasterList to hold Customer objects for the class.
     * @param url A URL.
     * @param resourceBundle A ResourceBundle.
     * @see scheduler.tools.Fetcher
     * @see scheduler.objects.MasterList
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPostColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customerDateCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("customerCreateDate"));
        customerCreatedByColumn.setCellValueFactory(new PropertyValueFactory<>("customerCreatedBy"));
        customerLastUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("customerLastUpdated"));
        customerLastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("customerLastUpdatedBy"));
        customerDivisionIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivId"));

        try {
            fetcher.fetchCustomers(customerList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        masterList.setAllCustomers(customerList);
        customerTable.setItems(masterList.getAllCustomers());
        customerTable.getSelectionModel().selectFirst();

    }

    /**
     * Changes the scene to a new window where a user can enter data to create a new customer and save it to the database.
     * @throws IOException Throws IOException if there is an error loading the FXML file.
     */
    public void addButtonClicked() throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view_control/AddCustomerWindowUI.fxml"));
        Parent addCustomer = loader.load();

        Scene addCustomerWindow = new Scene(addCustomer, 400, 400);
        Main.window.setScene(addCustomerWindow);

    }

    /**
     * Changes the scene to a new window where a user can modify existing Customer data and save it to the database.
     * @throws Exception Throws Exception if there is an error loading the FXML file or calling the populateFields()
     * method.
     */
    public void modifyButtonClicked() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view_control/ModifyCustomerWindowUI.fxml"));
        Parent modifyCustomer = loader.load();
        Scene modifyCustomerWindow = new Scene(modifyCustomer, 400, 400);

        ModifyCustomerWindowController modifyCustomerWindowController = loader.getController();

        Customer customer = getSelectedCustomer();
        modifyCustomerWindowController.populateFields(customer);
        Main.window.setScene(modifyCustomerWindow);

    }

    /**
     * Deletes the selected Customer and displays an Alert Box with a confirmation that the Customer was deleted
     * successfully. Then reloads the CustomerWindowUI FXML file.
     * @throws Exception Throws Exception if there is an error in EditDatabase or loading the FXML file.
     */
    public void deleteButtonClicked() throws Exception {

        try{
            editDatabase.deleteCustomer(getSelectedCustomer());
        } catch (Exception e) {
            alerts.alertBox("No customer selected!", "OK");
            return;
        }

        alerts.alertBoxSuccess("Customer deleted successfully!");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view_control/CustomerWindowUI.fxml"));
        Parent customer = loader.load();

        Scene customerWindow = new Scene(customer, 1000, 500);
        Main.window.setScene(customerWindow);
            
    }

    /**
     * The back button changes the scene back to the main window
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
     * Gets the selected Customer object from the customerTable.
     * @return Returns the currently selected Customer object from the customerTable.
     */
    public Customer getSelectedCustomer(){
        return this.customerTable.getSelectionModel().getSelectedItem();
    }

}

