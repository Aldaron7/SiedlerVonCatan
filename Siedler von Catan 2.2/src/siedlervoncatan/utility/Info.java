package siedlervoncatan.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

public class Info
{
    /**
     * Erzeugt einen Alert vom Typ INFORMATION mit dem ContentText text.
     * 
     * @param text
     */
    public Info(String text)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.getDialogPane().getScene().getStylesheets().add(Pfade.STYLESHEET);
        alert.setTitle("");
        alert.setHeaderText("");
        alert.setContentText(text);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.showAndWait();
    }

}
