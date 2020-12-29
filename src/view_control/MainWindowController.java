package view_control;

import scheduler.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Contains methods for handling button clicks on the Main Window and for loading other FXML files to change the scene.
 */
public class MainWindowController {

    public MainWindowController(){
    }

    /**
     * Changes the scene to the Customer window where actions such as adding, modifying, and deleting customers can be
     * found.
     * @throws IOException Throws IOException if there is an error loading the FXML file.
     */
    public void customerButtonClicked() throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view_control/CustomerWindowUI.fxml"));
        Parent customer = loader.load();

        Scene customerWindow = new Scene(customer, 1000, 500);
        Main.window.setScene(customerWindow);
    }

    /**
     * Changes the scene to the Schedule window where actions can be applied to setting, modifying, and deleting appointments
     * can be found
     * @throws IOException Throws IOException if there is an error loading the FXML file.
     */
    public void scheduleButtonClicked() throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view_control/ScheduleWindowUI.fxml"));
        Parent schedule = loader.load();

        Scene scheduleWindow = new Scene(schedule, 1200, 500);
        Main.window.setScene(scheduleWindow);
    }

    /**
     * Changes the scene to the Reports window where reports can be generated
     * @throws IOException Throws IOException if there is an error loading the FXML file.
     */
    public void reportsButtonClicked() throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view_control/ReportsWindowUI.fxml"));
        Parent reports = loader.load();

        Scene reportsWindow = new Scene(reports, 600, 400);
        Main.window.setScene(reportsWindow);

    }

    /**
     * Closes the Stage, terminating the program.
     */
    public void exitButtonClicked() {
        Main.window.close();
    }
}
