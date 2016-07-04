package siedlervoncatan.utility;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import siedlervoncatan.sound.Sound;

public class Error
{
    private Alert alert;

    /**
     * Erzeugt einen Alert vom Typ ERROR mit dem ContentText text.
     * 
     * @param text
     */
    public Error(String text)
    {
        this.alert = new Alert(AlertType.ERROR);
        this.alert.getDialogPane().getScene().getStylesheets().add(Pfade.STYLESHEET);
        this.alert.setTitle("Fehler");
        this.alert.setContentText(text);
        this.alert.initStyle(StageStyle.UNDECORATED);
    }

    public Optional<ButtonType> showAndWait()
    {
        Sound.getInstanz().playSoundeffekt(Sound.ERROR_CLIP);
        Optional<ButtonType> result = this.alert.showAndWait();
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        return result;
    }

}
