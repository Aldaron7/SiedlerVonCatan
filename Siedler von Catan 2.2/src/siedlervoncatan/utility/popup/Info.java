package siedlervoncatan.utility.popup;

import java.io.IOException;

import javafx.stage.Stage;
import siedlervoncatan.utility.Pfade;
import siedlervoncatan.view.ViewController;

/**
 * Ein Info Popup um Informationen anzuzeigen
 * 
 * @author mvr
 *
 */
public class Info extends Popup
{
    private Stage stage;

    public Info(String text)
    {
        try
        {
            ViewController viewController = new ViewController(null, null);
            this.stage = viewController.createStage(Pfade.INFO, text);
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
