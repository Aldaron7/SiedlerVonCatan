package siedlervoncatan.enums;

/**
 * Die Häfen mit ihren zu tauschenden Rohstoffen.
 * 
 * @author mvr
 *
 */
public enum Hafen
{
    DREI_ZU_EINS(null), HOLZ_HAFEN(Rohstoff.HOLZ), LEHM_HAFEN(Rohstoff.LEHM), WOLLE_HAFEN(Rohstoff.WOLLE), ERZ_HAFEN(Rohstoff.ERZ), KORN_HAFEN(Rohstoff.KORN);

    private Rohstoff rohstoff;

    private Hafen(Rohstoff rohstoff)
    {
        this.rohstoff = rohstoff;
    }

    public Rohstoff getRohstoff()
    {
        return this.rohstoff;
    }

}
