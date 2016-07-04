package siedlervoncatan.utility.popup;

import java.io.IOException;

import javafx.stage.Stage;
import siedlervoncatan.utility.Pfade;
import siedlervoncatan.view.ViewController;

public class Info
{
    private Stage stage;

    public Info(String text)
    {
        try
        {
            ViewController viewController = new ViewController(null);
            this.stage = viewController.createStage(Pfade.INFO, text);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showAndWait()
    {
        this.stage.showAndWait();
    }

}
