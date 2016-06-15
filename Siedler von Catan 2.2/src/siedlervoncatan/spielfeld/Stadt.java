package siedlervoncatan.spielfeld;

import java.io.Serializable;
import java.util.Map;

import javafx.scene.image.Image;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Position;

public class Stadt implements Ortschaft, Serializable
{
    private static final long serialVersionUID = 1L;
    private Spieler           besitzer;
    private Position          position;
    private transient Image   image;

    public Stadt(Spieler besitzer, Position position) throws IllegalArgumentException
    {
        if (this.bauErlaubt(position, besitzer))
        {
            this.besitzer = besitzer;
            this.position = position;
            this.besitzer.getSpiel().getSpielfeld().putOrtschaft(this);
        }
        else
        {
            throw new IllegalArgumentException("Kann an dieser Position nicht gebaut werden.");
        }
    }

    /**
     * Überprüft, ob an der Position position eine Siedlung des Spielers besitzer existiert.
     * 
     * @param position
     * @param besitzer
     * @return true, wenn eine Siedlung existiert.
     */
    private boolean bauErlaubt(Position position, Spieler besitzer)
    {
        Map<Position, Ortschaft> ortschaften = besitzer.getOrtschaften();
        if (ortschaften.containsKey(position) && ortschaften.get(position) instanceof Siedlung)
        {
            return true;
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
        return 2;
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
            this.image = new Image("file:bilder/Stadt_" + farbe + ".png");
        }
        return this.image;
    }

    @Override
    public String toString()
    {
        return String.format("Stadt %s", this.besitzer.getFarbe());
    }

}
