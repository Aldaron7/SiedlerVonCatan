package siedlervoncatan.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;
import siedlervoncatan.sound.Sound;

public class Error
{

    /**
     * Erzeugt einen Alert vom Typ ERROR mit dem ContentText text.
     * 
     * @param text
     */
    public Error(String text)
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().getScene().getStylesheets().add(Pfade.STYLESHEET);
        alert.setTitle("Fehler");
        alert.setContentText(text);
        alert.initStyle(StageStyle.UNDECORATED);
        Sound.getInstanz().playSoundeffekt(Sound.ERROR_CLIP);
        alert.showAndWait();
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
    }

}
