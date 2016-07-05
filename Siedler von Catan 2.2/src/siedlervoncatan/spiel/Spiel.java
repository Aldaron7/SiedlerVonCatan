package siedlervoncatan.spiel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import siedlervoncatan.Spielstart;
import siedlervoncatan.enums.Farbe;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.enums.Zustand;
import siedlervoncatan.io.UserInterface;
import siedlervoncatan.spielfeld.Entwicklungskarte;
import siedlervoncatan.spielfeld.Landschaftsfeld;
import siedlervoncatan.spielfeld.Raeuber;
import siedlervoncatan.spielfeld.Spielfeld;
import siedlervoncatan.utility.Position;
import siedlervoncatan.utility.Wuerfel;

public class Spiel implements Serializable, PropertyChangeListener
{
    private static final long                 serialVersionUID = 1L;
    public static List<Farbe>                 farben;
    private Position                          posSiedlung;
    private int                               anzahlRundenSave;
    private transient IntegerProperty         anzahlRunden;
    private List<Spieler>                     alleSpielerSave;
    private transient ObservableList<Spieler> alleSpieler;
    private Spielfeld                         spielfeld;
    private Wuerfel                           wuerfel;
    private Raeuber                           raeuber;
    private Spieler                           sieger;
    private Spieler                           aktiverSpieler;
    private transient Spielstart              spielstart;
    private transient UserInterface           ui;
    private Zustand                           zustand;
    private boolean                           hatGewuerfelt;
    private boolean                           saveable;

    public Spiel()
    {
        Spiel.farben = new ArrayList<>(Arrays.asList(Farbe.values()));
        this.alleSpieler = FXCollections.observableArrayList();
        this.spielfeld = new Spielfeld();
        this.wuerfel = new Wuerfel();
        this.wuerfel.addListener(this);
        this.raeuber = new Raeuber(this);
        this.sieger = null;
        this.anzahlRunden = new SimpleIntegerProperty(1);
        this.hatGewuerfelt = false;
        this.saveable = false;
    }

    public void setSpielstart(Spielstart spielstart)
    {
        this.spielstart = spielstart;
        this.ui = spielstart.getUserInterface();
    }

    /**
     * Startet ein neues Spiel. Fügt this dem SpielfeldController als Listener hinzu. Ruft das NeueSpielMenü auf.
     */
    public void starten()
    {
        this.ui.getSpielfeldController().addListener(this);
        this.ui.zeigeNeuesspielMenue();
    }

    private void autosave()
    {
        try
        {
            File file = new File("saves/autosave.svc");
            file.createNewFile();
            this.speichern(file);
        }
        catch (IOException e)
        {
            this.ui.zeigeError("Autospeichern konnte nicht ausgeführt werden.");
            e.printStackTrace();
        }
    }

    /**
     * Speichert this in file. Führt zuvor die preSave Methoden aus. Es kann nur im WürfelMenü und ZugMenü gespeichert
     * werden.
     * 
     * @param file
     */
    public void speichern(File file)
    {
        if (this.saveable)
        {
            try
            {
                this.preSave();
                Path path = file.toPath();
                OutputStream os = Files.newOutputStream(path);
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os));
                oos.writeObject(this);
                oos.flush();
                oos.close();
                os.close();
            }
            catch (Exception e)
            {
                this.ui.zeigeError("Spielstand konnte nicht gespeichert werden in der Datei:\n" + file.getPath());
                e.printStackTrace();
            }
        }
        else
        {
            this.ui.zeigeError("Spielstand kann jetzt nicht gespeichert werden.");
        }
    }

    /**
     * Ruft das SpielerAnlegenMenü auf.
     */
    public void spielerAnlegen()
    {
        this.ui.spielerAnlegen();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName().equals("Ecke") && this.zustand != null)
        {
            switch (this.zustand)
            {
                case ERSTE_SIEDLUNG:
                    this.setzeSiedlung((Position) evt.getNewValue());
                    break;
                case SIEDLUNG_BAUEN:
                    this.siedlungBauen((Position) evt.getNewValue());
                    break;
                case STADT_BAUEN:
                    this.stadtBauen((Position) evt.getNewValue());
                    break;

                default:
                    break;
            }
        }
        if (evt.getPropertyName().equals("Kante") && this.zustand != null)
        {
            switch (this.zustand)
            {
                case ERSTE_STRASSE:
                    this.setzeStrasse((Set<Position>) evt.getNewValue());
                    break;
                case STARSSE_BAUEN:
                    this.strasseBauen((Set<Position>) evt.getNewValue());
                    break;
                default:
                    break;
            }
        }
        if (evt.getPropertyName().equals("Landschaftsfeld") && this.zustand == Zustand.LANDSCHAFTSFELD)
        {
            this.versetzeRauber((Position) evt.getNewValue());
        }
        if (evt.getPropertyName().equals("Spieler") && this.zustand == Zustand.SPIELER)
        {
            this.angrenzenderSpieler((Spieler) evt.getNewValue());
        }

        if (evt.getPropertyName().equals("wuerfeln"))
        {
            String ergebnis = evt.getNewValue().toString();
            this.ui.zeigeMessage(this.aktiverSpieler + " hat eine " + ergebnis + " gewürfelt.");
        }
    }

    /**
     * Mischt die Zugreihenfolge der Spieler und ruft die SpielInfos auf.
     */
    public void spielen()
    {
        Collections.shuffle(this.alleSpieler);
        this.ui.zeigeSpielInfos();
        this.ersteRunde();
    }

    /**
     * Setzt einen geladenen Spielstand fort. Fügt this dem SpielfeldController als Listener hinzu.
     */
    public void weiterspielen()
    {
        this.ui.getSpielfeldController().addListener(this);
        if (this.hatGewuerfelt)
        {
            this.zeigeZug();
        }
        else
        {
            this.naechsteRunde();
        }
    }

    /**
     * Führt die erste Runde aus. Dabei werden zwei Siedlungen und zwei angrenzende Starssen der Reihe nach gesetzt.
     */
    private void ersteRunde()
    {
        this.aktiverSpieler = this.naechsterSpieler();
        if (this.aktiverSpieler != null)
        {
            this.setzeErsteRunde(false);
        }
        else
        {
            Collections.reverse(this.alleSpieler);
            for (Spieler spieler : this.alleSpieler)
            {
                spieler.hatGesetzt(false);
            }
            if (this.alleSpieler.get(0).getOrtschaften().size() < 2)
            {
                this.ersteRunde();
            }
            else
            {
                this.zustand = null;
                this.setSaveable();
                this.naechsteRunde();
            }
        }

    }

    /**
     * Setzt den Zustand auf ERSTE_SIEDLUNG wenn siedlungGebaut = false oder ERSTE_STRASSE wenn siedlungGebaut = true.
     * 
     * @param siedlungGebaut
     */
    private void setzeErsteRunde(boolean siedlungGebaut)
    {
        if (!siedlungGebaut)
        {
            // this.aktiverSpieler = this.naechsterSpieler();
            this.zustand = Zustand.ERSTE_SIEDLUNG;
            this.ui.zeigeMessage(this.aktiverSpieler + " wählen Sie einen Bauplatz für Ihre Siedlung.");

        }
        else
        {
            this.zustand = Zustand.ERSTE_STRASSE;
            this.ui.zeigeMessage(this.aktiverSpieler + " wählen Sie einen Bauplatz für Ihre Strasse.");
        }
    }

    /**
     * Der aktive Spieler setzt seine Siedlung. Speichert die Position der Siedlung in posSiedlung. Ruft setzeErsteRunde
     * mit siedlungGebaut = true auf.
     * 
     * @param position
     */
    private void setzeSiedlung(Position position)
    {
        this.posSiedlung = position;
        Boolean gebaut = this.aktiverSpieler.baueSiedlung(position, true);
        if (gebaut)
        {
            // erhalte für die zweite Siedlung die Rohstoffe
            if (this.aktiverSpieler.getOrtschaften().size() == 2)
            {
                for (Landschaftsfeld landschaftsfeld : this.spielfeld.getLandschaftsfelder().values())
                {
                    if (landschaftsfeld.getZentrum().isNachbar(position) && landschaftsfeld.getLandschaft().getRohstoff() != null)
                    {
                        Rohstoff rohstoff = landschaftsfeld.getLandschaft().getRohstoff();
                        this.aktiverSpieler.addKarte(rohstoff);
                    }
                }
            }
            this.setzeErsteRunde(gebaut);
        }
    }

    /**
     * Der aktive Spieler setzt seine Strasse angrenzend an die gesetzte Siedlung. Setzt aktiverSpieler auf hatGesetzt =
     * true. Ruft ersteRunde auf.
     * 
     * @param positionen
     */
    private void setzeStrasse(Set<Position> positionen)
    {
        if (positionen.contains(this.posSiedlung))
        {
            Boolean gebaut = this.aktiverSpieler.baueStrasse(positionen, true, true);
            if (gebaut)
            {
                this.aktiverSpieler.hatGesetzt(true);
                this.ersteRunde();
            }
        }
        else
        {
            this.ui.zeigeError("Strasse muss an die zuletzt gebaute Siedlung angrenzen.");
        }
    }

    /**
     * Gibt den nächsten Spieler in der Zugreihenfolge zurück.
     * 
     * @return nächster Spieler oder null falls alle Spieler ihren Zug beendet haben.
     */
    private Spieler naechsterSpieler()
    {
        for (Spieler spieler : this.alleSpieler)
        {
            if (spieler.getGespielteRunden() == this.anzahlRunden.get() || spieler.hatGesetzt())
            {
                continue;
            }
            return spieler;
        }
        return null;
    }

    /**
     * Startet den Zug des Nächsten Spielers in der Zugreihenfolge. Ruft das Würfelmenü auf falls noch kein Sieger
     * existiert, ansonsten die Siegernachricht. Speichert nach jeder Runde automatisch.
     */
    public void naechsteRunde()
    {
        this.hatGewuerfelt = false;
        this.ui.zeigeMessage("");
        if (this.sieger == null)
        {
            this.aktiverSpieler = this.naechsterSpieler();
            if (this.aktiverSpieler == null)
            {
                this.anzahlRunden.set(this.anzahlRunden.get() + 1);
                this.setSaveable();
                this.autosave();
                this.naechsteRunde();
            }
            else
            {
                this.ui.zeigeMessage(this.aktiverSpieler + " ist am Zug.");
                this.aktiverSpieler.setAktiv();
                this.aktiverSpieler.setEntwicklungskarteNichtGespielt();
                for (Entwicklungskarte entwicklungskarte : this.aktiverSpieler.getEntwickulungskarten())
                {
                    entwicklungskarte.darfGespieltWerden();
                }
                this.setSaveable();
                this.ui.zeigeWuerfel();
            }
        }
        else
        {
            this.ui.zeigeSieger();
        }
    }

    /**
     * Würfelt und ruft zeigeZug auf.
     */
    public void wuerfeln()
    {
        this.wuerfel.wuerfeln();
        if (this.aktiverSpieler.getRaeuberVersetzen())
        {
            this.aktiverSpieler.versetzeRauber();
        }
        else
        {
            this.zeigeZug();
        }
    }

    /**
     * Der aktive Spieler kauft eine Entwicklung.
     */
    public void entwicklungKaufen()
    {
        boolean gekauft = this.aktiverSpieler.kaufeEntwicklungskarte();
        if (gekauft)
        {
            this.ui.zeigeInfo(String.format("%s hat eine Entwicklungskarte gekauft.", this.aktiverSpieler));
        }
    }

    /**
     * Der aktive Spieler baut eine Stadt an der Position position. Setzt den Zustand auf null und ruft zeigeZug auf.
     * 
     * @param position
     */
    public void stadtBauen(Position position)
    {
        Boolean gebaut = this.aktiverSpieler.baueStadt(position);
        if (gebaut)
        {
            this.zustand = null;
            this.ui.zeigeMessage("");
        }
    }

    /**
     * Der aktive Spieler baut eine Siedlung an der Position position. Setzt den Zustand auf null und ruft zeigeZug auf.
     * 
     * @param position
     */
    public void siedlungBauen(Position position)
    {
        Boolean gebaut = this.aktiverSpieler.baueSiedlung(position, false);
        if (gebaut)
        {
            this.zustand = null;
            this.ui.zeigeMessage("");
        }
    }

    /**
     * Der aktive Spieler baut eine Strasse an der Position position. Setzt den Zustand auf null und ruft zeigeZug auf.
     * 
     * @param positionen
     */
    public void strasseBauen(Set<Position> positionen)
    {
        Boolean gebaut = this.aktiverSpieler.baueStrasse(positionen, false, false);
        if (gebaut)
        {
            this.zustand = null;
            this.ui.zeigeMessage("");
        }
    }

    /**
     * Der aktive Spieler versetzt den Räuber. Setzt den Zustand auf null, falls der Räuber versetzt wurde, ansonsten
     * auf SPIELER.
     * 
     * @param position
     */
    private void versetzeRauber(Position position)
    {
        Boolean versetzt = this.raeuber.versetze(position);
        if (versetzt)
        {
            this.aktiverSpieler.rauberVersetzt();
            if (this.raeuber.getAngrenzendeSpieler().isEmpty())
            {
                this.zustand = null;
                this.ui.zeigeMessage("");
                this.zeigeZug();
            }
            else
            {
                this.zustand = Zustand.SPIELER;
                this.ui.zeigeMessage(this.aktiverSpieler + " wählen Sie den Spieler bei dem Sie ziehen möchten.");
            }
        }
    }

    /**
     * Zieht eine Karte von Spieler spieler, falls dieser an dem vom Räuber besetzten Feld eine Ortschaft besitzt. Setzt
     * den Zustand auf null und ruft zeigeZug auf.
     * 
     * @param spieler
     */
    private void angrenzenderSpieler(Spieler spieler)
    {
        if (this.raeuber.getAngrenzendeSpieler().contains(spieler) && !this.aktiverSpieler.equals(spieler))
        {
            this.aktiverSpieler.zieheKarte(spieler);
            this.zustand = null;
            this.ui.zeigeMessage("");
            this.zeigeZug();
        }
        else
        {
            this.ui.zeigeError("Sie können nicht bei " + spieler + " ziehen.");
        }
    }

    /**
     * Setzt den Zustand auf null und ruft das Zugmenü auf.
     */
    private void zeigeZug()
    {
        this.zustand = null;
        this.hatGewuerfelt = true;
        this.saveable = true;
        this.ui.zeigeZug();
    }

    public void setSieger(Spieler spieler)
    {
        this.sieger = spieler;
    }

    public Spieler getSieger()
    {
        return this.sieger;
    }

    /**
     * Fügt alleSpieler den Spieler spieler und dem wuerfel als Listener hinzu.
     * 
     * @param spieler
     */
    public void addSpieler(Spieler spieler)
    {
        this.alleSpieler.add(spieler);
        this.wuerfel.addListener(spieler);
    }

    public ObservableList<Spieler> getAlleSpieler()
    {
        return this.alleSpieler;
    }

    public Raeuber getRaeuber()
    {
        return this.raeuber;
    }

    public Spielfeld getSpielfeld()
    {
        return this.spielfeld;
    }

    public UserInterface getUserInterface()
    {
        return this.ui;
    }

    public IntegerProperty getAnzahlRunden()
    {
        return this.anzahlRunden;
    }

    public Spieler getAktiverSpieler()
    {
        return this.aktiverSpieler;
    }

    public Spielstart getSpielstart()
    {
        return this.spielstart;
    }

    public void setZustand(Zustand zustand)
    {
        this.zustand = zustand;
    }

    public Zustand getZustand()
    {
        return this.zustand;
    }

    public boolean isSaveable()
    {
        return this.saveable;
    }

    public void setSaveable()
    {
        this.saveable = true;
    }

    public void setNotSaveable()
    {
        this.saveable = false;
    }

    public boolean hatGewuerfelt()
    {
        return this.hatGewuerfelt;
    }

    public Wuerfel getWuerfel()
    {
        return this.wuerfel;
    }

    /**
     * Schreibt die transient Objekte in die serialisierbaren Counterparts.
     */
    public void preSave()
    {
        this.alleSpielerSave = new ArrayList<>(this.alleSpieler);
        for (Spieler spieler : this.alleSpieler)
        {
            spieler.preSave();
        }
        this.spielfeld.preSave();
        this.raeuber.preSave();
        this.anzahlRundenSave = this.anzahlRunden.get();
    }

    /**
     * Schreibt in die transient Objekte die serialisierbaren Counterparts.
     */
    public void postLoad()
    {
        this.alleSpieler = FXCollections.observableArrayList(this.alleSpielerSave);
        for (Spieler spieler : this.alleSpieler)
        {
            spieler.postLoad();
        }
        this.spielfeld.postLoad();
        this.raeuber.postLoad();
        this.anzahlRunden = new SimpleIntegerProperty(this.anzahlRundenSave);
    }

}
