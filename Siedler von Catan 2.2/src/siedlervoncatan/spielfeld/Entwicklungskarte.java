package siedlervoncatan.spielfeld;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import siedlervoncatan.enums.Entwicklung;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.io.UserInterface;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Position;

/**
 * Eine Entwicklungskarte enthält ihren Besitzer, ihre Art und ob sie ausgespielt werden darf. Verwaltet das Ausspielen
 * von Entwicklungskarten.
 * 
 * @author mvr
 *
 */
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
     * Führt die Aktion entsprechend der Art der Entwicklung aus. Bei einem Siegpunkt werden diese erhöht, bei einem
     * Ritter wird der Räuber versetzt und bei einem angrenzenden Spieler gezogen, bei einem Rohstoffmonopol werden alle
     * Rohstoffe des gewählten Typs auf den Spieler übertragen, bei einem Strassenbau dürfen zwei Strassen gebaut werden
     * und bei einer Erfindung zwei Rohstoffe gezogen werden.
     * 
     * @return true, wenn die Aktion ausgeführt wurde.
     */
    public boolean ausspielen()
    {
        if (this.entwicklung == Entwicklung.SIEGPUNKT)
        {
            this.besitzer.getSpiel().getUserInterface().zeigeInfo(this.besitzer + " spielt die karte Siegpunkt.");
            this.besitzer.erhoeheSiegpunkte();
            this.besitzer.getEntwickulungskarten().remove(this);
            this.zeigeMenue();
            return true;
        }
        if (!this.besitzer.entwicklungskarteGespielt())
        {
            UserInterface userInterface = this.besitzer.getSpiel().getUserInterface();
            switch (this.entwicklung)
            {
                case RITTER:
                    this.besitzer.getSpiel().getUserInterface().zeigeInfo(this.besitzer + " spielt die Karte Ritter.");
                    this.besitzer.versetzeRauber();
                    this.besitzer.addRitter();
                    break;
                case ROHSTOFFMONOPOL:
                    Rohstoff rohstoff = userInterface.zeigeRohstoffauswahl(this.besitzer + " wählen Sie einen Rohstoff.");
                    this.besitzer.getSpiel().getUserInterface()
                                    .zeigeInfo(this.besitzer + " spielt die Karte Rohstoffmonopol und bekommt alles " + rohstoff + ".");
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
                    this.besitzer.getSpiel().getUserInterface().zeigeInfo(this.besitzer + " spielt die Karte Strassenbau.");
                    this.besitzer.getSpiel().setNotSaveable();
                    userInterface.zeigeMessage(this.besitzer + " wählen Sie einen Bauplatz für Ihre erste Strasse.");
                    userInterface.getSpielfeldController().addListener(this);
                    Entwicklung.addEntwicklung(this.entwicklung);
                    break;
                case ERFINDUNG:
                    this.besitzer.getSpiel().setNotSaveable();
                    this.besitzer.getSpiel().getUserInterface().zeigeInfo(this.besitzer + " spielt die Karte Erfindung.");
                    rohstoff = userInterface.zeigeRohstoffauswahl(this.besitzer + " wählen Sie einen Rohstoff.");
                    this.besitzer.addKarte(rohstoff);
                    rohstoff = userInterface.zeigeRohstoffauswahl(this.besitzer + " wählen Sie einen Rohstoff.");
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
            this.besitzer.getSpiel().getUserInterface().zeigeError("Sie können keine weitere Entwicklung mehr spielen.");
            return false;
        }
    }

    /**
     * Baut eine Strasse zwischen den angegebenen Positionen positionen. Falls starsse1 = false wird this als Listener
     * des SpielfeldControllers entfernt, damit keine weitere Strasse gebaut werden kann.
     * 
     * @param positionen
     */
    private void strassenbau(Set<Position> positionen)
    {
        Boolean gebaut = this.besitzer.baueStrasse(positionen, false, true);
        if (gebaut)
        {
            UserInterface userInterface = this.besitzer.getSpiel().getUserInterface();
            if (this.strasse1)
            {
                this.strasse1 = false;
                userInterface.zeigeMessage(this.besitzer + " wählen Sie einen Bauplatz für Ihre zweite Strasse.");
            }
            else
            {
                userInterface.getSpielfeldController().removeListener(this);
                userInterface.zeigeMessage("");
                this.zeigeMenue();
            }
        }
    }

    /**
     * Zeigt das weiterführende Menü je nachdem ob der Spieler bereits gewürfelt hat.
     */
    private void zeigeMenue()
    {
        this.besitzer.getSpiel().setSaveable();
        if (this.besitzer.getSpiel().hatGewuerfelt())
        {
            this.besitzer.getSpiel().getUserInterface().zeigeZug();
        }
        else
        {
            this.besitzer.getSpiel().getUserInterface().zeigeWuerfel();
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

    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName() == "Kante")
        {
            this.strassenbau((Set<Position>) evt.getNewValue());
        }
    }

}
