package siedlervoncatan.spielfeld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import siedlervoncatan.enums.Hafen;
import siedlervoncatan.enums.Landschaft;
import siedlervoncatan.utility.Position;

public class Spielfeld implements Serializable
{
    private static final long                               serialVersionUID       = 1L;
    private static final List<Position>                     LANDSCHAFTS_POSITIONEN = new ArrayList<>();
    private static final List<Landschaft>                   LANDSCHAFTEN           = new ArrayList<>();
    private static final List<Integer>                      ZAHLENCHIPS            = new ArrayList<>();
    private static final Map<Position, Hafen>               HAEFEN                 = new HashMap<>();
    private Map<Position, Landschaftsfeld>                  landschaftsfelder;
    private Map<Position, Ortschaft>                        ortschaftenSave;                           //
    private transient ObservableMap<Position, Ortschaft>    ortschaften;
    private Map<Set<Position>, Strasse>                     strassenSave;                              //
    private transient ObservableMap<Set<Position>, Strasse> strassen;
    private Landschaftsfeld                                 wueste;

    static
    {
        // erzeuge Landschaftszentren
        int[][] xAchsen = new int[][] { { 3, 5, 7 }, { 2, 4, 6, 8 }, { 1, 3, 5, 7, 9 }, { 2, 4, 6, 8 }, { 3, 5, 7 } };
        for (int i = 1; i < 6; i++)
        {
            for (int xAchse : xAchsen[i - 1]) // die i-te Reihe
            {
                Position posNeu = new Position(xAchse, 3 * i - 1); // y-Achse für 2,5,8,11,14
                Spielfeld.LANDSCHAFTS_POSITIONEN.add(posNeu);
            }
        }
        // erzeuge alle Landschaftsarten(3 * ERZ, LEHM; 4 * HOLZ, KORN, WOLLE; 1 * WUESTE)
        for (int i = 0; i < 3; i++)
        {
            Spielfeld.LANDSCHAFTEN.add(Landschaft.GEBIRGE);
            Spielfeld.LANDSCHAFTEN.add(Landschaft.HUEGELLAND);
        }
        for (int i = 0; i < 4; i++)
        {
            Spielfeld.LANDSCHAFTEN.add(Landschaft.WALD);
            Spielfeld.LANDSCHAFTEN.add(Landschaft.ACKERLAND);
            Spielfeld.LANDSCHAFTEN.add(Landschaft.WEIDELAND);
        }
        Spielfeld.LANDSCHAFTEN.add(Landschaft.WUESTE);

        // erzeuge alle Zahlenchips
        // 3 bis 11 (ohne 7) je zwei mal
        for (int i = 3; i < 12; i++)
        {
            if (i != 7)
            {
                Spielfeld.ZAHLENCHIPS.add(i);
                Spielfeld.ZAHLENCHIPS.add(i);
            }
        }
        // 2, 12 nur einmal
        Spielfeld.ZAHLENCHIPS.add(2);
        Spielfeld.ZAHLENCHIPS.add(12);

        // erzeuge Häfen
        Position key = new Position(1, 4);
        Spielfeld.HAEFEN.put(key, Hafen.WOLLE_HAFEN);
        key = new Position(2, 3);
        Spielfeld.HAEFEN.put(key, Hafen.WOLLE_HAFEN);
        key = new Position(4, 1);
        Spielfeld.HAEFEN.put(key, Hafen.ERZ_HAFEN);
        key = new Position(5, 0);
        Spielfeld.HAEFEN.put(key, Hafen.ERZ_HAFEN);
        key = new Position(9, 4);
        Spielfeld.HAEFEN.put(key, Hafen.KORN_HAFEN);
        key = new Position(9, 6);
        Spielfeld.HAEFEN.put(key, Hafen.KORN_HAFEN);
        key = new Position(4, 15);
        Spielfeld.HAEFEN.put(key, Hafen.LEHM_HAFEN);
        key = new Position(5, 16);
        Spielfeld.HAEFEN.put(key, Hafen.LEHM_HAFEN);
        key = new Position(1, 12);
        Spielfeld.HAEFEN.put(key, Hafen.HOLZ_HAFEN);
        key = new Position(2, 13);
        Spielfeld.HAEFEN.put(key, Hafen.HOLZ_HAFEN);
        key = new Position(0, 7);
        Spielfeld.HAEFEN.put(key, Hafen.DREI_ZU_EINS);
        key = new Position(0, 9);
        Spielfeld.HAEFEN.put(key, Hafen.DREI_ZU_EINS);
        key = new Position(7, 0);
        Spielfeld.HAEFEN.put(key, Hafen.DREI_ZU_EINS);
        key = new Position(8, 1);
        Spielfeld.HAEFEN.put(key, Hafen.DREI_ZU_EINS);
        key = new Position(9, 10);
        Spielfeld.HAEFEN.put(key, Hafen.DREI_ZU_EINS);
        key = new Position(9, 12);
        Spielfeld.HAEFEN.put(key, Hafen.DREI_ZU_EINS);
        key = new Position(7, 16);
        Spielfeld.HAEFEN.put(key, Hafen.DREI_ZU_EINS);
        key = new Position(8, 15);
        Spielfeld.HAEFEN.put(key, Hafen.DREI_ZU_EINS);

    }

    public Spielfeld()
    {
        this.strassen = FXCollections.observableHashMap();
        this.ortschaften = FXCollections.observableHashMap();
        this.landschaftsfelder = new HashMap<>();
        // erzeuge Spielfeld
        do
        {
            this.erzeugeSpielfeld();
        }
        while (this.roteZahlenNebeneinander());
    }

    /**
     * Erzeugt aus den LANDSCHAFTEN und ZAHLENCHIPS ein zufälliges Spielfeld.
     */
    private void erzeugeSpielfeld()
    {
        this.landschaftsfelder.clear();
        Collections.shuffle(Spielfeld.LANDSCHAFTEN);
        Collections.shuffle(Spielfeld.ZAHLENCHIPS);

        for (int i = 0, j = 0; i < Spielfeld.LANDSCHAFTS_POSITIONEN.size(); i++, j++)
        {
            Position position = Spielfeld.LANDSCHAFTS_POSITIONEN.get(i);
            Landschaft landschaft = Spielfeld.LANDSCHAFTEN.get(i);
            Landschaftsfeld landschaftsfeld;
            // Auf die Wüste kommt kein Zahlenchip.
            if (landschaft.equals(Landschaft.WUESTE))
            {
                landschaftsfeld = new Landschaftsfeld(position, landschaft, 0);
                this.wueste = landschaftsfeld;
                j--;
            }
            else
            {
                landschaftsfeld = new Landschaftsfeld(position, landschaft, Spielfeld.ZAHLENCHIPS.get(j));
            }
            this.landschaftsfelder.put(position, landschaftsfeld);
        }
    }

    /**
     * Überprüft, ob 6er und/oder 8er nebeneinander liegen.
     * 
     * @return true, wenn 6er und/oder 8er nebeneinander liegen.
     */
    private boolean roteZahlenNebeneinander()
    {
        for (Landschaftsfeld landschaftsfeld : this.landschaftsfelder.values())
        {
            int zahl = landschaftsfeld.getZahl();
            if (zahl == 6 || zahl == 8)
            {
                for (Landschaftsfeld andereLandschaft : this.landschaftsfelder.values())
                {
                    int andereZahl = andereLandschaft.getZahl();
                    if (andereLandschaft.getZentrum().isNachbarlandschaft(landschaftsfeld.getZentrum()) && (andereZahl == 6 || andereZahl == 8))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void putOrtschaft(Ortschaft ortschaft)
    {
        this.ortschaften.put(ortschaft.getPosition(), ortschaft);
    }

    public void addOrtschaftenListener(MapChangeListener<Position, Ortschaft> listener)
    {
        this.ortschaften.addListener(listener);
    }

    public void removeOrtschaftListener(MapChangeListener<Position, Ortschaft> listener)
    {
        this.ortschaften.removeListener(listener);
    }

    public void putStrasse(Strasse strasse)
    {
        this.strassen.put(strasse.getPositionen(), strasse);
    }

    public void addStrassenListener(MapChangeListener<Set<Position>, Strasse> listener)
    {
        this.strassen.addListener(listener);
    }

    public void removeStrassenListener(MapChangeListener<Set<Position>, Strasse> listener)
    {
        this.strassen.removeListener(listener);
    }

    public ObservableMap<Set<Position>, Strasse> getStrassen()
    {
        return this.strassen;
    }

    public static List<Position> getLandschaftsPositionen()
    {
        return Spielfeld.LANDSCHAFTS_POSITIONEN;
    }

    public Map<Position, Landschaftsfeld> getLandschaftsfelder()
    {
        return this.landschaftsfelder;
    }

    public ObservableMap<Position, Ortschaft> getBauplaetze()
    {
        return this.ortschaften;
    }

    public static Map<Position, Hafen> getHaefen()
    {
        return Spielfeld.HAEFEN;
    }

    public Landschaftsfeld getWueste()
    {
        return this.wueste;
    }

    /**
     * Schreibt die transient Objekte in die serialisierbaren Counterparts.
     */
    public void preSave()
    {
        this.ortschaftenSave = new HashMap<>(this.ortschaften);
        this.strassenSave = new HashMap<>(this.strassen);
    }

    /**
     * Schreibt in die transient Objekte die serialisierbaren Counterparts.
     */
    public void postLoad()
    {
        this.ortschaften = FXCollections.observableMap(this.ortschaftenSave);
        this.strassen = FXCollections.observableMap(this.strassenSave);
    }

}
