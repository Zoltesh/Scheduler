package scheduler.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Contains methods for generating custom alert boxes.
 */
public class Alerts {

    public Alerts(){}

    /**
     * Creates an Alert Box indicating an Error has occurred.
     * @param showText A string of text to be displayed in the message portion of the Alert Box.
     */
    public void alertBox(String showText, String okButton){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR");
        alert.setContentText(showText);

        ButtonType ok = new ButtonType(okButton);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ok);

        Optional<ButtonType> option = alert.showAndWait();

    }

    /**
     * Creates an Alert Box indicating that an action has been completed Successfully.
     * @param showText A string of text to be displayed in the message portion of the Alert Box.
     */
    public void alertBoxSuccess(String showText){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText("SUCCESS");
        alert.setContentText(showText);

        ButtonType ok = new ButtonType("OK");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ok);

        Optional<ButtonType> option = alert.showAndWait();

    }

    /**
     * Creates an Alert Box indicating useful Information for the user.
     * @param showText A string of text to be displayed in the message portion of the Alert Box.
     */
    public void alertBoxInformation(String showText){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText("INFORMATION");
        alert.setContentText(showText);

        ButtonType ok = new ButtonType("OK");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ok);

        Optional<ButtonType> option = alert.showAndWait();

    }

}
