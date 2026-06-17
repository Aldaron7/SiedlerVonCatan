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

    /**
     * Lädt die UserSettings bezüglich Audio und Window settings.
     */
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
                boolean musikAn = Boolean.parseBoolean(properties.getProperty("musikAn"));
                sound.setMusikAn(musikAn);
                double musikVolume = Double.parseDouble(properties.getProperty("musikVolume"));
                sound.changeMusikVolume(musikVolume);
                boolean soundeffekteAn = Boolean.parseBoolean(properties.getProperty("soundeffekteAn"));
                sound.setSoundeffekteAn(soundeffekteAn);
                double soundeffekteVolume = Double.parseDouble(properties.getProperty("soundeffekteVolume"));
                sound.changeSoundeffekteVolume(soundeffekteVolume);
                Spielstart.PRIMARYSTAGE.setMaximized(Boolean.parseBoolean(properties.getProperty("maximized")));
                is.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Speichert die UserSettings bezüglich Audio und Window settings.
     */
    private void saveProperties()
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
        // Workaround for missing libXxf86vm.so.1 on some Linux systems
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            System.setProperty("prism.order", "sw");
        }
        try {
            Application.launch(args);
        } catch (Throwable t) {
            if (t.getMessage() != null && (t.getMessage().contains("glassgtk3") || t.getMessage().contains("libgthread")) ||
                (t.getCause() != null && t.getCause().getMessage() != null && (t.getCause().getMessage().contains("glassgtk3") || t.getCause().getMessage().contains("libgthread")))) {
                System.err.println("---------------------------------------------------------");
                System.err.println("ERROR: JavaFX GTK3 libraries could not be loaded.");
                System.err.println("If you are on NixOS, please run this application using:");
                System.err.println("nix-shell shell.nix --run 'mvn javafx:run'");
                System.err.println("---------------------------------------------------------");
            }
            throw t;
        }
    }

    /**
     * Erzeugt das Rootlayout und zeigt die PRIMARYSTAGE.
     */
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

    /**
     * Speichert den Spielstand in der Datei file.
     * 
     * @param file
     */
    public void spielSpeichern(File file)
    {
        if (this.spiel != null)
        {
            this.spiel.speichern(file);
        }
    }

    /**
     * Legt ein neues Spiel an und startet es.
     */
    public void neuesSpiel()
    {
        this.spiel = new Spiel();
        this.spiel.setSpielstart(this);
        this.ui.zeigeSpielfeld();
        this.spiel.starten();
    }

    /**
     * Beendet nach einer Confirmation die Applikation.
     */
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
