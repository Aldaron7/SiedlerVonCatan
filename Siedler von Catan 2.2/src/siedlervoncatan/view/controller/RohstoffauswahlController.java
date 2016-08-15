package siedlervoncatan.view.controller;

import java.beans.PropertyChangeSupport;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.utility.popup.Rohstoffauswahl;
import siedlervoncatan.view.PopupController;

/**
 * Controller f�r das Anzeigen einer Rohstoffauswahl als Popup
 * 
 * @author mvr
 *
 */
public class RohstoffauswahlController implements PopupController
{
    @FXML
    private Label                 textL;

    private PropertyChangeSupport support;
    private Stage                 stage;

    public RohstoffauswahlController()
    {
        this.support = new PropertyChangeSupport(this);
    }

    @FXML
    private void handleRohstoffClicked(Event event)
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        Button button = (Button) event.getSource();
        String text = button.getText();
        Rohstoff rohstoff = Rohstoff.getRohstoff(text);
        this.support.firePropertyChange("Rohstoff", null, rohstoff);
        this.stage.close();
    }

    @Override
    public void setText(String text)
    {
        this.textL.setText(text);
    }

    public void setRohstoffauswahl(Rohstoffauswahl rohstoffauswahl)
    {
        this.support.addPropertyChangeListener(rohstoffauswahl);
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
}
