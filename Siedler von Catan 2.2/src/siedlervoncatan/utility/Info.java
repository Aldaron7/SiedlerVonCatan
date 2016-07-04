package siedlervoncatan.utility;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import siedlervoncatan.sound.Sound;

public class Info
{
    private Alert alert;

    /**
     * Erzeugt einen Alert vom Typ INFORMATION mit dem ContentText text.
     * 
     * @param text
     */
    public Info(String text)
    {
        this.alert = new Alert(AlertType.INFORMATION);
        this.alert.getDialogPane().getScene().getStylesheets().add(Pfade.STYLESHEET);
        this.alert.setGraphic(null);
        this.alert.setTitle("");
        this.alert.setHeaderText("");
        this.alert.setContentText(text);
        this.alert.initStyle(StageStyle.UNDECORATED);
    }

    public Optional<ButtonType> showAndWait()
    {
        Optional<ButtonType> result = this.alert.showAndWait();
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        return result;
    }

}
