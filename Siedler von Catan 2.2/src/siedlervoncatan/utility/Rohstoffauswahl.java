package siedlervoncatan.utility;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javafx.stage.Stage;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.view.ViewController;
import siedlervoncatan.view.controller.RohstoffauswahlController;

public class Rohstoffauswahl implements PropertyChangeListener
{
    private Rohstoff rohstoff;
    private Stage    stage;

    public Rohstoffauswahl()
    {
        this("Wähle einen Rohstoff.");
    }

    public Rohstoffauswahl(String text)
    {
        try
        {
            ViewController viewController = new ViewController(null);
            this.stage = viewController.createStage(Pfade.ROHSTOFFAUSWAHL, text);
            RohstoffauswahlController controller = viewController.getLoader().getController();
            controller.setRohstoffauswahl(this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Rohstoff showAndWait()
    {
        this.stage.showAndWait();
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        return this.rohstoff;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        this.rohstoff = (Rohstoff) evt.getNewValue();
    }
}
