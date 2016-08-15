package siedlervoncatan.enums;

import javafx.scene.image.Image;
import siedlervoncatan.utility.Pfade;

/**
 * Alle m�glichen Rohstoffarten mit ihrem jeweiligen Image zum anzeigen.
 * 
 * @author mvr
 *
 */
public enum Rohstoff
{
    LEHM(new Image(Pfade.LEHM)), ERZ(new Image(Pfade.ERZ)), HOLZ(new Image(Pfade.HOLZ)), WOLLE(new Image(Pfade.WOLLE)), KORN(new Image(Pfade.KORN));

    private Image image;

    private Rohstoff(Image image)
    {
        this.image = image;
    }

    public Image getImage()
    {
        return this.image;
    }

    /**
     * Gibt den passenden enum Rohstoff zum eingegebenen String rohstoff zur�ck.
     * 
     * @param rohstoff
     * @return enum Rohstoff
     */
    public static Rohstoff getRohstoff(String rohstoff)
    {
        switch (rohstoff.toLowerCase())
        {
            case "holz":
                return HOLZ;
            case "lehm":
                return LEHM;
            case "wolle":
                return WOLLE;
            case "korn":
                return KORN;
            case "erz":
                return ERZ;
            default:
                return null;
        }
    }
}
