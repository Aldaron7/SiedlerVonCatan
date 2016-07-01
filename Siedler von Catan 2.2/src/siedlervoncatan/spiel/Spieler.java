package siedlervoncatan.spiel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import siedlervoncatan.enums.Entwicklung;
import siedlervoncatan.enums.Farbe;
import siedlervoncatan.enums.Hafen;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.enums.Zustand;
import siedlervoncatan.spielfeld.Baukosten;
import siedlervoncatan.spielfeld.Entwicklungskarte;
import siedlervoncatan.spielfeld.Landschaftsfeld;
import siedlervoncatan.spielfeld.Ortschaft;
import siedlervoncatan.spielfeld.Siedlung;
import siedlervoncatan.spielfeld.Spielfeld;
import siedlervoncatan.spielfeld.Stadt;
import siedlervoncatan.spielfeld.Strasse;
import siedlervoncatan.utility.Error;
import siedlervoncatan.utility.Info;
import siedlervoncatan.utility.Position;
import siedlervoncatan.utility.Rohstoffauswahl;
import siedlervoncatan.utility.Wuerfel;
import siedlervoncatan.utility.Zusatzpunkte;

public class Spieler implements PropertyChangeListener, Serializable
{
    private static final long                           serialVersionUID = 1L;
    private String                                      name;
    private Farbe                                       farbe;
    private List<Rohstoff>                              kartenSave;                  //
    private transient ObservableList<Rohstoff>          karten;
    private List<Entwicklungskarte>                     entwicklungskartenSave;      //
    private transient ObservableList<Entwicklungskarte> entwicklungskarten;
    private Map<Position, Ortschaft>                    ortschaften;
    private Map<Set<Position>, Strasse>                 strassen;
    private int                                         anzahlSiedlungen;
    private int                                         anzahlStaedte;
    private int                                         anzahlStrassen;
    private Set<Hafen>                                  haefen;
    private Spiel                                       spiel;
    private boolean                                     aktiv;
    private int                                         siegpunkteSave;              //
    private transient IntegerProperty                   siegpunkte;
    private int                                         ritterSave;                  //
    private transient IntegerProperty                   ritter;
    private int                                         anzahlKartenSave;            //
    private transient IntegerProperty                   anzahlKarten;
    private int                                         laengsteHandelsstrasse;
    private boolean                                     hatGroessteRittermacht;
    private boolean                                     hatLaengsteHandelsstrasse;
    private boolean                                     hatEntwicklungskarteGespielt;
    private boolean                                     raeuberVersetzen;
    private int                                         gespielteRunden;
    private boolean                                     hatGesetzt;

    public Spieler(String name, Farbe farbe, Spiel spiel)
    {
        this.name = name;
        this.farbe = farbe;
        this.spiel = spiel;
        this.karten = FXCollections.observableArrayList();
        this.entwicklungskarten = FXCollections.observableArrayList();
        this.ortschaften = new HashMap<>();
        this.strassen = new HashMap<>();
        this.anzahlSiedlungen = 0;
        this.anzahlStaedte = 0;
        this.anzahlStrassen = 0;
        this.haefen = new HashSet<>();
        this.aktiv = false;
        this.siegpunkte = new SimpleIntegerProperty(0);
        this.ritter = new SimpleIntegerProperty(0);
        this.anzahlKarten = new SimpleIntegerProperty(0);
        this.laengsteHandelsstrasse = 0;
        this.hatGroessteRittermacht = false;
        this.hatLaengsteHandelsstrasse = false;
        this.hatEntwicklungskarteGespielt = false;
        this.raeuberVersetzen = false;
        this.gespielteRunden = 0;
        this.hatGesetzt = false;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue().equals(7))
        {
            if (this.karten.size() > 7)
            {
                // die Hälfte der Karten (abgerundet) wird abgegeben
                int anzahlAbzugebendeKarten = this.karten.size() / 2;

                this.spiel.getUserInterface().zeigeKartenAbgeben(this, anzahlAbzugebendeKarten);

            }
            if (this.isAktiv())
            {
                this.raeuberVersetzen = true;
            }
        }
        else
        {
            // ziehe Karten
            for (Ortschaft ortschaft : this.ortschaften.values())
            {
                for (Landschaftsfeld landschaftsfeld : this.spiel.getSpielfeld().getLandschaftsfelder().values())
                {
                    if (landschaftsfeld.getLandschaft().getRohstoff() != null && evt.getNewValue().equals(landschaftsfeld.getZahl())
                        && !landschaftsfeld.getZentrum().equals(this.spiel.getRaeuber().getPosition().get())
                        && landschaftsfeld.getZentrum().isNachbar(ortschaft.getPosition()))
                    {
                        int ertrag = ortschaft.getErtrag();
                        Rohstoff rohstoff = landschaftsfeld.getLandschaft().getRohstoff();
                        for (int i = 0; i < ertrag; i++)
                        {
                            this.addKarte(rohstoff);
                        }
                    }
                }
            }
        }
    }

    /**
     * Baut eine Strasse zwischen den angegebenen Positionen. Es kann nurOrtsanbindung gefordert werden. Die Strasse
     * kann kostenlos sein.
     * 
     * @param positionen
     * @param nurOrtsanbindung
     * @param kostenlos
     * @return
     */
    public boolean baueStrasse(Set<Position> positionen, boolean nurOrtsanbindung, boolean kostenlos)
    {
        if (kostenlos || this.decktKosten(Baukosten.STRASSE) && this.anzahlStrassen < 15 && positionen.size() == 2)
        {
            try
            {
                List<Position> pos = new ArrayList<>();
                positionen.forEach(p -> pos.add(p));
                Strasse strasse = new Strasse(this, pos.get(0), pos.get(1), nurOrtsanbindung);
                this.strassen.put(strasse.getPositionen(), strasse);
                // berechne längste Handelsstrasse neu
                int laengsteHandelsstrasse = Zusatzpunkte.laengsteHandelsstrasse(this, strasse);
                if (this.laengsteHandelsstrasse < laengsteHandelsstrasse && laengsteHandelsstrasse >= 5)
                {
                    this.laengsteHandelsstrasse = laengsteHandelsstrasse;
                    Zusatzpunkte.prüfeLaengsteHandelsstrasse(this);
                }
                if (!kostenlos)
                {
                    this.removeKarten(Baukosten.STRASSE);
                }
                this.anzahlStrassen++;
                return true;
            }
            catch (IllegalArgumentException e)
            {
                new Error("Strasse konnte nicht gebaut werden.");
            }
        }
        else
        {
            new Error("Nicht genügend Rohstoffe vorhanden.");
        }
        return false;
    }

    /**
     * 
     * Baut eine Siedlung an der angegebenen Position. Die Siedlung kann kostenlos sein.
     * 
     * @param position
     * @param kostenlos
     * @return
     */
    public boolean baueSiedlung(Position position, boolean kostenlos)
    {
        if (kostenlos || this.decktKosten(Baukosten.SIEDLUNG) && this.anzahlSiedlungen < 5)
        {
            Siedlung siedlung;
            boolean ignoriereStrassenanbindung = kostenlos;
            try
            {
                siedlung = new Siedlung(this, position, ignoriereStrassenanbindung);
                this.ortschaften.put(position, siedlung);
                this.erhoeheSiegpunkte();
                if (Spielfeld.getHaefen().containsKey(position))
                {
                    this.haefen.add(Spielfeld.getHaefen().get(position));
                }
                if (!kostenlos)
                {
                    this.removeKarten(Baukosten.SIEDLUNG);
                }
                this.anzahlSiedlungen++;
                return true;
            }
            catch (IllegalArgumentException e)
            {
                new Error("Siedlung konnte nicht gebaut werden.");
            }
        }
        else
        {
            new Error("Nicht genügend Rohstoffe vorhanden.");
        }
        return false;
    }

    /**
     * Baut eine Stadt an der angegebenen Position.
     * 
     * @param position
     * @return
     */
    public boolean baueStadt(Position position)
    {
        if (this.decktKosten(Baukosten.STADT) && this.anzahlStaedte < 4)
        {
            try
            {
                Stadt stadt = new Stadt(this, position);
                this.ortschaften.put(position, stadt);
                this.erhoeheSiegpunkte();
                this.removeKarten(Baukosten.STADT);
                this.anzahlStaedte++;
                this.anzahlSiedlungen--;
                return true;
            }
            catch (IllegalArgumentException e)
            {
                new Error("Stadt konnte nicht gebaut werden.");
            }
        }
        else
        {
            new Error("Nicht genügend Rohstoffe vorhanden.");
        }
        return false;
    }

    /**
     * Entfernt alle Rohstoffe rohstoffe aus den karten. Aktualisiert die anzahlKarten.
     * 
     * @param rohstoffe
     */
    public void removeKarten(Collection<Rohstoff> rohstoffe)
    {
        for (Rohstoff rohstoff : rohstoffe)
        {
            this.karten.remove(rohstoff);
        }
        this.anzahlKarten.set(this.karten.size());
    }

    /**
     * Entfernt den Rohstoff rohstoff aus den karten. Aktualisiert die anzahlKarten.
     * 
     * @param rohstoff
     */
    public void removeKarte(Rohstoff rohstoff)
    {
        this.karten.remove(rohstoff);
        this.anzahlKarten.set(this.karten.size());
    }

    /**
     * Fügt die Rohstoffe rohstoffe den karten hinzu. Aktualisiert die anzahlKarten.
     * 
     * @param rohstoffe
     */
    public void addKarten(Collection<Rohstoff> rohstoffe)
    {
        for (Rohstoff rohstoff : rohstoffe)
        {
            this.karten.add(rohstoff);
        }
        this.anzahlKarten.set(this.karten.size());
    }

    /**
     * Fügt den Rohstoff rohstoff den karten hinzu. Aktualisiert die anzahlKarten.
     * 
     * @param rohstoff
     */
    public void addKarte(Rohstoff rohstoff)
    {
        this.karten.add(rohstoff);
        this.anzahlKarten.set(this.karten.size());
    }

    /**
     * Überprüft ob die kosten gedeckt sind.
     * 
     * @param kosten
     * @return true, wenn kosten in karten enthalten sind.
     */
    private boolean decktKosten(Collection<Rohstoff> kosten)
    {
        for (Rohstoff rohstoff : kosten)
        {
            if (Collections.frequency(this.karten, rohstoff) < (Collections.frequency(kosten, rohstoff)))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Erzeugt eine neue Entwicklungskarte und fügt sie den entwicklungskarten hinzu.
     * 
     * @return true, wenn eine Entwicklungskarte erzeugt wurde.
     */
    public boolean kaufeEntwicklungskarte()
    {
        if (this.decktKosten(Baukosten.ENTWICKLUNGSKARTE) && Entwicklung.istNichtLeer())
        {
            Entwicklungskarte entwicklungskarte = new Entwicklungskarte(this);
            this.entwicklungskarten.add(entwicklungskarte);
            this.removeKarten(Baukosten.ENTWICKLUNGSKARTE);
            return true;
        }
        else
        {
            new Error("Nicht genügend Rohstoffe vorhanden.");
        }
        return false;
    }

    /**
     * Entfernt einen zufälligen Rohstoff aus den karten.
     * 
     * @return den entfernten Rohstoff.
     */
    public Rohstoff removeZufaelligeKarte()
    {
        int maxWert = this.karten.size();
        int zufallsZahl = Wuerfel.generiereZufallsZahl(maxWert) - 1;
        Rohstoff karte = this.karten.remove(zufallsZahl);
        this.anzahlKarten.set(this.karten.size());
        return karte;
    }

    /**
     * Spielt die Entwicklungskarte entwicklungskarte aus.
     * 
     * @param entwicklungskarte
     */
    public void spieleEntwicklungskarte(Entwicklungskarte entwicklungskarte)
    {
        if (this.entwicklungskarten.contains(entwicklungskarte))
        {
            boolean ausgespielt = entwicklungskarte.ausspielen();
            if (ausgespielt)
            {
                this.entwicklungskarten.remove(entwicklungskarte);
            }
        }
    }

    /**
     * Setzt den Zustand des Spiels auf LANDSCHAFTSFELD.
     */
    public void versetzeRauber()
    {
        this.spiel.setNotSaveable();
        // TODO
        // this.spiel.getMenue().zeigeLeeresMenue();
        this.spiel.getSpielstart().getSpielfeldController().setMessages(this + " bitte versetzen Sie den Räuber.");
        this.spiel.setZustand(Zustand.LANDSCHAFTSFELD);
    }

    /**
     * Entfernt eine zufällige Karte von Spieler spieler und fügt sie den karten hinzu.
     * 
     * @param spieler
     */
    public void zieheKarte(Spieler spieler)
    {
        if (spieler.getAnzahlKarten().get() > 0)
        {
            new Info(this + " zieht eine Karte von " + spieler);
            Rohstoff karte = spieler.removeZufaelligeKarte();
            this.addKarte(karte);
        }
        else
        {
            new Info(spieler + " hat keine Karten.");
        }
    }

    /**
     * Wählt den abzugebenden und den zu erhaltenden Rohstoff aus und ruft tauscheRohstoffe auf.
     */
    public void seehandel()
    {
        Rohstoff abzugeben = Rohstoffauswahl.getRohstoff(this + " wählen Sie den Rohstoff, den Sie abgeben möchten.");
        Rohstoff erhalten = Rohstoffauswahl.getRohstoff(this + " wählen Sie den Rohstoff, gegen den Sie tauschen möchten.");

        this.tauscheRohstoffe(abzugeben, erhalten);
    }

    /**
     * Entfernt den Rohstoff abzugeben aus den karten entsprechend dem umtauschkurs und fügt den Rohstoff erhalten den
     * karten hinzu.
     * 
     * @param abzugeben
     * @param erhalten
     */
    public void tauscheRohstoffe(Rohstoff abzugeben, Rohstoff erhalten)
    {
        int vorhandenerRohstoff = Collections.frequency(this.karten, abzugeben);
        int umtauschkurs = 4;

        if (this.haefen.contains(Hafen.DREI_ZU_EINS))
        {
            umtauschkurs = 3;
        }

        for (Hafen hafen : this.haefen)
        {
            if (abzugeben.equals(hafen.getRohstoff()))
            {
                umtauschkurs = 2;
            }
        }
        if (umtauschkurs <= vorhandenerRohstoff)
        {
            boolean antwort = this.spiel.getUserInterface()
                            .zeigeConfirmation(String.format("Wolen Sie %s gegen %s im Verhältnis %d:1 tauschen?", abzugeben, erhalten, umtauschkurs));
            final int tauschkurs = umtauschkurs;
            if (antwort)
            {
                for (int i = 0; i < tauschkurs; i++)
                {
                    this.removeKarte(abzugeben);
                }
                this.addKarte(erhalten);
            }
        }
        else

        {
            new Error("Nicht genügend Rohstoffe vorhanden.");
        }
    }

    public ObservableList<Entwicklungskarte> getEntwickulungskarten()
    {
        return this.entwicklungskarten;
    }

    public boolean hatGroessteRittermacht()
    {
        return this.hatGroessteRittermacht;
    }

    public void bekommtGroessteRittermacht()
    {
        this.hatGroessteRittermacht = true;
    }

    public void verliertGroessteRittermacht()
    {
        this.hatGroessteRittermacht = false;
    }

    public boolean hatLaengsteHandelsstrasse()
    {
        return this.hatLaengsteHandelsstrasse;
    }

    public void bekommtLaengsteHandelsstrasse()
    {
        this.hatLaengsteHandelsstrasse = true;
    }

    public void verliertLaengsteHandelsstrasse()
    {
        this.hatLaengsteHandelsstrasse = false;
    }

    /**
     * Erhöht die siegpunkte um 1. Falls danach mehr als 10 Siegpunkte erreicht sind wird der Sieger auf this gesetzt.
     */
    public void erhoeheSiegpunkte()
    {
        this.siegpunkte.set(this.siegpunkte.get() + 1);
        if (this.siegpunkte.get() >= 10 && this.spiel.getSieger() == null)
        {
            this.spiel.setSieger(this);
        }
    }

    public void erniedrigeSiegpunkte()
    {
        this.siegpunkte.subtract(1);
    }

    public Farbe getFarbe()
    {
        return this.farbe;
    }

    public Set<Hafen> getHaefen()
    {
        return this.haefen;
    }

    /**
     * Gibt eine sortierte Liste der karten zurück.
     * 
     * @return
     */
    public ObservableList<Rohstoff> getKarten()
    {
        Collections.sort(this.karten);
        return this.karten;
    }

    public String getName()
    {
        return this.name;
    }

    public Map<Position, Ortschaft> getOrtschaften()
    {
        return this.ortschaften;
    }

    public Map<Set<Position>, Strasse> getStrassen()
    {
        return this.strassen;
    }

    public boolean isAktiv()
    {
        return this.aktiv;
    }

    public void setAktiv()
    {
        this.aktiv = true;
    }

    public void setNichtAktiv()
    {
        this.aktiv = false;
    }

    public IntegerProperty getSiegpunkte()
    {
        return this.siegpunkte;
    }

    public int getLaengsteHandelsstrasse()
    {
        return this.laengsteHandelsstrasse;
    }

    public IntegerProperty getRitter()
    {
        return this.ritter;
    }

    /**
     * Erhöht die Anzahl der Ritter um 1. Überprüft auf größte Rittermacht.
     */
    public void addRitter()
    {
        this.ritter.set(this.ritter.get() + 1);
        if (this.ritter.get() >= 3)
        {
            Zusatzpunkte.pruefeGroessteRittermacht(this);
        }
    }

    public boolean entwicklungskarteGespielt()
    {
        return this.hatEntwicklungskarteGespielt;
    }

    public void setEntwicklungskarteGespielt()
    {
        this.hatEntwicklungskarteGespielt = true;
    }

    public void setEntwicklungskarteNichtGespielt()
    {
        this.hatEntwicklungskarteGespielt = false;
    }

    public Spiel getSpiel()
    {
        return this.spiel;
    }

    public boolean getRaeuberVersetzen()
    {
        return this.raeuberVersetzen;
    }

    public void rauberVersetzt()
    {
        this.raeuberVersetzen = false;
    }

    public void erhoeheGespielteRunden()
    {
        this.gespielteRunden++;
    }

    public int getGespielteRunden()
    {
        return this.gespielteRunden;
    }

    public boolean hatGesetzt()
    {
        return this.hatGesetzt;
    }

    public void hatGesetzt(boolean hatGesetzt)
    {
        this.hatGesetzt = hatGesetzt;
    }

    public IntegerProperty getAnzahlKarten()
    {
        return this.anzahlKarten;
    }

    /**
     * Schreibt die transient Objekte in die serialisierbaren Counterparts.
     */
    public void preSave()
    {
        this.entwicklungskartenSave = new ArrayList<>(this.entwicklungskarten);
        this.kartenSave = new ArrayList<>(this.karten);
        this.anzahlKartenSave = this.anzahlKarten.get();
        this.ritterSave = this.ritter.get();
        this.siegpunkteSave = this.siegpunkte.get();
    }

    /**
     * Schreibt in die transient Objekte die serialisierbaren Counterparts.
     */
    public void postLoad()
    {
        this.entwicklungskarten = FXCollections.observableArrayList(this.entwicklungskartenSave);
        this.karten = FXCollections.observableArrayList(this.kartenSave);
        this.anzahlKarten = new SimpleIntegerProperty(this.anzahlKartenSave);
        this.ritter = new SimpleIntegerProperty(this.ritterSave);
        this.siegpunkte = new SimpleIntegerProperty(this.siegpunkteSave);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.farbe == null) ? 0 : this.farbe.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Spieler))
        {
            return false;
        }
        Spieler other = (Spieler) obj;
        if (this.farbe != other.farbe)
        {
            return false;
        }
        if (this.name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else
            if (!this.name.equals(other.name))
            {
                return false;
            }
        return true;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
