package siedlervoncatan.utility.popup;

import java.io.IOException;

import javafx.stage.Stage;
import siedlervoncatan.utility.Pfade;
import siedlervoncatan.view.ViewController;

/**
 * Ein Error Popup zur Anzeige ungültiger Aktionen
 * 
 * @author mvr
 *
 */
public class Error extends Popup
{
    private Stage stage;

    public Error(String text)
    {
        try
        {
            ViewController viewController = new ViewController(null, null);
            this.stage = viewController.createStage(Pfade.ERROR, text);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showAndWait()
    {
        super.animatedShowAndWait(this.stage);
    }

}
