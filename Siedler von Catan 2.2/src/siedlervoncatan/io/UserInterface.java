package siedlervoncatan.io;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import siedlervoncatan.Spielstart;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Handel;
import siedlervoncatan.view.controller.SpielfeldController;

public interface UserInterface
{

    void setSpielstart(Spielstart spielstart);

    void setSpiel(Spiel spiel);

    /**
     * Erzeugt das Hauptmenue im Zentrum des Rootlayouts. Men�: Neu, Laden, Beenden.
     */
    void zeigeHauptmenue();

    /**
     * Men�: Musik An/Aus, Musik Lautst�rke, Soundeffekte An/Aus, Soundeffekte Lautst�rke.
     * 
     */
    void zeigeAudiomenue();

    /**
     * Erzeugt das Spielfeld im Zentrum des RootLayouts.
     */
    void zeigeSpielfeld();

    /**
     * Erzeugt das NeueSpielMenue rechts im RootLayout Men�: Neuer Spieler, Spielen.
     */
    void zeigeNeuesspielMenue();

    /**
     * Erzeugt das SpielerAnlegenMen� im Zentrum des Rootlayout. Men�: Name, Farbe, Ok, Abbrechen.
     */
    void spielerAnlegen();

    /**
     * Erzeugt das W�rfelMen� rechts im RootLayout. Men�: Entwicklungen, List<Karten>, W�rfeln.
     */
    void zeigeWuerfel();

    /**
     * Erzeugt das ZugMenue rechts im RootLayout. Men�: Entwicklungen, Bauen/Kaufen, Seehandel, Handel Spieler, List
     * <Karten>, Ende.
     */
    void zeigeZug();

    /**
     * Erzeugt das EntwicklungskartenMen� im Zentrum des Rootlayout. Zeigt die Entwicklungskarten mit Text und l�sst sie
     * ausspielen.
     */
    void zeigeEntwicklungskarten();

    /**
     * Erzeugt das Baumen� rechts im RootLayout. Men�: Strasse, Siedlung, Stadt, Entwicklung, Abbrechen.
     */
    void zeigeBau();

    /**
     * Erzeugt das Layout in dem die SpielerAvatare angezeigt werden k�nnen oben links im Rootlayout.
     */
    void zeigeSpielInfos();

    /**
     * Erzeugt das HandelMen� im Zentrum des Rootlayout. Auswahl des Angebots und der Nachfrage, die in einem
     * HandelObjekt gespeichert werden.
     */
    void zeigeHandel();

    /**
     * Erzeugt das SpielerHandelMen� im Zentrum des Rootlayout. Zeigt Angebot und Nachfrage aus dem �bergebenen
     * HandelObjekt und die Auswahl des Handelspartners aus den restlichen Mitspielern. Speichert alles im handelObjekt.
     * 
     * @param handel
     */
    void zeigeSpielerHandel(Handel handel);

    /**
     * Erzeugt das KarteAbgebenMen� im Zentrum des Rootlayout. Zeigt alle Karten des Spielers spieler und die Karten die
     * ausgew�hlt werden zum abgeben.
     * 
     * @param spieler
     *            der Karten abgeben muss
     * @param anzahl
     *            der abzugebnden Karten
     */
    void zeigeKartenAbgeben(Spieler spieler, int anzahl);

    /**
     * Erzeugt eine Gl�ckw�nsch Nachricht mit dem Sieger im Zentrum des Rootlayout. Men�: Neues Spiel, Beenden.
     */
    void zeigeSieger();

    /**
     * Zeigt ein Popup Info
     * 
     * @param text
     *            der angezeigt wird
     */
    void zeigeInfo(String text);

    /**
     * Zeigt ein Popup Error
     * 
     * @param text
     *            der angezeigt wird
     */
    void zeigeError(String text);

    /**
     * Zeigt ein Auswahl aller Rohstoffe.
     * 
     * @param text
     *            der angezeigt wird
     * @return den ausgew�hlten Rohstoff
     */
    Rohstoff zeigeRohstoffauswahl(String text);

    /**
     * Zeigt ein Popup Confirmation
     * 
     * @param text
     *            der angezeigt wird
     * @return true, wenn best�tigt wurde
     */
    boolean zeigeConfirmation(String text);

    /**
     * Erzeugt die SpielInfo Anzeige f�r einen Spieler im Zentrum des RootLayout. Zeigt Spieler, Siegpunkte, Karten,
     * Anzahl ausgespielter Ritter, l�ngste Handelsstrasse.
     */
    Pane zeigeSpielerInfos(Spieler spieler);

    /**
     * Zeigt den Avatar des Spielers spieler in SpielInfo.
     * 
     * @param spieler
     */
    Pane zeigeAvatar(Spieler spieler);

    void removeFromCenterAnimatedH(Node node);

    SpielfeldController getSpielfeldController();

    /**
     * Zeigt die Nachricht auf dem Spielfeld.
     * 
     * @param message
     */
    void zeigeMessage(String message);

    /**
     * Zeigt eine Liste der Baukosten.
     */
    void zeigeBaukosten();

}