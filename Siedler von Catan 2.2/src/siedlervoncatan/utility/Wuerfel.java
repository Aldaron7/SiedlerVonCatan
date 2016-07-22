package siedlervoncatan.utility;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Random;

/**
 * Ein W�rfelobjekt besteht aus zwei W6 W�rfeln deren Ergebnisse addiert werden. Es kann aber auch eine Zufallszahl
 * zwischen 1 und einem maxWert generiert werden.
 * 
 * @author mvr
 *
 */
public class Wuerfel implements Serializable
{
    private static final long   serialVersionUID = 1L;
    private static final Random RANDOM           = new Random();

    /**
     * Erzeugt eine Zufallszahl zwischen 1 und maxWert, einschlie�lich der Grenzen.
     * 
     * @param maxWert
     * @return die erzeugte Zuffalszahl.
     */
    public static int generiereZufallsZahl(int maxWert)
    {
        return Wuerfel.RANDOM.nextInt(maxWert) + 1;
    }

    private PropertyChangeSupport support;

    public Wuerfel()
    {
        this.support = new PropertyChangeSupport(this);
    }

    public void addListener(PropertyChangeListener listener)
    {
        this.support.addPropertyChangeListener(listener);
    }

    public void removeListener(PropertyChangeListener listener)
    {
        this.support.removePropertyChangeListener(listener);
    }

    /**
     * W�rfelt mit zwei W6ern und sendet die Summe der Ergebnisse.
     */
    public void wuerfeln()
    {
        int w1 = Wuerfel.generiereZufallsZahl(6);
        int w2 = Wuerfel.generiereZufallsZahl(6);
        int ergebnis = w1 + w2;
        this.support.firePropertyChange("wuerfeln", 0, ergebnis);
        this.support.firePropertyChange("wuerfel1", 0, w1);
        this.support.firePropertyChange("wuerfel2", 0, w2);
    }
}
