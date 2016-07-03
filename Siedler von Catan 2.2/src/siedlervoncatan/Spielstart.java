package siedlervoncatan;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import siedlervoncatan.io.Menuefx;
import siedlervoncatan.io.UserInterface;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.utility.Error;
import siedlervoncatan.utility.Pfade;
import siedlervoncatan.view.controller.RootLayoutController;
import siedlervoncatan.view.controller.SpielfeldController;

public class Spielstart extends Application
{
    private static Stage         PRIMARYSTAGE;
    private BorderPane           rootLayout;
    private RootLayoutController layoutController;
    private Spiel                spiel;
    private SpielfeldController  spielfeldController;
    private UserInterface        menue;

    @Override
    public void start(Stage primaryStage)
    {
        Spielstart.PRIMARYSTAGE = primaryStage;
        primaryStage.setTitle("Siedler von Catan");
        primaryStage.getIcons().add(new Image("file:bilder/logo.png"));
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setMinHeight(730);
        primaryStage.setMinWidth(920);

        this.initRootLayout();
        this.menue = new Menuefx();
        this.menue.setSpielstart(this);
        this.menue.zeigeHauptmenue();
    }

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    private void initRootLayout()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource(Pfade.ROOTLAYOUT));
            this.rootLayout = loader.load();
            Scene scene = new Scene(this.rootLayout);
            Spielstart.PRIMARYSTAGE.setScene(scene);

            this.layoutController = loader.getController();
            this.layoutController.setSpielstart(this);

            Spielstart.PRIMARYSTAGE.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Lädt einen Spielstand aus file. Führt die postLoad Methoden aus.
     * 
     * @param file
     */
    public void spielLaden(File file)
    {

        try
        {
            Path path = file.toPath();
            InputStream is = Files.newInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is));
            this.spiel = (Spiel) ois.readObject();
            ois.close();
            is.close();
            this.spiel.postLoad();
            this.spiel.setSpielstart(this);
            this.menue.zeigeSpielfeld();
            this.menue.zeigeSpielInfos();
            this.spiel.weiterspielen();
        }
        catch (Exception e)
        {
            new Error("Spielstand konnte nicht geladen werden aus der Datei:\n" + file.getPath());
            e.printStackTrace();
        }
    }

    public void spielSpeichern(File file)
    {
        if (this.spiel != null)
        {
            this.spiel.speichern(file);
        }
    }

    public void neuesSpiel()
    {
        this.spiel = new Spiel();
        this.spiel.setSpielstart(this);
        this.menue.zeigeSpielfeld();
        this.spiel.starten();
    }

    public void beenden()
    {
        boolean antwort = this.menue.zeigeConfirmation("Möchten Sie das Spiel wirklich beenden?");
        if (antwort)
        {
            System.exit(0);
        }
    }

    public static Stage getPrimaryStage()
    {
        return Spielstart.PRIMARYSTAGE;
    }

    public BorderPane getRootLayout()
    {
        return this.rootLayout;
    }

    public SpielfeldController getSpielfeldController()
    {
        return this.spielfeldController;
    }

    public void setSpielfeldController(SpielfeldController spielfeldController)
    {
        this.spielfeldController = spielfeldController;
    }

    public Spiel getSpiel()
    {
        return this.spiel;
    }

    public UserInterface getUserInterface()
    {
        return this.menue;
    }

    public RootLayoutController getLayoutController()
    {
        return this.layoutController;
    }

}
