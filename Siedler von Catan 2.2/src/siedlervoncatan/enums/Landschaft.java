package siedlervoncatan.enums;

import javafx.scene.paint.Color;

public enum Landschaft
{
    WALD(Rohstoff.HOLZ, Color.DARKGREEN), WEIDELAND(Rohstoff.WOLLE, Color.CHARTREUSE), ACKERLAND(Rohstoff.KORN, Color.GOLDENROD),
    HUEGELLAND(Rohstoff.LEHM, Color.CORAL), GEBIRGE(Rohstoff.ERZ, Color.GRAY), WUESTE(null, Color.YELLOW), MEER(null, Color.BLUE);

    private Rohstoff rohstoff;
    private Color    color;

    Landschaft(Rohstoff rohstoff, Color color)
    {
        this.rohstoff = rohstoff;
        this.color = color;
    }

    public Rohstoff getRohstoff()
    {
        return this.rohstoff;
    }

    public Color getColor()
    {
        return this.color;
    }

}
