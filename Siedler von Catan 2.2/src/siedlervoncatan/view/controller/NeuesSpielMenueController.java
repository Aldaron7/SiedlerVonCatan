package siedlervoncatan.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Error;
import siedlervoncatan.view.Controller;

public class NeuesSpielMenueController implements Controller
{
    @FXML
    private ListView<Spieler> alleSpieler;

    private Spiel             spiel;

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        this.alleSpieler.setItems(spiel.getAlleSpieler());
    }

    @FXML
    private void handleSpielerHinzufuegen()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        if (this.spiel.getAlleSpieler().size() >= 4)
        {
            new Error("Die maximale Spieleranzahl ist bereits erreicht.");
        }
        else
        {
            this.spiel.spielerAnlegen();
        }
    }

    @FXML
    private void handleSpielStarten()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        if (this.spiel.getAlleSpieler().size() <= 1)
        {
            new Error("Zu wenige Spieler.");
        }
        else
        {
            this.spiel.getSpielstart().getRootLayout().setRight(null);
            this.spiel.spielen();
        }
    }

    @Override
    public void setLayoutController(RootLayoutController layoutController)
    {
    }

    @Override
    public void setNode(Node self)
    {
    }

}
