package siedlervoncatan.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.spielfeld.Strasse;

public class Zusatzpunkte implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Überprüft, ob der Spieler spieler die größte Rittermacht bekommt.
     * 
     * @param spieler
     */
    public static void pruefeGroessteRittermacht(Spieler spieler)
    {
        if (!spieler.hatGroessteRittermacht())
        {
            boolean groessteRittermachtVergeben = false;
            for (Spieler andererSpieler : spieler.getSpiel().getAlleSpieler())
            {
                if (andererSpieler.hatGroessteRittermacht())
                {
                    groessteRittermachtVergeben = true;
                    if (spieler.getRitter().get() > andererSpieler.getRitter().get())
                    {
                        Zusatzpunkte.changeGroessteRittermacht(spieler, andererSpieler);
                    }
                }
            }
            if (groessteRittermachtVergeben == false)
            {
                Zusatzpunkte.setGroessteRittermacht(spieler);
            }
        }
    }

    /**
     * Der Spieler spieler bekommt die größte Rittermacht.
     * 
     * @param spieler
     */
    private static void setGroessteRittermacht(Spieler spieler)
    {
        spieler.bekommtGroessteRittermacht();
        spieler.erhoeheSiegpunkte();
        spieler.erhoeheSiegpunkte();
        spieler.getSpiel().getUserInterface().zeigeMessage(spieler + " hat nun die größte Rittermacht.");
    }

    /**
     * Der Spieler spieler bekommt die größte Rittermacht, der Spieler andererSpieler verliert die größte Rittermacht.
     * 
     * @param spieler
     * @param andererSpieler
     */
    private static void changeGroessteRittermacht(Spieler spieler, Spieler andererSpieler)
    {
        spieler.bekommtGroessteRittermacht();
        spieler.erhoeheSiegpunkte();
        spieler.erhoeheSiegpunkte();
        andererSpieler.verliertGroessteRittermacht();
        andererSpieler.erniedrigeSiegpunkte();
        andererSpieler.erniedrigeSiegpunkte();
        spieler.getSpiel().getUserInterface().zeigeMessage(spieler + " hat nun die größte Rittermacht.");
    }

    /**
     * Gibt ausgehend von der Strasse strasse die Länge der längsten zusammenhängenden Handelsstrasse von Spieler
     * spieler zurück.
     * 
     * @param spieler
     * @param strasse
     * @return
     */
    public static int laengsteHandelsstrasse(Spieler spieler, Strasse strasse)
    {
        Collection<Strasse> strassen = spieler.getStrassen().values();
        int laenge = 1;
        List<Strasse> kopieStrassen = new ArrayList<>(strassen);
        // die Anfangsstrasse darf nicht doppelt gezählt werden.
        kopieStrassen.remove(strasse);
        for (Position position : strasse.getPositionen())
        {
            laenge += Zusatzpunkte.laengsteHandelsstrasse(position, kopieStrassen);
        }
        return laenge;
    }

    /**
     * Berechnet rekursiv ausgehend von Position startpossition aus der Liste strassen die Länge der längsten
     * zusammenhängenden Handelsstrasse.
     * 
     * @param startposition
     * @param strassen
     * @return
     */
    private static int laengsteHandelsstrasse(Position startposition, List<Strasse> strassen)
    {
        // TODO gegnerische Siedlung unterbricht die längste Handelsstrasse

        int maxlaenge = 0;
        // da strassen rekursiv verändert wird muss über eine Kopie iteriert werden.
        List<Strasse> kopiestrassen = new ArrayList<>(strassen);
        for (Strasse strasse : kopiestrassen)
        {
            if (strassen.contains(strasse)) // aus strassen wurde immer die abgearbeitete strasse entfernt.
            {
                Set<Position> positionen = new HashSet<>(strasse.getPositionen());
                if (positionen.contains(startposition))
                {
                    positionen.remove(startposition);
                    strassen.remove(strasse);
                    int teillaenge = 1;
                    for (Position endposition : positionen) // nur die endposition ist noch im Set positionen enthalten.
                    {
                        teillaenge += Zusatzpunkte.laengsteHandelsstrasse(endposition, strassen);
                        maxlaenge = Math.max(maxlaenge, teillaenge); // gibt die maxlaenge aus allen möglichen Pfaden.
                    }
                }
            }
        }
        return maxlaenge;
    }

    /**
     * Überprüft, ob der Spieler spieler die längste Handelsstrasse bekommt.
     * 
     * @param spieler
     */
    public static void prüfeLaengsteHandelsstrasse(Spieler spieler)
    {
        if (!spieler.hatLaengsteHandelsstrasse())
        {
            boolean laengsteHandelsstrasseVergeben = false;
            for (Spieler andererSpieler : spieler.getSpiel().getAlleSpieler())
            {
                if (andererSpieler.hatLaengsteHandelsstrasse())
                {
                    laengsteHandelsstrasseVergeben = true;
                    if (spieler.getLaengsteHandelsstrasse() > andererSpieler.getLaengsteHandelsstrasse())
                    {
                        Zusatzpunkte.changeLaengsteHandelsstrasse(spieler, andererSpieler);
                    }
                }
            }
            if (laengsteHandelsstrasseVergeben == false)
            {
                Zusatzpunkte.setLaengsteHandelsstrasse(spieler);
            }
        }
    }

    /**
     * Der Spieler spieler bekommt die längste Handelsstrasse.
     * 
     * @param spieler
     */
    private static void setLaengsteHandelsstrasse(Spieler spieler)
    {
        spieler.bekommtLaengsteHandelsstrasse();
        spieler.erhoeheSiegpunkte();
        spieler.erhoeheSiegpunkte();
        spieler.getSpiel().getUserInterface().zeigeMessage(spieler + " hat nun die längste Handelsstraße.");
    }

    /**
     * Der Spieler spieler bekommt die längste Handelsstrasse, der Spieler andererSpieler verliert die längste
     * Handelsstrasse.
     * 
     * @param spieler
     * @param andererSpieler
     */
    private static void changeLaengsteHandelsstrasse(Spieler spieler, Spieler andererSpieler)
    {
        spieler.bekommtLaengsteHandelsstrasse();
        spieler.erhoeheSiegpunkte();
        spieler.erhoeheSiegpunkte();
        andererSpieler.verliertLaengsteHandelsstrasse();
        andererSpieler.erniedrigeSiegpunkte();
        andererSpieler.erniedrigeSiegpunkte();
        spieler.getSpiel().getUserInterface().zeigeMessage(spieler + " hat nun die längste Handelsstraße.");
    }
}
