package siedlervoncatan.view.controller;

import java.util.Collections;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.enums.Zustand;
import siedlervoncatan.io.UserInterface;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Baukosten;
import siedlervoncatan.view.Controller;

public class BauMenueController implements Controller
{
    @FXML
    private Label                    spieler;
    @FXML
    private Label                    anzahlHolzL;
    @FXML
    private Label                    anzahlLehmL;
    @FXML
    private Label                    anzahlWolleL;
    @FXML
    private Label                    anzahlKornL;
    @FXML
    private Label                    anzahlErzL;
    @FXML
    private ImageView                avatarIV;

    private Spiel                    spiel;
    private ObservableList<Rohstoff> karten;
    private UserInterface            ui;
    private Node                     self;
    private RootLayoutController     layoutController;

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        Spieler aktiverSpieler = spiel.getAktiverSpieler();
        this.spieler.setText(aktiverSpieler.toString());
        this.karten = aktiverSpieler.getKarten();
        this.ui = spiel.getUserInterface();

        String farbe = aktiverSpieler.getFarbe().toString().toLowerCase();
        Image image = new Image("file:bilder/avatar_" + farbe + ".png");
        this.avatarIV.setImage(image);

        this.setAnzahlRohstoffe(aktiverSpieler);
    }

    private void setAnzahlRohstoffe(Spieler spieler)
    {
        this.anzahlHolzL.setText(this.getAnzahlAsString(spieler.getKarten(), Rohstoff.HOLZ));
        this.anzahlLehmL.setText(this.getAnzahlAsString(spieler.getKarten(), Rohstoff.LEHM));
        this.anzahlWolleL.setText(this.getAnzahlAsString(spieler.getKarten(), Rohstoff.WOLLE));
        this.anzahlKornL.setText(this.getAnzahlAsString(spieler.getKarten(), Rohstoff.KORN));
        this.anzahlErzL.setText(this.getAnzahlAsString(spieler.getKarten(), Rohstoff.ERZ));

        spieler.getKarten().addListener((ListChangeListener.Change<? extends Rohstoff> c) -> {
            while (c.next())
            {
                if (!c.wasPermutated())
                {
                    this.anzahlHolzL.setText(this.getAnzahlAsString(c.getList(), Rohstoff.HOLZ));
                    this.anzahlLehmL.setText(this.getAnzahlAsString(c.getList(), Rohstoff.LEHM));
                    this.anzahlWolleL.setText(this.getAnzahlAsString(c.getList(), Rohstoff.WOLLE));
                    this.anzahlKornL.setText(this.getAnzahlAsString(c.getList(), Rohstoff.KORN));
                    this.anzahlErzL.setText(this.getAnzahlAsString(c.getList(), Rohstoff.ERZ));
                }
            }
        });
    }

    private String getAnzahlAsString(ObservableList<? extends Rohstoff> observableList, Rohstoff rohstoff)
    {
        return Integer.toString(Collections.frequency(observableList, rohstoff));
    }

    private void resetZustand()
    {
        this.spiel.setZustand(null);
        this.ui.zeigeMessage("");
    }

    @FXML
    private void handleStrasse()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        if (this.karten.containsAll(Baukosten.STRASSE))
        {
            this.ui.zeigeMessage(this.spiel.getAktiverSpieler() + " wählen Sie einen Bauplatz für ihre Strasse.");
            this.spiel.setZustand(Zustand.STARSSE_BAUEN);
        }
        else
        {
            this.ui.zeigeError("Sie haben nicht genug Rohstoffe um eine Strasse zu bauen.");
            this.resetZustand();
        }
    }

    @FXML
    private void handleSiedlung()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        if (this.karten.containsAll(Baukosten.SIEDLUNG))
        {
            this.ui.zeigeMessage(this.spiel.getAktiverSpieler() + " wählen Sie einen Bauplatz für ihre Siedlung.");
            this.spiel.setZustand(Zustand.SIEDLUNG_BAUEN);
        }
        else
        {
            this.ui.zeigeError("Sie haben nicht genug Rohstoffe um eine Siedlung zu bauen.");
            this.resetZustand();
        }
    }

    @FXML
    private void handleStadt()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        if (this.karten.containsAll(Baukosten.STADT))
        {
            this.ui.zeigeMessage(this.spiel.getAktiverSpieler() + " wählen Sie einen Bauplatz für ihre Stadt.");
            this.spiel.setZustand(Zustand.STADT_BAUEN);
        }
        else
        {
            this.ui.zeigeError("Sie haben nicht genug Rohstoffe um eine Stadt zu bauen.");
            this.resetZustand();
        }
    }

    @FXML
    private void handleEntwicklung()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        if (this.karten.containsAll(Baukosten.ENTWICKLUNGSKARTE))
        {
            this.spiel.entwicklungKaufen();
        }
        else
        {
            this.ui.zeigeError("Sie haben nicht genug Rohstoffe um eine Entwicklungskarte zu kaufen.");
            this.resetZustand();
        }
    }

    @FXML
    private void handleAbbrechen()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        this.resetZustand();
        this.layoutController.removeFromCenterAnimatedV(this.self);
        Timer timer = new Timer(500, e -> Platform.runLater(() -> this.ui.zeigeZug()));
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void setLayoutController(RootLayoutController layoutController)
    {
        this.layoutController = layoutController;
    }

    @Override
    public void setNode(Node self)
    {
        this.self = self;
    }

}
