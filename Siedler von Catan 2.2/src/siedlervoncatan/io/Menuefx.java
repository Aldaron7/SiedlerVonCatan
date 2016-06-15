package siedlervoncatan.io;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import siedlervoncatan.Spielstart;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Handel;
import siedlervoncatan.view.BauMenueController;
import siedlervoncatan.view.EntwicklungskartenController;
import siedlervoncatan.view.HandelMenueController;
import siedlervoncatan.view.HauptmenueController;
import siedlervoncatan.view.KartenAbgebenMenueController;
import siedlervoncatan.view.NeuesSpielMenueController;
import siedlervoncatan.view.SiegerController;
import siedlervoncatan.view.SpielInfosController;
import siedlervoncatan.view.SpielerAnlegenController;
import siedlervoncatan.view.SpielerHandelAuswahlController;
import siedlervoncatan.view.SpielfeldController;
import siedlervoncatan.view.WuerfelMenueController;
import siedlervoncatan.view.ZugMenueController;

public class Menuefx
{
    private Spielstart spielstart;

    public void setSpielstart(Spielstart spielstart)
    {
        this.spielstart = spielstart;
    }

    /**
     * Erzeugt das Hauptmenue im Zentrum des Rootlayouts Menue: Neu, Laden, Beenden.
     */
    public void zeigeHauptmenue()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource("view/Hauptmenue.fxml"));
            AnchorPane menue = loader.load();
            this.spielstart.getRootLayout().setCenter(menue);
            this.spielstart.getRootLayout().setRight(null);
            HauptmenueController controller = loader.getController();
            controller.setSpielstart(this.spielstart);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt das Spielfeld im Zentrum des RootLayouts. Übergibt den SpielfeldController an Spielstart.
     */
    public void zeigeSpielfeld()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource("view/Spielfeld.fxml"));
            AnchorPane spielfeld = loader.load();
            this.spielstart.getRootLayout().setCenter(spielfeld);
            SpielfeldController controller = loader.getController();
            controller.setSpiel(this.spielstart.getSpiel());
            this.spielstart.setSpielfeldController(controller);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt das NeueSpielMenue rechts im RootLayout Menü: Neuer Spieler, Spielen.
     */
    public void zeigeNeuesspielMenue()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource("view/NeuesSpielMenue.fxml"));
            AnchorPane menue = loader.load();
            this.spielstart.getRootLayout().setRight(menue);

            NeuesSpielMenueController controller = loader.getController();
            controller.setSpiel(this.spielstart.getSpiel());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt das SpielerAnlegenMenü in einem Popup Fenster. Menü: Name, Farbe, Ok, Abbrechen.
     */
    public void spielerAnlegen()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource("view/SpielerAnlegen.fxml"));
            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setTitle("Neuer Spieler");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.spielstart.getPrimaryStage());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);

            SpielerAnlegenController controller = loader.getController();
            controller.setSpiel(this.spielstart.getSpiel());
            controller.setStage(stage);
            stage.showAndWait();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt das WürfelMenü rechts im RootLayout. Menü: Entwicklungen, List<Karten>, Würfeln.
     */
    public void zeigeWuerfel()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource("view/WuerfelMenue.fxml"));
            AnchorPane menue = loader.load();
            this.spielstart.getRootLayout().setRight(menue);

            WuerfelMenueController controller = loader.getController();
            controller.setSpiel(this.spielstart.getSpiel());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt das ZugMenue rechts im RootLayout. Menü: Entwicklungen, Bauen/Kaufen, Seehandel, Handel Spieler, List
     * <Karten>, Ende.
     */
    public void zeigeZug()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource("view/ZugMenue.fxml"));
            AnchorPane menue = loader.load();
            this.spielstart.getRootLayout().setRight(menue);

            ZugMenueController controller = loader.getController();
            controller.setSpiel(this.spielstart.getSpiel());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Erzeugt das EntwicklungskartenMenü in einem Popup Fenster. Zeigt die Entwicklungskarten mit Text und lässt sie
     * ausspielen.
     */
    public void zeigeEntwicklungskarten()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource("view/Entwicklungskarten.fxml"));
            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setTitle("Entwicklungskarten");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.spielstart.getPrimaryStage());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);

            EntwicklungskartenController controller = loader.getController();
            controller.setSpiel(this.spielstart.getSpiel());
            controller.setStage(stage);
            stage.showAndWait();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt das Baumenü rechts im RootLayout. Menü: Strasse, Siedlung, Stadt, Entwicklung, Abbrechen. Wird disabled
     * wenn gebaut wird.
     */
    public void zeigeBau()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource("view/BauMenue.fxml"));
            AnchorPane menue = loader.load();
            this.spielstart.getRootLayout().setRight(menue);

            BauMenueController controller = loader.getController();
            controller.setSpiel(this.spielstart.getSpiel());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt die SpielInfo Anzeige links im RootLayout. Zeigt Spieler, Siegpunkte, Anzahl Karten, Anzahl ausgespielter
     * Ritter.
     */
    public void zeigeSpielInfos()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource("view/SpielInfos.fxml"));
            AnchorPane menue = loader.load();
            this.spielstart.getRootLayout().setLeft(menue);

            SpielInfosController controller = loader.getController();
            controller.setSpiel(this.spielstart.getSpiel());

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt das HandelMenü in einem Popup Fenster. Auswahl des Angebots und der Nachfrage, die in einem HandelObjekt
     * gespeichert werden.
     */
    public void zeigeHandel()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource("view/HandelMenue.fxml"));
            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setTitle("Handeln");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.spielstart.getPrimaryStage());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);

            HandelMenueController controller = loader.getController();
            controller.setStage(stage);
            controller.setSpiel(this.spielstart.getSpiel());
            stage.showAndWait();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt das SpielerHandelMenü in einem Popup Fenster. Zeigt Angebot und Nachfrage aus dem übergebenen
     * HandelObjekt und die Auswahl des Handelspartners aus den restlichen Mitspielern. Speichert alles im handelObjekt.
     * 
     * @param handel
     */
    public void zeigeSpielerHandel(Handel handel)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource("view/SpielerHandelAuswahl.fxml"));
            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setTitle("Wähle Spieler zum Handeln.");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.spielstart.getPrimaryStage());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);

            SpielerHandelAuswahlController controller = loader.getController();
            controller.setSpiel(this.spielstart.getSpiel());
            controller.setAngebotNachfrage(handel);
            controller.setStage(stage);
            stage.showAndWait();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Erzeugt das KarteAbgebenMenü in einem Popup Fenster. Zeigt alle Karten des Spielers und entfernt die ausgewählte
     * Karte aus den Handkarten.
     * 
     * @param spieler
     */
    public void zeigeKartenAbgeben(Spieler spieler, int anzahl)
    {

        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource("view/KartenAbgebenMenue.fxml"));
            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setTitle("Rohstoffauswahl");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.spielstart.getPrimaryStage());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);

            KartenAbgebenMenueController controller = loader.getController();
            controller.setAnzahl(anzahl);
            controller.setSpieler(spieler);
            controller.setStage(stage);
            stage.showAndWait();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Erzeugt eine Glückwünsch Nachricht mit dem Sieger in einem Popup Fenster.
     */
    public void zeigeSieger()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Spielstart.class.getResource("view/Sieger.fxml"));
            AnchorPane pane = loader.load();

            Scene scene = new Scene(pane);
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("Sieger");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.spielstart.getPrimaryStage());
            stage.setScene(scene);

            SiegerController controller = loader.getController();
            controller.setSpiel(this.spielstart.getSpiel());
            controller.setStage(stage);
            stage.showAndWait();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
