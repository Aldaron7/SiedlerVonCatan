package siedlervoncatan.io;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import siedlervoncatan.Spielstart;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Confirmation;
import siedlervoncatan.utility.Error;
import siedlervoncatan.utility.Handel;
import siedlervoncatan.utility.Info;
import siedlervoncatan.utility.Pfade;
import siedlervoncatan.utility.Rohstoffauswahl;
import siedlervoncatan.view.ViewController;
import siedlervoncatan.view.controller.AvatarController;
import siedlervoncatan.view.controller.HauptmenueController;
import siedlervoncatan.view.controller.KartenAbgebenMenueController;
import siedlervoncatan.view.controller.RootLayoutController;
import siedlervoncatan.view.controller.SpielerHandelAuswahlController;
import siedlervoncatan.view.controller.SpielerInfosController;
import siedlervoncatan.view.controller.SpielfeldController;

public class Menuefx implements UserInterface
{
    private Spielstart           spielstart;
    private ViewController       viewController;
    private RootLayoutController layoutController;

    @Override
    public void setSpielstart(Spielstart spielstart)
    {
        this.spielstart = spielstart;
        this.viewController = new ViewController(spielstart.getSpiel(), spielstart.getLayoutController());
        this.layoutController = spielstart.getLayoutController();
    }

    /**
     * Erzeugt das Hauptmenue im Zentrum des Rootlayouts Menue: Neu, Laden, Beenden.
     */
    @Override
    public void zeigeHauptmenue()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.HAUPT_MENUE);

            this.layoutController.addToCenterAnimatedV(pane);
            HauptmenueController controller = this.viewController.getLoader().getController();
            controller.setSpielstart(this.spielstart);
            Sound.getInstanz().playMusik(Sound.MUSIK_MENUE);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void zeigeAudiomenue()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.AUDIO_MENUE);
            this.layoutController.addToCenterAnimatedH(pane);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt das Spielfeld im Zentrum des RootLayouts. Übergibt den SpielfeldController an Spielstart.
     */
    @Override
    public void zeigeSpielfeld()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.SPIELFELD);
            this.layoutController.addToCenter(pane);
            SpielfeldController controller = this.viewController.getLoader().getController();
            this.spielstart.setSpielfeldController(controller);
            Sound.getInstanz().playMusik(Sound.MUSIK_MEER);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt das NeueSpielMenue rechts im RootLayout Menü: Neuer Spieler, Spielen.
     */
    @Override
    public void zeigeNeuesspielMenue()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.NEUES_SPIEL_MENUE);
            StackPane.setAlignment(pane, Pos.CENTER_RIGHT);
            this.layoutController.addToCenterAnimatedV(pane);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt das SpielerAnlegenMenü in einem Popup Fenster. Menü: Name, Farbe, Ok, Abbrechen.
     */
    @Override
    public void spielerAnlegen()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.SPIELER_ANLEGEN);
            this.layoutController.addToCenterAnimatedH(pane);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt das WürfelMenü rechts im RootLayout. Menü: Entwicklungen, List<Karten>, Würfeln.
     */
    @Override
    public void zeigeWuerfel()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.WUERFEL_MENUE);
            StackPane.setAlignment(pane, Pos.CENTER_RIGHT);
            this.layoutController.addToCenterAnimatedV(pane);
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
    @Override
    public void zeigeZug()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.ZUG_MENUE);
            StackPane.setAlignment(pane, Pos.CENTER_RIGHT);
            this.layoutController.addToCenterAnimatedV(pane);
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
    @Override
    public void zeigeEntwicklungskarten()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.ENTWICKLUNGSKARTEN);
            this.layoutController.addToCenterAnimatedH(pane);
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
    @Override
    public void zeigeBau()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.BAU_MENUE);
            StackPane.setAlignment(pane, Pos.CENTER_RIGHT);
            this.layoutController.addToCenterAnimatedV(pane);
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
    @Override
    public void zeigeSpielInfos()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.SPIEL_INFOS);
            StackPane.setAlignment(pane, Pos.TOP_LEFT);
            this.layoutController.addToCenter(pane);
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
    @Override
    public void zeigeHandel()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.HANDEL_MENUE);
            this.layoutController.addToCenterAnimatedH(pane);
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
    @Override
    public void zeigeSpielerHandel(Handel handel)
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.SPIELER_HANDEL_AUSWAHL);
            ((SpielerHandelAuswahlController) this.viewController.getLoader().getController()).setAngebotNachfrage(handel);
            this.layoutController.addToCenterAnimatedH(pane);
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
    @Override
    public void zeigeKartenAbgeben(Spieler spieler, int anzahl)
    {

        try
        {
            Pane pane = this.viewController.initPane(Pfade.KARTEN_ABGEBEN_MENUE);
            KartenAbgebenMenueController controller = this.viewController.getLoader().getController();
            controller.setAnzahl(anzahl);
            controller.setSpieler(spieler);
            this.layoutController.addToCenterAnimatedH(pane);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Erzeugt eine Glückwünsch Nachricht mit dem Sieger in einem Popup Fenster.
     */
    @Override
    public void zeigeSieger()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.SIEGER);
            Sound.getInstanz().playSoundeffekt(Sound.SIEGER_CLIP);
            this.layoutController.addToCenterAnimatedH(pane);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean zeigeConfirmation(String text)
    {
        Confirmation confirmation = new Confirmation();
        confirmation.setText(text);
        boolean response = confirmation.showAndWait();
        return response;
    }

    @Override
    public Pane zeigeSpielerInfos(Spieler spieler)
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.SPIELERINFOS);
            FXMLLoader loader = this.viewController.getLoader();
            SpielerInfosController controller = loader.getController();
            controller.setSpieler(spieler);
            this.layoutController.addToCenterAnimatedH(pane);
            return pane;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Pane zeigeAvatar(Spieler spieler)
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.AVATAR);
            AvatarController controller = this.viewController.getLoader().getController();
            controller.setSpieler(spieler);
            return pane;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void removeFromCenterAnimatedH(Node node)
    {
        this.layoutController.removeFromCenterAnimatedH(node);
    }

    @Override
    public Rohstoff zeigeRohstoffauswahl(String text)
    {
        Rohstoffauswahl rohstoffauswahl = new Rohstoffauswahl(text);
        Rohstoff rohstoff = rohstoffauswahl.showAndWait();
        return rohstoff;
    }

    @Override
    public void zeigeInfo(String text)
    {
        new Info(text).showAndWait();
    }

    @Override
    public void zeigeError(String text)
    {
        new Error(text).showAndWait();
    }

}
