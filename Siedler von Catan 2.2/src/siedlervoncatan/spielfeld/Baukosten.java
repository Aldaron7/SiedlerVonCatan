package siedlervoncatan.spielfeld;

import java.util.ArrayList;
import java.util.Collection;

import siedlervoncatan.enums.Rohstoff;

public class Baukosten
{
    public static final Collection<Rohstoff> SIEDLUNG;
    public static final Collection<Rohstoff> STADT;
    public static final Collection<Rohstoff> STRASSE;
    public static final Collection<Rohstoff> ENTWICKLUNGSKARTE;

    static
    {
        SIEDLUNG = new ArrayList<>();
        Baukosten.SIEDLUNG.add(Rohstoff.HOLZ);
        Baukosten.SIEDLUNG.add(Rohstoff.LEHM);
        Baukosten.SIEDLUNG.add(Rohstoff.KORN);
        Baukosten.SIEDLUNG.add(Rohstoff.WOLLE);

        STADT = new ArrayList<>();
        Baukosten.STADT.add(Rohstoff.ERZ);
        Baukosten.STADT.add(Rohstoff.ERZ);
        Baukosten.STADT.add(Rohstoff.ERZ);
        Baukosten.STADT.add(Rohstoff.KORN);
        Baukosten.STADT.add(Rohstoff.KORN);

        STRASSE = new ArrayList<>();
        Baukosten.STRASSE.add(Rohstoff.HOLZ);
        Baukosten.STRASSE.add(Rohstoff.LEHM);

        ENTWICKLUNGSKARTE = new ArrayList<>();
        Baukosten.ENTWICKLUNGSKARTE.add(Rohstoff.ERZ);
        Baukosten.ENTWICKLUNGSKARTE.add(Rohstoff.WOLLE);
        Baukosten.ENTWICKLUNGSKARTE.add(Rohstoff.KORN);
    }
}
