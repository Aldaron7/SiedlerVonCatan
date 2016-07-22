package siedlervoncatan.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.view.Controller;

/**
 * Controller für die Anzeige der Baukostenübersicht
 * 
 * @author mvr
 *
 */
public class BaukostenController implements Controller
{
    private Node                 self;
    private RootLayoutController layoutController;

    @FXML
    private void handleClose()
    {
        this.layoutController.removeFromCenterAnimatedH(this.self);
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
    }

    @Override
    public void setLayoutController(RootLayoutController layoutController)
    {
        this.layoutController = layoutController;
    }

    @Override
    public void setNode(Node self)
    {
        this.self = self;
    }

}
