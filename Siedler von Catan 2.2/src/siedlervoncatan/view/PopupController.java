package siedlervoncatan.view;

import javafx.stage.Stage;
import siedlervoncatan.spiel.Spiel;

/**
 * Controller Interface von dem alle Popup-Menü-Controller ableiten
 * 
 * @author mvr
 *
 */
public interface PopupController
{
    public void setStage(Stage stage);

    public void setSpiel(Spiel spiel);

    public void setText(String text);
}
