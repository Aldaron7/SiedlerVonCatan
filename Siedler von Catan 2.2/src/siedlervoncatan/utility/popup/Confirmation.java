package siedlervoncatan.utility.popup;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javafx.stage.Stage;
import siedlervoncatan.utility.Pfade;
import siedlervoncatan.view.ViewController;
import siedlervoncatan.view.controller.ConfirmationController;

public class Confirmation extends Popup implements PropertyChangeListener
{
    private Stage   stage;
    private boolean antwort;

    public Confirmation(String text)
    {
        try
        {
            ViewController viewController = new ViewController(null);
            this.stage = viewController.createStage(Pfade.CONFIRMATION, text);
            ConfirmationController controller = viewController.getLoader().getController();
            controller.setConfirmation(this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean showAndWait()
    {
        super.animatedShowAndWait(this.stage);
        return this.antwort;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        this.antwort = (boolean) evt.getNewValue();
    }

}
