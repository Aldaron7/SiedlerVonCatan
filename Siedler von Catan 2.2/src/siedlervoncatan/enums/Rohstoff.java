package siedlervoncatan.enums;

import javafx.scene.image.Image;
import siedlervoncatan.view.View;

public enum Rohstoff
{
    LEHM(new Image(View.LEHM.getPfad())), ERZ(new Image(View.ERZ.getPfad())), HOLZ(new Image(View.HOLZ.getPfad())), WOLLE(new Image(View.WOLLE.getPfad())),
    KORN(new Image(View.KORN.getPfad()));

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
