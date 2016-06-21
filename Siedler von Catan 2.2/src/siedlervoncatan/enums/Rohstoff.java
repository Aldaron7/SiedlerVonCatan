package siedlervoncatan.enums;

import javafx.scene.image.Image;
import siedlervoncatan.utility.Pfade;

public enum Rohstoff
{
    LEHM(new Image(Pfade.LEHM.getPfad())), ERZ(new Image(Pfade.ERZ.getPfad())), HOLZ(new Image(Pfade.HOLZ.getPfad())), WOLLE(new Image(Pfade.WOLLE.getPfad())),
    KORN(new Image(Pfade.KORN.getPfad()));

    private Image image;

    private Rohstoff(Image image)
    {
        this.image = image;
    }

    public Image getImage()
    {
        return this.image;
    }

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
