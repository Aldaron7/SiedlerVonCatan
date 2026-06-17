package siedlervoncatan.view.controller;

import java.beans.PropertyChangeSupport;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.utility.popup.Confirmation;
import siedlervoncatan.view.PopupController;

/**
 * Controller zum Anzeigen eines Confirmation Popups
 * 
 * @author mvr
 *
 */
public class ConfirmationController implements PopupController
{
    @FXML
    private Label                 textL;

    private PropertyChangeSupport support;
    private Stage                 stage;

    public ConfirmationController()
    {
        this.support = new PropertyChangeSupport(this);
    }

    @FXML
    private void handleOK()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        this.support.firePropertyChange("Confirmation", null, true);
        this.stage.close();
    }

    @FXML
    private void handleAbbrechen()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        this.support.firePropertyChange("Confirmation", null, false);
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

    public void setConfirmation(Confirmation confirmation)
    {
        this.support.addPropertyChangeListener(confirmation);
    }

}
