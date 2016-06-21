package siedlervoncatan.enums;

public enum Rohstoff
{
    LEHM, ERZ, HOLZ, WOLLE, KORN;

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
