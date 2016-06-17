package siedlervoncatan.view.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import siedlervoncatan.Spielstart;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.utility.Error;
import siedlervoncatan.view.Controller;

public class RootLayoutController implements Controller
{
    private Spielstart spielstart;

    public void setSpielstart(Spielstart spielstart)
    {
        this.spielstart = spielstart;
    }

    @FXML
    private void handleLaden()
    {
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
    private void handleNeu()
    {
        this.spielstart.neuesSpiel();
    }

    @FXML
    private void handleSpeichern()
    {
        if (this.spielstart.getSpiel().isSaveable())
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("SVC files (*.svc)", "*.svc");
            fileChooser.getExtensionFilters().add(extensionFilter);
            fileChooser.setInitialDirectory(new File("saves"));
            File file = fileChooser.showSaveDialog(this.spielstart.getPrimaryStage());
            if (file != null)
            {
                if (!file.getPath().endsWith(".svc"))
                {
                    file = new File(file.getPath() + ".svc");
                }
                this.spielstart.spielSpeichern(file);
            }
        }
        else
        {
            new Error("Spielstand kann jetzt nicht gespeichert werden.");
        }
    }

    @FXML
    private void handleBeenden()
    {
        this.spielstart.beenden();
    }

    @FXML
    private void handleAnleitung()
    {
        File file = new File("data/die_siedler_von_catan_jubilaeumsausgabe_almanach.pdf");
        try
        {
            Desktop.getDesktop().open(file);
        }
        catch (IOException e)
        {
            new Error("Die Anleitung konnte nicht geöffnet werden.");
            e.printStackTrace();
        }
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
    }

    @Override
    public void setStage(Stage stage)
    {
    }

}
