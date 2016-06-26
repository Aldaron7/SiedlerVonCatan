package siedlervoncatan.view.controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import siedlervoncatan.Spielstart;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.view.Controller;

public class HauptmenueController implements Controller
{
    private Spielstart spielstart;
    private Sound      sound;

    public void setSpielstart(Spielstart spielstart)
    {
        this.spielstart = spielstart;
        this.sound = spielstart.getSound();
    }

    @FXML
    private void handleNeu()
    {
        this.sound.playSoundeffekt(Sound.BUTTON_CLIP);
        this.spielstart.neuesSpiel();
    }

    @FXML
    private void handleLaden()
    {
        this.sound.playSoundeffekt(Sound.BUTTON_CLIP);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("SVC files", "*svc");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setInitialDirectory(new File("saves"));
        File file = fileChooser.showOpenDialog(this.spielstart.getPrimaryStage());
        if (file != null)
        {
            this.spielstart.spielLaden(file);
        }
    }

    @FXML
    private void handleBeenden()
    {
        this.sound.playSoundeffekt(Sound.BUTTON_CLIP);
        System.exit(0);
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
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
