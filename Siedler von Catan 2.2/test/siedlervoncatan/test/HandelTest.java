package siedlervoncatan.test;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import siedlervoncatan.enums.Farbe;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Handel;

public class HandelTest {

	@Test
	public void test() {
		
		Spiel spiel = new Spiel();
		
		ObservableList<Rohstoff> angebot = FXCollections.observableArrayList(Rohstoff.ERZ, Rohstoff.ERZ, Rohstoff.KORN, Rohstoff.KORN, Rohstoff.KORN);
		ObservableList<Rohstoff> nachfrage = FXCollections.observableArrayList(Rohstoff.HOLZ, Rohstoff.HOLZ,Rohstoff.HOLZ,Rohstoff.HOLZ);

		
		
		Spieler spieler1 = new Spieler("Marcel", Farbe.BRAUN, spiel);
		spieler1.addKarten(angebot);
		//daskldmlaks
		
		Spieler spieler2 = new Spieler("Artem", Farbe.BLAU, spiel);
		spieler2.addKarten(nachfrage);
		
		Handel handel = new Handel();
		
		handel.setAnbieter(spieler1);
		handel.setNachfrager(spieler2);
		
		
		// Handel bestätigen bzw. ablehnen
		
		handel.handeln();
		
		
		
		
		
		
	}

}
