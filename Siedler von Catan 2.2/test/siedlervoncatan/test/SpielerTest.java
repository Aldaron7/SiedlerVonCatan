package siedlervoncatan.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import siedlervoncatan.enums.Farbe;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Position;

public class SpielerTest
{
    Spieler spieler1;

    @Test
    public void test()
    {
        this.initTest();
        Assert.assertFalse(this.spieler1.getOrtschaften().isEmpty());
        Assert.assertFalse(this.spieler1.getStrassen().isEmpty());
        boolean stadtGebaut = this.spieler1.baueStadt(new Position(5, 6));
        Assert.assertTrue(stadtGebaut);
        Assert.assertTrue(this.spieler1.getKarten().isEmpty());
    }

    private void initTest()
    {
        Spiel spiel = new Spiel();
        this.spieler1 = new Spieler("Spieler1", Farbe.BLAU, spiel);
        Position position5_6 = new Position(5, 6);
        this.spieler1.baueSiedlung(position5_6, true);
        this.spieler1.addKarte(Rohstoff.HOLZ);
        this.spieler1.addKarte(Rohstoff.LEHM);
        this.spieler1.addKarte(Rohstoff.KORN);
        this.spieler1.addKarte(Rohstoff.KORN);
        this.spieler1.addKarte(Rohstoff.ERZ);
        this.spieler1.addKarte(Rohstoff.ERZ);
        this.spieler1.addKarte(Rohstoff.ERZ);
        Set<Position> positionen = new HashSet<>();
        positionen.add(position5_6);
        positionen.add(new Position(5, 4));
        this.spieler1.baueStrasse(positionen, false, false);
    }
}
