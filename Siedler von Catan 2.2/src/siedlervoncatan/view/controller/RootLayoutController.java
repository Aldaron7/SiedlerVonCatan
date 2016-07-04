package siedlervoncatan.view.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import siedlervoncatan.Spielstart;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.view.Controller;

public class RootLayoutController implements Controller
{
    @FXML
    private StackPane       centerSP;

    private Spielstart      spielstart;
    private ScaleTransition appearH;
    private ScaleTransition disappearH;
    private ScaleTransition appearV;
    private ScaleTransition disappearV;
    private Sound           sound;

    public void setSpielstart(Spielstart spielstart)
    {
        this.spielstart = spielstart;
        this.appearH = new ScaleTransition(Duration.millis(500));
        this.appearH.setFromX(0);
        this.appearH.setToX(1);
        this.disappearH = new ScaleTransition(Duration.millis(500));
        this.disappearH.setFromX(1);
        this.disappearH.setToX(0);
        this.appearV = new ScaleTransition(Duration.millis(500));
        this.appearV.setFromY(0);
        this.appearV.setToY(1);
        this.disappearV = new ScaleTransition(Duration.millis(500));
        this.disappearV.setFromY(1);
        this.disappearV.setToY(0);
        this.sound = Sound.getInstanz();
    }

    public void addToCenter(Node node)
    {
        if (!this.centerSP.getChildren().contains(node))
        {
            this.centerSP.getChildren().add(node);
        }
    }

    public void addToCenterAnimatedH(Node node)
    {
        this.addToCenter(node);
        this.appearH.setNode(node);
        this.appearH.play();
        this.sound.playSoundeffekt(Sound.PAPER);
    }

    public void addToCenterAnimatedV(Node node)
    {
        this.addToCenter(node);
        this.appearV.setNode(node);
        this.appearV.play();
        this.sound.playSoundeffekt(Sound.PAPER);
    }

    public void removeFromCenter(Node node)
    {
        this.centerSP.getChildren().remove(node);
    }

    public void removeFromCenterAnimatedH(Node node)
    {
        this.disappearH.setNode(node);
        this.disappearH.play();
        this.sound.playSoundeffekt(Sound.PAPER);
        this.disappearH.setOnFinished(e -> this.removeFromCenter(node));
    }

    public void removeFromCenterAnimatedV(Node node)
    {
        this.disappearV.setNode(node);
        this.disappearV.play();
        this.sound.playSoundeffekt(Sound.PAPER);
        this.disappearV.setOnFinished(e -> this.removeFromCenter(node));
    }

    @FXML
    private void handleLaden()
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("SVC files", "*svc");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setInitialDirectory(new File("saves"));
        File file = fileChooser.showOpenDialog(Spielstart.getPrimaryStage());
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
            File file = fileChooser.showSaveDialog(Spielstart.getPrimaryStage());
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
            this.spielstart.getUserInterface().zeigeError("Spielstand kann jetzt nicht gespeichert werden.");
        }
    }

    @FXML
    private void handleBeenden()
    {
        this.spielstart.beenden();
    }

    @FXML
    private void handleAudio()
    {
        this.spielstart.getUserInterface().zeigeAudiomenue();
    }

    @FXML
    private void handleVollbild()
    {
        Stage primaryStage = Spielstart.getPrimaryStage();
        primaryStage.setFullScreen(!primaryStage.isFullScreen());
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
            this.spielstart.getUserInterface().zeigeError("Die Anleitung konnte nicht geöffnet werden.");
            e.printStackTrace();
        }
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
