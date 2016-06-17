package siedlervoncatan.test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Test
{
    public static final Set<String> COL = new HashSet<>();

    static
    {
        Collections.addAll(Test.COL, "a", "b");
    }

    public static void main(String[] args)
    {
        // ImageViewPosition test_0_1_5_13;
        // test_0_1_5_13.
        // System.out.println(null instanceof Object);

        // for (Entwicklung entwicklung : Entwicklung.getStapel())
        // {
        // System.out.println(entwicklung);
        // }

        // for (Rohstoff rohstoff : Rohstoff.values())
        // {
        // System.out.println(rohstoff);
        // String s = rohstoff.toString();
        // System.out.println(s);
        // }

        // List<String> lst = (List<String>) Test.COL;
        // System.out.println(lst);

        // List<Entwicklungskarte> lst = new LinkedList<>();
        // lst.add(new Entwicklungskarte());
        // lst.add(new Entwicklungskarte());
        // lst.add(new Entwicklungskarte());
        // lst.add(new Entwicklungskarte());
        // System.out.println(lst);
        // System.out.println(Entwicklung.getStapel());
        // System.out.println(Entwicklung.getStapel().size());

        // Set<Position> set1 = new HashSet<>();
        // Set<Position> set2 = new HashSet<>();
        // set1.add(new Position(1, 1));
        // set1.add(new Position(2, 1));
        // set2.add(new Position(2, 1));
        // set2.add(new Position(1, 1));
        // System.out.println(set1.equals(set2));

        // System.out.println(Spielfeld.getLandschaftsPositionen());
        // System.out.println(Spielfeld.getSpielfeldLandschaften());
        // System.out.println(Spielfeld.getSpielfeld());

        // Spiel spiel = new Spiel();
        // Spieler besitzer = new Spieler("Marcel", Farbe.BLAU, spiel);
        // spiel.addSpieler(besitzer);
        // Test.IO.spielfeldAnzeigen(spiel.getSpielfeld());
        //
        // Position position1 = new Position(3, 10);
        // besitzer.baueSiedlung(position1, true);
        // Position position2 = new Position(3, 12);
        // Position position3 = new Position(4, 13);
        // Position position4 = new Position(2, 13);
        // Position position5 = new Position(4, 9);
        // Position position6 = new Position(4, 7);
        // Position position7 = new Position(4, 15);
        // Position position8 = new Position(5, 12);
        // Position position9 = new Position(5, 10);
        // besitzer.baueStrasse(position1, position2, false, true);
        // System.out.println(besitzer.getLaengsteHandelsstrasse() + "\n" + besitzer.getStrassen());
        // besitzer.baueStrasse(position2, position3, false, true);
        // System.out.println(besitzer.getLaengsteHandelsstrasse() + "\n" + besitzer.getStrassen());
        // besitzer.baueStrasse(position2, position4, false, true);
        // System.out.println(besitzer.getLaengsteHandelsstrasse() + "\n" + besitzer.getStrassen());
        // besitzer.baueStrasse(position1, position5, false, true);
        // System.out.println(besitzer.getLaengsteHandelsstrasse() + "\n" + besitzer.getStrassen());
        // besitzer.baueStrasse(position6, position5, false, true);
        // System.out.println(besitzer.getLaengsteHandelsstrasse() + "\n" + besitzer.getStrassen());
        // besitzer.baueStrasse(position3, position7, false, true);
        // System.out.println(besitzer.getLaengsteHandelsstrasse() + "\n" + besitzer.getStrassen());
        // besitzer.baueStrasse(position3, position8, false, true);
        // System.out.println(besitzer.getLaengsteHandelsstrasse() + "\n" + besitzer.getStrassen());
        // besitzer.baueStrasse(position8, position9, false, true);
        // System.out.println(besitzer.getLaengsteHandelsstrasse() + "\n" + besitzer.getStrassen());
        // besitzer.baueStrasse(position9, position5, false, true);
        // System.out.println(besitzer.getLaengsteHandelsstrasse() + "\n" + besitzer.getStrassen());
        // Test.IO.spielfeldAnzeigen(spiel.getSpielfeld());

        // System.out.println(besitzer.getSpiel().getAlleSpieler());

        // System.out.println("Original:" + besitzer.getStrassen());
        // List<Strasse> strassen = new ArrayList<>(besitzer.getStrassen());
        // System.out.println("Kopie:" + strassen);
        // strassen.remove(0);
        // System.out.println("Original" + besitzer.getStrassen());
        // System.out.println("Kopie:" + strassen);

    }

}
