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
     * Erzeugt das Spielfeld im Zentrum des RootLayouts. �bergibt den SpielfeldController an Spielstart.
     */
    void zeigeSpielfeld();

    /**
     * Erzeugt das NeueSpielMenue rechts im RootLayout Men�: Neuer Spieler, Spielen.
     */
    void zeigeNeuesspielMenue();

    /**
     * Erzeugt das SpielerAnlegenMen� in einem Popup Fenster. Men�: Name, Farbe, Ok, Abbrechen.
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
     * Erzeugt das EntwicklungskartenMen� in einem Popup Fenster. Zeigt die Entwicklungskarten mit Text und l�sst sie
     * ausspielen.
     */
    void zeigeEntwicklungskarten();

    /**
     * Erzeugt das Baumen� rechts im RootLayout. Men�: Strasse, Siedlung, Stadt, Entwicklung, Abbrechen. Wird disabled
     * wenn gebaut wird.
     */
    void zeigeBau();

    /**
     * Erzeugt die SpielInfo Anzeige links im RootLayout. Zeigt Spieler, Siegpunkte, Anzahl Karten, Anzahl ausgespielter
     * Ritter.
     */
    void zeigeSpielInfos();

    /**
     * Erzeugt das HandelMen� in einem Popup Fenster. Auswahl des Angebots und der Nachfrage, die in einem HandelObjekt
     * gespeichert werden.
     */
    void zeigeHandel();

    /**
     * Erzeugt das SpielerHandelMen� in einem Popup Fenster. Zeigt Angebot und Nachfrage aus dem �bergebenen
     * HandelObjekt und die Auswahl des Handelspartners aus den restlichen Mitspielern. Speichert alles im handelObjekt.
     * 
     * @param handel
     */
    void zeigeSpielerHandel(Handel handel);

    /**
     * Erzeugt das KarteAbgebenMen� in einem Popup Fenster. Zeigt alle Karten des Spielers und entfernt die ausgew�hlte
     * Karte aus den Handkarten.
     * 
     * @param spieler
     */
    void zeigeKartenAbgeben(Spieler spieler, int anzahl);

    /**
     * Erzeugt eine Gl�ckw�nsch Nachricht mit dem Sieger in einem Popup Fenster.
     */
    void zeigeSieger();

    boolean zeigeConfirmation(String text);

    Pane zeigeSpielerInfos(Spieler spieler);

    Pane zeigeAvatar(Spieler spieler);

    void removeFromCenterAnimatedH(Node node);

}