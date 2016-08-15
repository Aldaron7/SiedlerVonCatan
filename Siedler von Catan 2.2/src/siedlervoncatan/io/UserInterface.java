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
     * Erzeugt das Hauptmenue im Zentrum des Rootlayouts. Menü: Neu, Laden, Beenden.
     */
    void zeigeHauptmenue();

    /**
     * Menü: Musik An/Aus, Musik Lautstärke, Soundeffekte An/Aus, Soundeffekte Lautstärke.
     * 
     */
    void zeigeAudiomenue();

    /**
     * Erzeugt das Spielfeld im Zentrum des RootLayouts.
     */
    void zeigeSpielfeld();

    /**
     * Erzeugt das NeueSpielMenue rechts im RootLayout Menü: Neuer Spieler, Spielen.
     */
    void zeigeNeuesspielMenue();

    /**
     * Erzeugt das SpielerAnlegenMenü im Zentrum des Rootlayout. Menü: Name, Farbe, Ok, Abbrechen.
     */
    void spielerAnlegen();

    /**
     * Erzeugt das WürfelMenü rechts im RootLayout. Menü: Entwicklungen, List<Karten>, Würfeln.
     */
    void zeigeWuerfel();

    /**
     * Erzeugt das ZugMenue rechts im RootLayout. Menü: Entwicklungen, Bauen/Kaufen, Seehandel, Handel Spieler, List
     * <Karten>, Ende.
     */
    void zeigeZug();

    /**
     * Erzeugt das EntwicklungskartenMenü im Zentrum des Rootlayout. Zeigt die Entwicklungskarten mit Text und lässt sie
     * ausspielen.
     */
    void zeigeEntwicklungskarten();

    /**
     * Erzeugt das Baumenü rechts im RootLayout. Menü: Strasse, Siedlung, Stadt, Entwicklung, Abbrechen.
     */
    void zeigeBau();

    /**
     * Erzeugt das Layout in dem die SpielerAvatare angezeigt werden können oben links im Rootlayout.
     */
    void zeigeSpielInfos();

    /**
     * Erzeugt das HandelMenü im Zentrum des Rootlayout. Auswahl des Angebots und der Nachfrage, die in einem
     * HandelObjekt gespeichert werden.
     */
    void zeigeHandel();

    /**
     * Erzeugt das SpielerHandelMenü im Zentrum des Rootlayout. Zeigt Angebot und Nachfrage aus dem übergebenen
     * HandelObjekt und die Auswahl des Handelspartners aus den restlichen Mitspielern. Speichert alles im handelObjekt.
     * 
     * @param handel
     */
    void zeigeSpielerHandel(Handel handel);

    /**
     * Erzeugt das KarteAbgebenMenü im Zentrum des Rootlayout. Zeigt alle Karten des Spielers spieler und die Karten die
     * ausgewählt werden zum abgeben.
     * 
     * @param spieler
     *            der Karten abgeben muss
     * @param anzahl
     *            der abzugebnden Karten
     */
    void zeigeKartenAbgeben(Spieler spieler, int anzahl);

    /**
     * Erzeugt eine Glückwünsch Nachricht mit dem Sieger im Zentrum des Rootlayout. Menü: Neues Spiel, Beenden.
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
     * @return den ausgewählten Rohstoff
     */
    Rohstoff zeigeRohstoffauswahl(String text);

    /**
     * Zeigt ein Popup Confirmation
     * 
     * @param text
     *            der angezeigt wird
     * @return true, wenn bestätigt wurde
     */
    boolean zeigeConfirmation(String text);

    /**
     * Erzeugt die SpielInfo Anzeige für einen Spieler im Zentrum des RootLayout. Zeigt Spieler, Siegpunkte, Karten,
     * Anzahl ausgespielter Ritter, längste Handelsstrasse.
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