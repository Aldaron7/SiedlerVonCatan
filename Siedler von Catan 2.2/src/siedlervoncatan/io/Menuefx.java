package siedlervoncatan.io;

import java.io.IOException;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import siedlervoncatan.Spielstart;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Handel;
import siedlervoncatan.utility.Pfade;
import siedlervoncatan.view.ViewController;
import siedlervoncatan.view.controller.HauptmenueController;
import siedlervoncatan.view.controller.KartenAbgebenMenueController;
import siedlervoncatan.view.controller.SpielerHandelAuswahlController;
import siedlervoncatan.view.controller.SpielfeldController;

public class Menuefx
{
    private Spielstart     spielstart;
    private ViewController viewController;

    public void setSpielstart(Spielstart spielstart)
    {
        this.spielstart = spielstart;
        this.viewController = new ViewController(spielstart.getPrimaryStage(), spielstart.getSpiel());
    }

    /**
     * Erzeugt das Hauptmenue im Zentrum des Rootlayouts Menue: Neu, Laden, Beenden.
     */
    public void zeigeHauptmenue()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.HAUPT_MENUE);

            this.spielstart.getRootLayout().setCenter(pane);
            this.spielstart.getRootLayout().setRight(null);
            HauptmenueController controller = this.viewController.getLoader().getController();
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
            Pane pane = this.viewController.initPane(Pfade.SPIELFELD);
            this.spielstart.getRootLayout().setCenter(pane);
            SpielfeldController controller = this.viewController.getLoader().getController();
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
            Pane pane = this.viewController.initPane(Pfade.NEUES_SPIEL_MENUE);
            this.spielstart.getRootLayout().setRight(pane);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void zeigeLeeresMenue()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.LEERES_MENUE);
            this.spielstart.getRootLayout().setRight(pane);
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
            Stage stage = this.viewController.createStage(Pfade.SPIELER_ANLEGEN, "Neuer Spieler");
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
            Pane pane = this.viewController.initPane(Pfade.WUERFEL_MENUE);
            this.spielstart.getRootLayout().setRight(pane);
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
            Pane pane = this.viewController.initPane(Pfade.ZUG_MENUE);
            this.spielstart.getRootLayout().setRight(pane);
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
            Stage stage = this.viewController.createStage(Pfade.ENTWICKLUNGSKARTEN, "Entwicklungskarten");
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
            Pane pane = this.viewController.initPane(Pfade.BAU_MENUE);
            this.spielstart.getRootLayout().setRight(pane);
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
            Pane pane = this.viewController.initPane(Pfade.SPIEL_INFOS);
            this.spielstart.getRootLayout().setLeft(pane);
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
            Stage stage = this.viewController.createStage(Pfade.HANDEL_MENUE, "Handeln");
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
            Stage stage = this.viewController.createStage(Pfade.SPIELER_HANDEL_AUSWAHL, "Wähle Spieler zum Handeln.");
            ((SpielerHandelAuswahlController) this.viewController.getLoader().getController()).setAngebotNachfrage(handel);
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
            Stage stage = this.viewController.createStage(Pfade.KARTEN_ABGEBEN_MENUE, "Karten Abgeben");

            KartenAbgebenMenueController controller = this.viewController.getLoader().getController();
            controller.setAnzahl(anzahl);
            controller.setSpieler(spieler);
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
            Stage stage = this.viewController.createStage(Pfade.SIEGER, "Sieger");
            stage.showAndWait();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
