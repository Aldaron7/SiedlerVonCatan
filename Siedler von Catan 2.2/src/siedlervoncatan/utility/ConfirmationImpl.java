package siedlervoncatan.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import siedlervoncatan.sound.Sound;

public class ConfirmationImpl implements Confirmation
{
    private Alert   alert;
    private boolean result;

    public ConfirmationImpl()
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.getDialogPane().getScene().getStylesheets().add(Pfade.STYLESHEET);
        alert.initStyle(StageStyle.UNDECORATED);
        this.result = false;
    }

    @Override
    public void setText(String text)
    {
        this.alert.setContentText(text);
    }

    @Override
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
