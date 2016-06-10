package siedlervoncatan.spielfeld;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javafx.scene.image.Image;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Position;

public class Siedlung implements Ortschaft, Serializable
{
    private static final long serialVersionUID = 1L;
    private Spieler           besitzer;
    private Position          position;
    private transient Image   image;

    public Siedlung(Spieler besitzer, Position position, boolean ignoriereStrassenanbindung) throws IllegalArgumentException
    {
        if (this.bauplatzFrei(position, besitzer) && (ignoriereStrassenanbindung || this.hatStrassenanbindung(position, besitzer)))
        {
            this.besitzer = besitzer;
            this.position = position;
            besitzer.getSpiel().getSpielfeld().putOrtschaft(this);
        }
        else
        {
            throw new IllegalArgumentException("Kann an dieser Position nicht gebaut werden.");
        }
    }

    /**
     * Überprüft, ob an der Position position gebaut werden darf.
     * 
     * @param position
     * @param besitzer
     * @return
     */
    private boolean bauplatzFrei(Position position, Spieler besitzer)
    {
        Map<Position, Ortschaft> bauplaetze = besitzer.getSpiel().getSpielfeld().getBauplaetze();
        // testet, ob bauplatz belegt ist.
        if (bauplaetze.get(position) != null)
        {
            return false;
        }
        // testet, ob nachbarbauplätze belegt sind.
        for (Position posNachbar : position.getNachbarn())
        {
            if (bauplaetze.get(posNachbar) != null)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Überprüft ob an der Position position der Spieler besitzer eine anschließende Strasse besitzt.
     * 
     * @param position
     * @param besitzer
     * @return
     */
    private boolean hatStrassenanbindung(Position position, Spieler besitzer)
    {
        for (Set<Position> posStrasse : besitzer.getStrassen().keySet())
        {
            if (posStrasse.contains(position))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public Spieler getBesitzer()
    {
        return this.besitzer;
    }

    @Override
    public int getErtrag()
    {
        return 1;
    }

    @Override
    public Position getPosition()
    {
        return this.position;
    }

    @Override
    public Image getImage()
    {
        if (this.image == null)
        {
            String farbe = this.besitzer.getFarbe().toString().toLowerCase();
            this.image = new Image("file:bilder/Siedlung_" + farbe + ".png");
        }
        return this.image;
    }

    @Override
    public String toString()
    {
        return String.format("Siedlung %s", this.besitzer.getFarbe());
    }
}
