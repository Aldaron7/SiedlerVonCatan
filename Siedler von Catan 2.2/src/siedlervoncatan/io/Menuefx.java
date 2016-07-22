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
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Handel;
import siedlervoncatan.utility.Pfade;
import siedlervoncatan.utility.popup.Confirmation;
import siedlervoncatan.utility.popup.Error;
import siedlervoncatan.utility.popup.Info;
import siedlervoncatan.utility.popup.Rohstoffauswahl;
import siedlervoncatan.view.ViewController;
import siedlervoncatan.view.controller.AvatarController;
import siedlervoncatan.view.controller.HauptmenueController;
import siedlervoncatan.view.controller.KartenAbgebenMenueController;
import siedlervoncatan.view.controller.RootLayoutController;
import siedlervoncatan.view.controller.SpielerHandelAuswahlController;
import siedlervoncatan.view.controller.SpielerInfosController;
import siedlervoncatan.view.controller.SpielfeldController;

/**
 * Die Implementierung von @UserInterface in JavaFX.
 * 
 * @author mvr
 *
 */
public class Menuefx implements UserInterface
{
    private Spielstart           spielstart;
    private ViewController       viewController;
    private RootLayoutController layoutController;
    private SpielfeldController  spielfeldController;

    @Override
    public void setSpielstart(Spielstart spielstart)
    {
        this.spielstart = spielstart;
        this.layoutController = spielstart.getLayoutController();
        this.viewController = new ViewController(null, spielstart.getLayoutController());
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.viewController = new ViewController(spiel, this.layoutController);

    }

    /**
     * Erzeugt das Hauptmenue im Zentrum des Rootlayouts. Menü: Neu, Laden, Beenden.
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

    /**
     * Menü: Musik An/Aus, Musik Lautstärke, Soundeffekte An/Aus, Soundeffekte Lautstärke.
     */
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
     * Zeigt eine Liste der Baukosten.
     */
    @Override
    public void zeigeBaukosten()
    {
        try
        {
            Pane pane = this.viewController.initPane(Pfade.BAUKOSTEN);
            this.layoutController.addToCenterAnimatedH(pane);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt das Spielfeld im Zentrum des RootLayouts.
     */
    @Override
    public void zeigeSpielfeld()
    {
        this.layoutController.clearCenter();
        try
        {
            Pane pane = this.viewController.initPane(Pfade.SPIELFELD);
            this.layoutController.addToCenter(pane);
            SpielfeldController controller = this.viewController.getLoader().getController();
            this.spielfeldController = controller;
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
     * Erzeugt das SpielerAnlegenMenü im Zentrum des Rootlayout. Menü: Name, Farbe, Ok, Abbrechen.
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
     * Erzeugt das EntwicklungskartenMenü im Zentrum des Rootlayout. Zeigt die Entwicklungskarten mit Text und lässt sie
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
     * Erzeugt das Baumenü rechts im RootLayout. Menü: Strasse, Siedlung, Stadt, Entwicklung, Abbrechen.
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
     * Erzeugt das Layout in dem die SpielerAvatare angezeigt werden können oben links im Rootlayout.
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
     * Erzeugt das HandelMenü im Zentrum des Rootlayout. Auswahl des Angebots und der Nachfrage, die in einem
     * HandelObjekt gespeichert werden.
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
     * Erzeugt das SpielerHandelMenü im Zentrum des Rootlayout. Zeigt Angebot und Nachfrage aus dem übergebenen
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
     * Erzeugt das KarteAbgebenMenü im Zentrum des Rootlayout. Zeigt alle Karten des Spielers spieler und die Karten die
     * ausgewählt werden zum abgeben.
     * 
     * @param spieler
     *            der Karten abgeben muss
     * @param anzahl
     *            der abzugebnden Karten
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
     * Erzeugt eine Glückwünsch Nachricht mit dem Sieger im Zentrum des Rootlayout. Menü: Neues Spiel, Beenden.
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

    /**
     * Erzeugt die SpielInfo Anzeige für einen Spieler im Zentrum des RootLayout. Zeigt Spieler, Siegpunkte, Karten,
     * Anzahl ausgespielter Ritter, längste Handelsstrasse.
     */
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

    /**
     * Zeigt den Avatar des Spielers spieler in SpielInfo.
     * 
     * @param spieler
     */
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

    /**
     * Zeigt ein Popup Confirmation
     * 
     * @param text
     *            der angezeigt wird
     * @return true, wenn bestätigt wurde
     */
    @Override
    public boolean zeigeConfirmation(String text)
    {
        Confirmation confirmation = new Confirmation(text);
        boolean antwort = confirmation.showAndWait();
        return antwort;
    }

    /**
     * Zeigt ein Auswahl aller Rohstoffe.
     * 
     * @param text
     *            der angezeigt wird
     * @return den ausgewählten Rohstoff
     */
    @Override
    public Rohstoff zeigeRohstoffauswahl(String text)
    {
        Rohstoffauswahl rohstoffauswahl = new Rohstoffauswahl(text);
        Rohstoff rohstoff = rohstoffauswahl.showAndWait();
        return rohstoff;
    }

    /**
     * Zeigt ein Popup Info
     * 
     * @param text
     *            der angezeigt wird
     */
    @Override
    public void zeigeInfo(String text)
    {
        new Info(text).showAndWait();
    }

    /**
     * Zeigt ein Popup Error
     * 
     * @param text
     *            der angezeigt wird
     */
    @Override
    public void zeigeError(String text)
    {
        new Error(text).showAndWait();
    }

    @Override
    public SpielfeldController getSpielfeldController()
    {
        return this.spielfeldController;
    }

    /**
     * Zeigt die Nachricht auf dem Spielfeld.
     * 
     * @param message
     */
    @Override
    public void zeigeMessage(String message)
    {
        this.spielfeldController.setMessage(message);
    }

}
