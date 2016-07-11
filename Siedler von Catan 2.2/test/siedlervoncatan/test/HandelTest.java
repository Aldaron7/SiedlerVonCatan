package siedlervoncatan.test;

import org.junit.Assert;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import siedlervoncatan.enums.Farbe;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Handel;

public class HandelTest
{

    @Test
    public void test()
    {
        ObservableList<Rohstoff> kartenSpieler1 = FXCollections.observableArrayList(Rohstoff.ERZ, Rohstoff.ERZ, Rohstoff.KORN, Rohstoff.KORN, Rohstoff.KORN);
        ObservableList<Rohstoff> kartenSpieler2 = FXCollections.observableArrayList(Rohstoff.HOLZ, Rohstoff.HOLZ, Rohstoff.HOLZ, Rohstoff.HOLZ);

        Spiel spiel = new Spiel();
        spiel.setUserInterface(new MenueTest());
        Spieler spieler1 = new Spieler("Marcel", Farbe.BRAUN, spiel);
        spieler1.addKarten(kartenSpieler1);

        Spieler spieler2 = new Spieler("Artem", Farbe.BLAU, spiel);
        spieler2.addKarten(kartenSpieler2);

        Handel handel = new Handel();

        handel.setAnbieter(spieler1);
        handel.setNachfrager(spieler2);

        handel.addAngebot(Rohstoff.ERZ);
        handel.addNachfrage(Rohstoff.HOLZ);

        handel.handeln();

        ObservableList<Rohstoff> karten1 = spieler1.getKarten();
        ObservableList<Rohstoff> karten2 = spieler2.getKarten();

        kartenSpieler1 = FXCollections.observableArrayList(Rohstoff.HOLZ, Rohstoff.ERZ, Rohstoff.KORN, Rohstoff.KORN, Rohstoff.KORN);
        kartenSpieler2 = FXCollections.observableArrayList(Rohstoff.ERZ, Rohstoff.HOLZ, Rohstoff.HOLZ, Rohstoff.HOLZ);

        Assert.assertTrue(karten1.containsAll(kartenSpieler1));
        Assert.assertTrue(karten2.containsAll(kartenSpieler2));

    }
}
