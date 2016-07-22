package siedlervoncatan.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.view.Controller;

/**
 * Controller für die Anzeige des Siegers
 * 
 * @author mvr
 *
 */
public class SiegerController implements Controller
{
    @FXML
    private Label                labelSpieler;

    private Spiel                spiel;
    private Node                 self;
    private RootLayoutController layoutController;

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        this.labelSpieler.setText(spiel.getSieger().getName());
    }

    @FXML
    private void handleNeu()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        this.layoutController.removeFromCenterAnimatedH(this.self);
        this.spiel.getSpielstart().neuesSpiel();
    }

    @FXML
    private void handleEnde()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        System.exit(0);
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
