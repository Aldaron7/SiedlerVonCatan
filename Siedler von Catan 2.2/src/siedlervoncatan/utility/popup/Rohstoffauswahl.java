package siedlervoncatan.utility.popup;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javafx.stage.Stage;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.utility.Pfade;
import siedlervoncatan.view.ViewController;
import siedlervoncatan.view.controller.RohstoffauswahlController;

/**
 * Zeigt alle Rohstoffarten zur Auswahl eines Rohstoffes an
 * 
 * @author mvr
 *
 */
public class Rohstoffauswahl extends Popup implements PropertyChangeListener
{
    private Rohstoff rohstoff;
    private Stage    stage;

    public Rohstoffauswahl()
    {
        this("W�hle einen Rohstoff.");
    }

    public Rohstoffauswahl(String text)
    {
        try
        {
            ViewController viewController = new ViewController(null, null);
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
        super.animatedShowAndWait(this.stage);
        return this.rohstoff;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        this.rohstoff = (Rohstoff) evt.getNewValue();
    }
}
