package siedlervoncatan.utility;

public enum Pfade
{
    BAU_MENUE("view/fxml/BauMenue.fxml"), ENTWICKLUNGSKARTEN("view/fxml/Entwicklungskarten.fxml"), HANDEL_MENUE("view/fxml/HandelMenue.fxml"),
    HAUPT_MENUE("view/fxml/Hauptmenue.fxml"), KARTEN_ABGEBEN_MENUE("view/fxml/KartenAbgebenMenue.fxml"), NEUES_SPIEL_MENUE("view/fxml/NeuesSpielMenue.fxml"),
    ROOTLAYOUT("view/fxml/RootLayout.fxml"), SIEGER("view/fxml/Sieger.fxml"), SPIELER_ANLEGEN("view/fxml/SpielerAnlegen.fxml"),
    SPIELER_HANDEL_AUSWAHL("view/fxml/SpielerHandelAuswahl.fxml"), SPIELFELD("view/fxml/Spielfeld.fxml"), SPIEL_INFOS("view/fxml/SpielInfos.fxml"),
    WUERFEL_MENUE("view/fxml/WuerfelMenue.fxml"), ZUG_MENUE("view/fxml/ZugMenue.fxml"), STYLESHEET("siedlervoncatan/view/fxml/stylesheet.css"),
    LEERES_MENUE("view/fxml/LeeresMenue.fxml"), HOLZ("file:bilder/holz.png"), LEHM("file:bilder/lehm.png"), WOLLE("file:bilder/wolle.png"),
    KORN("file:bilder/korn.png"), ERZ("file:bilder/erz.png");

    private String pfad;

    private Pfade(String pfad)
    {
        this.pfad = pfad;
    }

    public String getPfad()
    {
        return this.pfad;
    }
}
