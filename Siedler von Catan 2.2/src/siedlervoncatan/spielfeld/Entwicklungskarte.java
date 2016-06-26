package siedlervoncatan.spielfeld;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import siedlervoncatan.enums.Entwicklung;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Error;
import siedlervoncatan.utility.Info;
import siedlervoncatan.utility.Position;
import siedlervoncatan.utility.Rohstoffauswahl;
import siedlervoncatan.view.controller.SpielfeldController;

public class Entwicklungskarte implements Serializable, PropertyChangeListener
{
    private static final long serialVersionUID = 1L;
    private Entwicklung       entwicklung;
    private boolean           darfGespieltWerden;
    private Spieler           besitzer;
    private boolean           strasse1;

    public Entwicklungskarte(Spieler besitzer)
    {
        this.besitzer = besitzer;
        this.entwicklung = this.getZufaelligeEntwicklung();
        if (this.entwicklung.equals(Entwicklung.SIEGPUNKT))
        {
            this.darfGespieltWerden = true;
        }
        else
        {
            this.darfGespieltWerden = false;
        }
    }

    /**
     * Führt die Aktion entsprechend der entwicklung aus.
     * 
     * @return true, wenn die Aktion ausgeführt wurde.
     */
    public boolean ausspielen()
    {
        if (this.entwicklung == Entwicklung.SIEGPUNKT)
        {
            new Info(this.besitzer + " spilet die karte Siegpunkt.");
            this.besitzer.erhoeheSiegpunkte();
            this.besitzer.getEntwickulungskarten().remove(this);
            this.zeigeMenue();
            return true;
        }
        if (!this.besitzer.entwicklungskarteGespielt())
        {
            SpielfeldController spielfeldController = this.besitzer.getSpiel().getSpielstart().getSpielfeldController();
            switch (this.entwicklung)
            {
                case RITTER:
                    new Info(this.besitzer + " spielt die Karte Ritter.");
                    this.besitzer.versetzeRauber();
                    this.besitzer.addRitter();
                    break;
                case ROHSTOFFMONOPOL:
                    Rohstoff rohstoff = Rohstoffauswahl.getRohstoff();
                    new Info(this.besitzer + " spielt die Karte Rohstoffmonopol und bekommt alles " + rohstoff + ".");
                    for (Spieler spieler : this.besitzer.getSpiel().getAlleSpieler())
                    {
                        if (!this.besitzer.equals(spieler))
                        {
                            List<Rohstoff> kopieKarten = new ArrayList<>(spieler.getKarten());
                            for (Rohstoff rohstoffKarte : kopieKarten)
                            {
                                if (rohstoff.equals(rohstoffKarte))
                                {
                                    spieler.removeKarte(rohstoff);
                                    this.besitzer.addKarte(rohstoff);
                                }
                            }
                        }
                    }
                    Entwicklung.addEntwicklung(this.entwicklung);
                    this.zeigeMenue();
                    break;
                case STRASSENBAU:
                    this.strasse1 = true;
                    new Info(this.besitzer + " spielt die Karte Strassenbau.");
                    this.besitzer.getSpiel().setNotSaveable();
                    this.besitzer.getSpiel().getSpielstart().getRootLayout().getRight().setVisible(false);
                    spielfeldController.setMessages(this.besitzer + " wählen Sie einen Bauplatz für Ihre erste Strasse.");
                    spielfeldController.addListener(this);
                    Entwicklung.addEntwicklung(this.entwicklung);
                    break;
                case ERFINDUNG:
                    this.besitzer.getSpiel().setNotSaveable();
                    new Info(this.besitzer + " spielt die Karte Erfindung.");
                    rohstoff = Rohstoffauswahl.getRohstoff();
                    this.besitzer.addKarte(rohstoff);
                    rohstoff = Rohstoffauswahl.getRohstoff();
                    this.besitzer.addKarte(rohstoff);
                    Entwicklung.addEntwicklung(this.entwicklung);
                    this.zeigeMenue();
                    break;
                case SIEGPUNKT:
                    break;
            }
            this.besitzer.setEntwicklungskarteGespielt();
            this.besitzer.getEntwickulungskarten().remove(this);
            return true;
        }
        else
        {
            new Error("Sie können keine weitere Entwicklung mehr spielen.");
            return false;
        }
    }

    /**
     * Baut eine Strasse zwischen den angegebenen Positionen positionen. Falls starsse1 = false wird this als Listener
     * des SpielfeldControllers entfernt.
     * 
     * @param positionen
     */
    private void strassenbau(Set<Position> positionen)
    {
        Boolean gebaut = this.besitzer.baueStrasse(positionen, false, true);
        if (gebaut)
        {
            SpielfeldController spielfeldController = this.besitzer.getSpiel().getSpielstart().getSpielfeldController();
            if (this.strasse1)
            {
                this.strasse1 = false;
                spielfeldController.setMessages(this.besitzer + " wählen Sie einen Bauplatz für Ihre zweite Strasse.");
            }
            else
            {
                spielfeldController.removeListener(this);
                spielfeldController.setMessages("");
                this.zeigeMenue();
            }
        }
    }

    private void zeigeMenue()
    {
        this.besitzer.getSpiel().setSaveable();
        if (this.besitzer.getSpiel().hatGewuerfelt())
        {
            this.besitzer.getSpiel().getMenue().zeigeZug();
        }
        else
        {
            this.besitzer.getSpiel().getMenue().zeigeWuerfel();
        }
    }

    private Entwicklung getZufaelligeEntwicklung()
    {
        Entwicklung entwicklung = Entwicklung.removeEntwicklung();
        return entwicklung;
    }

    public boolean getDarfGespieltWerden()
    {
        return this.darfGespieltWerden;
    }

    public void darfGespieltWerden()
    {
        this.darfGespieltWerden = true;
    }

    @Override
    public String toString()
    {
        return this.entwicklung.toString();
    }

    public Entwicklung getEntwicklung()
    {
        return this.entwicklung;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName() == "Kante")
        {
            this.strassenbau((Set<Position>) evt.getNewValue());
        }
    }

}
