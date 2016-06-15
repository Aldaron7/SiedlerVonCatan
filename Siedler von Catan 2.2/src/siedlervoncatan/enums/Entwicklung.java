package siedlervoncatan.enums;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public enum Entwicklung
{
    RITTER("Wenn Sie diese Karte ausspielen, versetzen Sie den Räuber und ziehen bei einem der betroffenen Spieler eine Karte."),
    STRASSENBAU("Wenn Sie diese Karte ausspielen, dürfen Sie kostenlos zwei Strassen bauen."),
    ROHSTOFFMONOPOL("Wenn Sie diese Karte ausspielen, wählen Sie einen Rohstoff. Alle Spieler müssen Ihnen von diesem Rohstoff alle Karten geben, die sie besitzen."),
    ERFINDUNG("Wenn Sie diese Karte ausspielen, nehmen Sie sofort zwei Rohstoffkarten Ihrer Wahl vom Vorrat."), SIEGPUNKT("1 Siegpunkt");

    private static List<Entwicklung> stapel = new LinkedList<Entwicklung>();
    public static String             textRitter;
    public static String             textStrassenbau;
    public static String             textMonopol;
    public static String             textErfindung;
    public static String             textSiegpunkt;

    // erzeugt alle Entwicklungen mit der jeweiligen Anzahl und legt sie in den Stapel
    static
    {
        Entwicklung.textRitter = "Wenn Sie diese Karte ausspielen, versetzen Sie den Räuber und ziehen bei einem der betroffenen Spieler eine Karte.";
        Entwicklung.textStrassenbau = "Wenn Sie diese Karte ausspielen, dürfen Sie kostenlos zwei Strassen bauen.";
        Entwicklung.textMonopol = "Wenn Sie diese Karte ausspielen, wählen Sie einen Rohstoff. Alle Spieler müssen Ihnen von diesem Rohstoff alle Karten geben, die sie besitzen.";
        Entwicklung.textErfindung = "Wenn Sie diese Karte ausspielen, nehmen Sie sofort zwei Rohstoffkarten Ihrer Wahl vom Vorrat.";
        Entwicklung.textSiegpunkt = "1 Siegpunkt";
        for (int i = 0; i < 14; i++)
        {
            Entwicklung.stapel.add(Entwicklung.RITTER);
        }
        Entwicklung.stapel.add(Entwicklung.ERFINDUNG);
        Entwicklung.stapel.add(Entwicklung.ERFINDUNG);
        Entwicklung.stapel.add(Entwicklung.ROHSTOFFMONOPOL);
        Entwicklung.stapel.add(Entwicklung.ROHSTOFFMONOPOL);
        Entwicklung.stapel.add(Entwicklung.STRASSENBAU);
        Entwicklung.stapel.add(Entwicklung.STRASSENBAU);
        for (int i = 0; i < 5; i++)
        {
            Entwicklung.stapel.add(Entwicklung.SIEGPUNKT);
        }
        Collections.shuffle(Entwicklung.stapel);
    }

    private String text;

    private Entwicklung(String text)
    {
        this.text = text;
    }

    public static void addEntwicklung(Entwicklung entwicklung)
    {
        Entwicklung.stapel.add(entwicklung);
    }

    public static boolean istNichtLeer()
    {
        if (Entwicklung.stapel.size() > 0)
        {
            return true;
        }
        return false;
    }

    public static Entwicklung removeEntwicklung()
    {
        return Entwicklung.stapel.remove(0);

    }

    public String getText()
    {
        return this.text;
    }
}
