package siedlervoncatan.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.view.PopupController;

/**
 * Controller für die Anzeige eines Info Popups
 * 
 * @author mvr
 *
 */
public class InfoController implements PopupController
{
    @FXML
    private Label textL;

    private Stage stage;

    @FXML
    private void handleClose()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        this.stage.close();
    }

    @Override
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
    }

    @Override
    public void setText(String text)
    {
        this.textL.setText(text);
    }

}
