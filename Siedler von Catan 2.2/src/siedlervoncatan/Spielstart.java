package siedlervoncatan;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import siedlervoncatan.io.Menuefx;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.utility.Error;
import siedlervoncatan.view.RootLayoutController;
import siedlervoncatan.view.SpielfeldController;

public class Spielstart extends Application
{
    private Stage               primaryStage;
    private BorderPane          rootLayout;
    private Spiel               spiel;
    private SpielfeldController spielfeldController;
    private Menuefx             menue;

    // test für github
    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Siedler von Catan");
        this.primaryStage.getIcons().add(new Image("file:bilder/logo.png"));
        this.primaryStage.initStyle(StageStyle.UNIFIED);
        this.primaryStage.setMinHeight(750);
        this.primaryStage.setMinWidth(920);

        this.menue = new Menuefx();
        this.menue.setSpielstart(this);
        this.initRootLayout();
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
            loader.setLocation(Spielstart.class.getResource("view/RootLayout.fxml"));
            this.rootLayout = loader.load();
            Scene scene = new Scene(this.rootLayout);
            this.primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setSpielstart(this);

            this.primaryStage.show();
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
            this.spiel.getMenue().zeigeSpielfeld();
            this.spiel.getMenue().zeigeSpielInfos();
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
        this.spiel.getMenue().zeigeSpielfeld();
        this.spiel.getMenue().zeigeSpielInfos();
        this.spiel.starten();
    }

    public void beenden()
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.getDialogPane().getScene().getStylesheets().add("siedlervoncatan/view/stylesheet.css");
        alert.setTitle("Spiel beenden?");
        alert.setContentText("Möchten Sie das Spiel ohne zu speichern beenden?");
        alert.initStyle(StageStyle.UNDECORATED);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            System.exit(0);
        }
    }

    public Stage getPrimaryStage()
    {
        return this.primaryStage;
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

}
