package siedlervoncatan.io;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import siedlervoncatan.Spielstart;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Handel;

public interface UserInterface
{

    void setSpielstart(Spielstart spielstart);

    /**
     * Erzeugt das Hauptmenue im Zentrum des Rootlayouts Menue: Neu, Laden, Beenden.
     */
    void zeigeHauptmenue();

    void zeigeAudiomenue();

    /**
     * Erzeugt das Spielfeld im Zentrum des RootLayouts. Übergibt den SpielfeldController an Spielstart.
     */
    void zeigeSpielfeld();

    /**
     * Erzeugt das NeueSpielMenue rechts im RootLayout Menü: Neuer Spieler, Spielen.
     */
    void zeigeNeuesspielMenue();

    /**
     * Erzeugt das SpielerAnlegenMenü in einem Popup Fenster. Menü: Name, Farbe, Ok, Abbrechen.
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
     * Erzeugt das EntwicklungskartenMenü in einem Popup Fenster. Zeigt die Entwicklungskarten mit Text und lässt sie
     * ausspielen.
     */
    void zeigeEntwicklungskarten();

    /**
     * Erzeugt das Baumenü rechts im RootLayout. Menü: Strasse, Siedlung, Stadt, Entwicklung, Abbrechen. Wird disabled
     * wenn gebaut wird.
     */
    void zeigeBau();

    /**
     * Erzeugt die SpielInfo Anzeige links im RootLayout. Zeigt Spieler, Siegpunkte, Anzahl Karten, Anzahl ausgespielter
     * Ritter.
     */
    void zeigeSpielInfos();

    /**
     * Erzeugt das HandelMenü in einem Popup Fenster. Auswahl des Angebots und der Nachfrage, die in einem HandelObjekt
     * gespeichert werden.
     */
    void zeigeHandel();

    /**
     * Erzeugt das SpielerHandelMenü in einem Popup Fenster. Zeigt Angebot und Nachfrage aus dem übergebenen
     * HandelObjekt und die Auswahl des Handelspartners aus den restlichen Mitspielern. Speichert alles im handelObjekt.
     * 
     * @param handel
     */
    void zeigeSpielerHandel(Handel handel);

    /**
     * Erzeugt das KarteAbgebenMenü in einem Popup Fenster. Zeigt alle Karten des Spielers und entfernt die ausgewählte
     * Karte aus den Handkarten.
     * 
     * @param spieler
     */
    void zeigeKartenAbgeben(Spieler spieler, int anzahl);

    /**
     * Erzeugt eine Glückwünsch Nachricht mit dem Sieger in einem Popup Fenster.
     */
    void zeigeSieger();

    boolean zeigeConfirmation(String text);

    Pane zeigeSpielerInfos(Spieler spieler);

    Pane zeigeAvatar(Spieler spieler);

    void removeFromCenterAnimatedH(Node node);

}