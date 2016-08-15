package siedlervoncatan.view.controller;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.view.Controller;

/**
 * Controller für das Anzeigen des NeueSpiel Menüs
 * 
 * @author mvr
 *
 */
public class NeuesSpielMenueController implements Controller
{
    @FXML
    private ListView<Spieler>    alleSpieler;

    private Spiel                spiel;
    private Node                 self;
    private RootLayoutController layoutController;

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        this.alleSpieler.setItems(spiel.getAlleSpieler());
    }

    @FXML
    private void handleSpielerHinzufuegen()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        if (this.spiel.getAlleSpieler().size() >= 4)
        {
            this.spiel.getUserInterface().zeigeError("Die maximale Spieleranzahl ist bereits erreicht.");
        }
        else
        {
            this.spiel.spielerAnlegen();
        }
    }

    @FXML
    private void handleSpielStarten()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        if (this.spiel.getAlleSpieler().size() <= 1)
        {
            this.spiel.getUserInterface().zeigeError("Zu wenige Spieler.");
        }
        else
        {
            this.layoutController.removeFromCenterAnimatedV(this.self);
            Timer timer = new Timer(500, e -> Platform.runLater(() -> this.spiel.spielen()));
            timer.setRepeats(false);
            timer.start();
        }
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
