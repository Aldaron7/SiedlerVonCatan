package siedlervoncatan.view;

import javafx.scene.Node;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.view.controller.RootLayoutController;

/**
 * Controller Interface von dem alle Men�-Controller ableiten
 * 
 * @author mvr
 *
 */
public interface Controller
{
    public void setSpiel(Spiel spiel);

    public void setLayoutController(RootLayoutController layoutController);

    public void setNode(Node self);
}
