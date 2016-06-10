package siedlervoncatan.spielfeld;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Error;
import siedlervoncatan.utility.Position;

public class Raeuber implements Serializable
{
    private static final long                  serialVersionUID = 1L;
    private Position                           positionSave;
    private transient ObjectProperty<Position> position;
    private Set<Spieler>                       angrenzendeSpieler;
    private Spiel                              spiel;

    public Raeuber(Spiel spiel)
    {
        Position zentrum = spiel.getSpielfeld().getWueste().getZentrum();
        this.position = new SimpleObjectProperty<Position>(zentrum);
        this.angrenzendeSpieler = new HashSet<>();
        this.spiel = spiel;
        this.position.addListener(e -> spiel.getSpielstart().getSpielfeldController().erzeugeSpielfeld());
    }

    public Set<Spieler> getAngrenzendeSpieler()
    {
        return this.angrenzendeSpieler;
    }

    /**
     * Versetzt den Räuber auf die neuePosition.
     * 
     * @param neuePosition
     * @return true, wenn der Räuber versetzt wurde.
     */
    public boolean versetze(Position neuePosition)
    {
        if (this.position.equals(neuePosition))
        {
            new Error("Der Räuber muss auf eine neue Position gesetzt werden.");
            return false;
        }
        else
        {
            this.position.set(neuePosition);
            this.setAngrenzendeSpieler(neuePosition);
            return true;
        }
    }

    /**
     * Setzt angrenzende Spieler auf alle Spieler an der neuen Position.
     * 
     * @param neuePosition
     */
    private void setAngrenzendeSpieler(Position neuePosition)
    {
        this.angrenzendeSpieler.clear();
        for (Spieler spieler : this.spiel.getAlleSpieler())
        {
            if (!spieler.isAktiv())
            {
                for (Position positionOrtschaft : spieler.getOrtschaften().keySet())
                {
                    if (positionOrtschaft.isNachbar(neuePosition))
                    {
                        this.angrenzendeSpieler.add(spieler);
                    }
                }
            }
        }
    }

    public ObjectProperty<Position> getPosition()
    {
        return this.position;
    }

    /**
     * Schreibt die transient Objekte in die serialisierbaren Counterparts.
     */
    public void preSave()
    {
        this.positionSave = this.position.get();
    }

    /**
     * Schreibt in die transient Objekte die serialisierbaren Counterparts.
     */
    public void postLoad()
    {
        this.position = new SimpleObjectProperty<Position>(this.positionSave);
        this.position.addListener(e -> this.spiel.getSpielstart().getSpielfeldController().erzeugeSpielfeld());
    }

}
