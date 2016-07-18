package siedlervoncatan;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import siedlervoncatan.io.Menuefx;
import siedlervoncatan.io.UserInterface;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.utility.Pfade;
import siedlervoncatan.view.controller.RootLayoutController;

public class Spielstart extends Application
{
    private static Stage         PRIMARYSTAGE;
    private BorderPane           rootLayout;
    private RootLayoutController layoutController;
    private Spiel                spiel;
    private UserInterface        ui;

    @Override
    public void start(Stage primaryStage)
    {
        Spielstart.PRIMARYSTAGE = primaryStage;
        primaryStage.setTitle("Siedler von Catan");
        primaryStage.getIcons().add(new Image("file:bilder/logo.png"));
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setMinHeight(730);
        primaryStage.setMinWidth(920);

        this.loadProperties();
        this.initRootLayout();
        this.ui = new Menuefx();
        this.ui.setSpielstart(this);
        this.ui.zeigeHauptmenue();
    }

    private void loadProperties()
    {
        Properties properties = new Properties();
        try
        {
            File file = new File(Pfade.PROPERTIES);
            Path path = file.toPath();
            if (file.exists())
            {
                InputStream is = Files.newInputStream(path);
                properties.load(is);

                Sound sound = Sound.getInstanz();
                Boolean musikAn = new Boolean(properties.getProperty("musikAn"));
                sound.setMusikAn(musikAn);
                Double musikVolume = new Double(properties.getProperty("musikVolume"));
                sound.changeMusikVolume(musikVolume);
                Boolean soundeffekteAn = new Boolean(properties.getProperty("soundeffekteAn"));
                sound.setSoundeffekteAn(soundeffekteAn);
                Double soundeffekteVolume = new Double(properties.getProperty("soundeffekteVolume"));
                sound.changeSoundeffekteVolume(soundeffekteVolume);
                Spielstart.PRIMARYSTAGE.setMaximized(new Boolean(properties.getProperty("maximized")));
                is.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void saveProperties()
    {
        try
        {
            Sound sound = Sound.getInstanz();
            Properties properties = new Properties();
            File file = new File(Pfade.PROPERTIES);
            Path path = file.toPath();
            if (!file.exists())
            {
                path = Files.createFile(path);
            }
            OutputStream os = Files.newOutputStream(path);
            properties.setProperty("musikAn", String.valueOf(sound.getMusikAn()));
            properties.setProperty("musikVolume", String.valueOf(sound.getMusikVolume()));
            properties.setProperty("soundeffekteAn", String.valueOf(sound.getSoundeffekteAn()));
            properties.setProperty("soundeffekteVolume", String.valueOf(sound.getSoundeffekteVolume()));
            properties.setProperty("maximized", String.valueOf(Spielstart.PRIMARYSTAGE.isMaximized()));

            properties.store(os, "");
            os.flush();
            os.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
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
            this.ui.zeigeSpielfeld();
            this.ui.zeigeSpielInfos();
            this.spiel.weiterspielen();
        }
        catch (Exception e)
        {
            this.ui.zeigeError("Spielstand konnte nicht geladen werden aus der Datei:\n" + file.getPath());
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
        this.ui.zeigeSpielfeld();
        this.spiel.starten();
    }

    public void beenden()
    {
        boolean antwort = this.ui.zeigeConfirmation("Möchten Sie das Spiel wirklich beenden?");
        if (antwort)
        {
            this.saveProperties();
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

    public Spiel getSpiel()
    {
        return this.spiel;
    }

    public UserInterface getUserInterface()
    {
        return this.ui;
    }

    public RootLayoutController getLayoutController()
    {
        return this.layoutController;
    }

}
