package siedlervoncatan.spielfeld;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.image.Image;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Position;

/**
 * Eine Strasse wird zum ausbau des eigenen Reiches gebaut. Sie führt zu neuen Bauplätzen für Siedlungen. Wer die
 * längste Handelsstrasse besitzt erhält 2 Siegpunkte. Strassen werden an Kanten gebaut.
 * 
 * @author mvr
 *
 */
public class Strasse implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Spieler           besitzer;
    private Set<Position>     positionen;
    private transient Image   image;

    public Strasse(Spieler besitzer, Position position1, Position position2, boolean nurOrtsanbindung) throws IllegalArgumentException
    {
        boolean hatAnbindung = this.hatOrtschaftsanbindung(position1, besitzer) || this.hatOrtschaftsanbindung(position2, besitzer)
                               || this.hatStrassenanbindung(position1, besitzer) || this.hatStrassenanbindung(position2, besitzer);
        if (nurOrtsanbindung)
        {
            hatAnbindung = this.hatOrtschaftsanbindung(position1, besitzer) || this.hatOrtschaftsanbindung(position2, besitzer);
        }

        if (position1.isNachbar(position2) && this.bauplatzFrei(position1, position2, besitzer) && hatAnbindung)
        {
            this.besitzer = besitzer;
            this.positionen = new HashSet<>();
            this.positionen.add(position1);
            this.positionen.add(position2);
            this.besitzer.getSpiel().getSpielfeld().putStrasse(this);
        }
        else
        {
            throw new IllegalArgumentException("Kann zwischen diesen Positionen nicht gebaut werden.");
        }
    }

    /**
     * Überprüft, ob zwischen den Positionen position1 und position2 bereits eine Strasse existiert.
     * 
     * @param position1
     * @param position2
     * @param besitzer
     * @return true, wenn der Bauplatz frei ist.
     */
    private boolean bauplatzFrei(Position position1, Position position2, Spieler besitzer)
    {
        for (Set<Position> posStrasse : besitzer.getSpiel().getSpielfeld().getStrassen().keySet())
        {
            if (posStrasse.contains(position1) && posStrasse.contains(position2))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Überprüft, ob der Spieler besitzer an der Position position angrenzend eine Strasse besitzt.
     * 
     * @param position
     * @param besitzer
     * @return
     */
    private boolean hatStrassenanbindung(Position position, Spieler besitzer)
    {
        for (Set<Position> posStrasse : besitzer.getStrassen().keySet())
        {
            if (posStrasse != null && posStrasse.contains(position))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Überprüft, ob der Spieler besitzer an der Position position eine Ortschaft besitzt.
     * 
     * @param position
     * @param besitzer
     * @return
     */
    private boolean hatOrtschaftsanbindung(Position position, Spieler besitzer)
    {
        for (Position posOrtschaft : besitzer.getOrtschaften().keySet())
        {
            if (position.equals(posOrtschaft))
            {
                return true;
            }
        }
        return false;
    }

    public Spieler getBesitzer()
    {
        return this.besitzer;
    }

    public Set<Position> getPositionen()
    {
        return this.positionen;
    }

    public Image getImage()
    {
        if (this.image == null)
        {
            String farbe = this.besitzer.getFarbe().toString().toLowerCase();
            this.image = new Image("file:bilder/Strasse_" + farbe + ".png");
        }
        return this.image;
    }

    @Override
    public String toString()
    {
        return String.format("Strasse %s", this.besitzer.getFarbe());
    }

}
