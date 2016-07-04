package siedlervoncatan.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import siedlervoncatan.sound.Sound;

public class Confirmation
{
    private Alert   alert;
    private boolean result;

    public Confirmation()
    {
        this.alert = new Alert(AlertType.CONFIRMATION);
        this.alert.getDialogPane().getScene().getStylesheets().add(Pfade.STYLESHEET);
        this.alert.initStyle(StageStyle.UNDECORATED);
        this.result = false;
    }

    public void setText(String text)
    {
        this.alert.setContentText(text);
    }

    public boolean showAndWait()
    {
        this.alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK)
            {
                this.result = true;
            }
        });
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        return this.result;
    }

}
