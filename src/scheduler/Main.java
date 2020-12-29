package scheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Brayden McArthur
 * Version 1.0
 */
public class Main extends Application {

    //Creates a static Stage and a static Scene so that the sub windows can return to the main window and/or terminate
    //the program.
    public static Stage window;
    public Scene loginWindow;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the program, passing in a Stage object to place the GUI.
     * @param primaryStage A Stage object on which to place the GUI.
     * @throws IOException If an error occurs loading the FXML files throws IOException.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = null;
        Locale currentLocale = Locale.getDefault();
        ResourceBundle resources = ResourceBundle.getBundle("scheduler/languages/resources", currentLocale);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view_control/LoginWindowUI.fxml"));
        loader.setResources(resources);
        root = loader.load();
        
        window = primaryStage;
        window.setTitle("Scheduler");
        loginWindow = new Scene(root, 300, 200);

        window.setScene(loginWindow);
        window.show();

    }

}
