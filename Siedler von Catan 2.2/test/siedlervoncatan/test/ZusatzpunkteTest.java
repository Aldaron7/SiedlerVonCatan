package siedlervoncatan.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import javafx.util.Pair;
import siedlervoncatan.enums.Farbe;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.spielfeld.Strasse;
import siedlervoncatan.utility.Position;
import siedlervoncatan.utility.Zusatzpunkte;

public class ZusatzpunkteTest
{
    private Pair<Spieler, Strasse> initTest()
    {
        Spiel spiel = new Spiel();
        spiel.setUserInterface(new MenueTest());
        Spieler spieler = new Spieler("Spieler1", Farbe.BLAU, spiel);
        Set<Position> positionen = new HashSet<>();
        Position position = new Position(5, 6);
        spieler.baueSiedlung(position, true);
        positionen.add(position);
        position = new Position(5, 4);
        positionen.add(position);
        spieler.baueStrasse(positionen, false, true);

        positionen.clear();
        positionen.add(position);
        position = new Position(4, 3);
        positionen.add(position);
        spieler.baueStrasse(positionen, false, true);

        positionen.clear();
        positionen.add(position);
        position = new Position(3, 4);
        positionen.add(position);
        spieler.baueStrasse(positionen, false, true);

        positionen.clear();
        positionen.add(position);
        position = new Position(3, 6);
        positionen.add(position);
        spieler.baueStrasse(positionen, false, true);

        positionen.clear();
        positionen.add(position);
        position = new Position(4, 7);
        positionen.add(position);
        spieler.baueStrasse(positionen, false, true);

        positionen.clear();
        positionen.add(position);
        position = new Position(5, 6);
        positionen.add(position);
        spieler.baueStrasse(positionen, false, true);

        Strasse strasse = new Strasse(spieler, new Position(5, 6), new Position(6, 7), false);
        Pair<Spieler, Strasse> pair = new Pair<>(spieler, strasse);
        return pair;
    }

    @Test
    public void test()
    {
        Pair<Spieler, Strasse> pair = this.initTest();
        int laengsteHandelsstrasse = Zusatzpunkte.laengsteHandelsstrasse(pair.getKey(), pair.getValue());
        Assert.assertEquals(7, laengsteHandelsstrasse);
    }
}
