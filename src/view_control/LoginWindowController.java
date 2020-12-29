package view_control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import scheduler.DBConnection;
import scheduler.objects.User;
import scheduler.tools.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Contains methods for validating user credentials, adjusting the program language based on the users' region, and
 * logging the user in to be able to use the program.
 */
public class LoginWindowController implements Initializable{

    Alerts alerts = new Alerts();

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    public static User currentUser = new User(0, "", "");
    private String username;
    private String password;

    @FXML private Label loginLabel;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label regionLabel;
    @FXML private Button loginButton;
    @FXML private Button quitButton;

    ResourceBundle resources;

    public LoginWindowController(){}


    /**
     * Tries to create a test user to setup the test account for logging in. If it is already created a message is
     * printed indicating the account is already created. If not, it creates the user in the database and confirms it by
     * printing a message.
     * @param url A URL
     * @param resources The resources to be used based on the users' region setting.
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {

        EditDatabase editDatabase = new EditDatabase();

        try {

            editDatabase.createTestUser();

            this.resources = resources;

            regionLabel.setText(resources.getString("region"));
            loginLabel.setText(resources.getString("login"));
            usernameLabel.setText(resources.getString("username"));
            passwordLabel.setText(resources.getString("password"));
            loginButton.setText(resources.getString("loginButton"));
            quitButton.setText(resources.getString("quitButton"));

        } catch (Exception e) {
            alerts.alertBox(resources.getString("noConnection"), resources.getString("ok"));
            quitButtonClicked();

        }

    }

    /**
     * Checks that the username provided matches a username in the users table of the database. If it doesn't
     * an alert box is displayed telling the user that the username does not exist. If it does exist, the password is then
     * validated. If the password and username are in the same row in the database, the user is logged in successfully.
     * If the password does not match the username, an alert box is displayed indicating a bad password.
     * Also creates a log file in the root folder of the Program to record successful or unsuccessful logins.
     * @throws Exception Throws Exception if there is an error in Fetcher, validateUsername(), validatePassword(),
     * getCurrentUserId(), or setMainWindow().
     */
    public void loginButtonClicked() throws Exception {

        DateAndTime dateAndTime = new DateAndTime();
        FileMaker fileMaker = new FileMaker();
        Fetcher fetcher = new Fetcher();

        setUsername(usernameTextField.getText());
        String dbUsername = fetcher.validateUsername(getUsername());

        if(usernameTextField.getText().isEmpty()){

            alerts.alertBox(resources.getString("empty"), resources.getString("ok"));
            return;

        }

        if (!dbUsername.equals(getUsername())){

            fileMaker.make("login_activity.txt", dateAndTime.getCurrentDateAndTime().toString() + " Invalid username: " + getUsername());

            alerts.alertBox(resources.getString("invalidUsername"), resources.getString("ok"));
            return;
        }

        setPassword(passwordTextField.getText());
        String dbPassword = fetcher.validatePassword(getUsername());

        if (dbPassword.equals(getPassword())){
            int id = fetcher.getCurrentUserId(dbUsername, dbPassword);
            setCurrentUser(id, dbUsername, dbPassword);
            setMainWindow();
        }else{

            fileMaker.make("login_activity.txt", dateAndTime.getCurrentDateAndTime().toString() + " Invalid password for username: " + getUsername());

            alerts.alertBox(resources.getString("invalidPassword"), resources.getString("ok"));
            return;
        }

        fileMaker.make("login_activity.txt", dateAndTime.getCurrentDateAndTime().toString() + " Successful login by user: " + getUsername());

        dateAndTime.checkMeetingWithinMinutes();

    }

    /**
     * Sets the username.
     * @param username A string to store the username.
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Gets the username.
     * @return Returns a string containing the username.
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Sets the password.
     * @param password A string to store the password.
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Gets the password.
     * @return Returns a string containing the password.
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Creates a user object that is used throughout the program to tell the database which user added/updated customers
     * and appointments.
     * @param id An integer obtained from the User object.
     * @param username A string obtained from the User object.
     * @param password A string obtained from the User object.
     */
    public void setCurrentUser(int id, String username, String password){
        currentUser.setId(id);
        currentUser.setName(username);
        currentUser.setPassword(password);
    }

    /**
     * Changes the scene to the main window where the user can use the program.
     * @throws IOException Throws IOException if there is an error loading the FXML file.
     */
    public void setMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view_control/MainWindowUI.fxml"));
        Parent window = loader.load();
        Scene mainWindow = new Scene(window, 270, 170);
        scheduler.Main.window.setScene(mainWindow);
    }

    /**
     * Closes the stage object which terminates the program.
     */
    public void quitButtonClicked(){
        scheduler.Main.window.close();
    }

}
