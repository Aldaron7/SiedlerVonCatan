package siedlervoncatan.spielfeld;

import java.io.Serializable;

import javafx.scene.image.Image;
import siedlervoncatan.enums.Landschaft;
import siedlervoncatan.utility.Position;

/**
 * Ein Spielfeldobjekt mit einer Landschaft, ihrer Position auf dem Spielfeld, der Zahl mit der der Rohstoff erhalten
 * wird und dem Image zum Anzeigen.
 * 
 * @author mvr
 *
 */
public class Landschaftsfeld implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Position          zentrum;
    private Landschaft        landschaft;
    private int               zahl;
    private transient Image   image;

    public Landschaftsfeld(Position zentrum, Landschaft landschaft, int zahl)
    {
        this.zentrum = zentrum;
        this.landschaft = landschaft;
        this.zahl = zahl;
    }

    public Landschaft getLandschaft()
    {
        return this.landschaft;
    }

    public int getZahl()
    {
        return this.zahl;
    }

    public Position getZentrum()
    {
        return this.zentrum;
    }

    public Image getImage()
    {
        if (this.image == null)
        {
            String art = this.landschaft.toString().toLowerCase();
            this.image = new Image("file:bilder/landschaft_" + art + ".png");
        }
        return this.image;
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %d.", this.zentrum, this.landschaft, this.zahl);
    }
}
