package siedlervoncatan.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import siedlervoncatan.enums.Farbe;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.spielfeld.Ortschaft;
import siedlervoncatan.spielfeld.Stadt;
import siedlervoncatan.utility.Position;

public class SpielTest
{
    private Spiel spiel;

    private void initTest()
    {
        this.spiel = new Spiel();
        Spieler spieler = new Spieler("Spieler1", Farbe.BLAU, this.spiel);
        this.spiel.addSpieler(spieler);
        spieler.baueSiedlung(new Position(5, 6), true);
        spieler.addKarte(Rohstoff.HOLZ);
        spieler.addKarte(Rohstoff.HOLZ);
        spieler.addKarte(Rohstoff.HOLZ);
        spieler.addKarte(Rohstoff.LEHM);
        spieler.addKarte(Rohstoff.LEHM);
        spieler.addKarte(Rohstoff.LEHM);
        spieler.addKarte(Rohstoff.WOLLE);
        spieler.addKarte(Rohstoff.WOLLE);
        spieler.addKarte(Rohstoff.KORN);
        spieler.addKarte(Rohstoff.KORN);
        spieler.addKarte(Rohstoff.KORN);
        spieler.addKarte(Rohstoff.KORN);
        spieler.addKarte(Rohstoff.ERZ);
        spieler.addKarte(Rohstoff.ERZ);
        spieler.addKarte(Rohstoff.ERZ);
        spieler.addKarte(Rohstoff.ERZ);
        this.spiel.setAktiverSpieler(spieler);
        this.spiel.setUserInterface(new MenueTest());
    }

    @Test
    public void test()
    {
        this.initTest();
        Set<Position> positionen = new HashSet<>();
        positionen.add(new Position(5, 6));
        positionen.add(new Position(5, 4));
        this.spiel.strasseBauen(positionen);
        positionen.clear();
        positionen.add(new Position(5, 4));
        Position position4_3 = new Position(4, 3);
        positionen.add(position4_3);
        this.spiel.strasseBauen(positionen);
        Assert.assertTrue(this.spiel.getSpielfeld().getStrassen().containsKey(positionen));

        this.spiel.siedlungBauen(new Position(5, 4));
        Assert.assertTrue(this.spiel.getSpielfeld().getBauplaetze().size() == 1);
        this.spiel.siedlungBauen(position4_3);
        Assert.assertTrue(this.spiel.getSpielfeld().getBauplaetze().size() == 2);

        Spieler spieler = this.spiel.getAlleSpieler().get(0);
        Assert.assertNotNull(spieler);

        this.spiel.stadtBauen(position4_3);
        Ortschaft ortschaft = spieler.getOrtschaften().get(position4_3);
        Assert.assertTrue(ortschaft instanceof Stadt);

        this.spiel.entwicklungKaufen();
        Assert.assertFalse(spieler.getEntwickulungskarten().isEmpty());
        Assert.assertTrue(spieler.getAnzahlKarten().get() == 0);
    }
}
