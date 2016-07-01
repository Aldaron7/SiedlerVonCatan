package siedlervoncatan.view.controller;

import java.util.Collections;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.view.Controller;

public class ZugMenueController implements Controller
{
    @FXML
    private Label                spieler;
    @FXML
    private Tooltip              tooltipSpieler;
    @FXML
    private Label                anzahlHolzL;
    @FXML
    private Label                anzahlLehmL;
    @FXML
    private Label                anzahlWolleL;
    @FXML
    private Label                anzahlKornL;
    @FXML
    private Label                anzahlErzL;

    private Spiel                spiel;
    private Node                 self;
    private RootLayoutController layoutController;

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        Spieler aktiverSpieler = this.spiel.getAktiverSpieler();
        this.spieler.setText(aktiverSpieler.toString());
        this.tooltipSpieler.setText(aktiverSpieler.getFarbe().toString());

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

    @FXML
    private void handleEntwicklungskarte()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        this.layoutController.removeFromCenter(this.self);
        this.spiel.getUserInterface().zeigeEntwicklungskarten();
    }

    @FXML
    private void handleBauen()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        this.layoutController.removeFromCenterAnimatedV(this.self);
        Timer timer = new Timer(500, e -> Platform.runLater(() -> this.spiel.getUserInterface().zeigeBau()));
        timer.setRepeats(false);
        timer.start();
    }

    @FXML
    private void handleSeehandel()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        this.spiel.getAktiverSpieler().seehandel();
    }

    @FXML
    private void handleHandeln()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        this.layoutController.removeFromCenterAnimatedV(this.self);
        this.spiel.getUserInterface().zeigeHandel();
    }

    @FXML
    private void handleEnde()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        this.spiel.getAktiverSpieler().erhoeheGespielteRunden();
        this.spiel.getAktiverSpieler().setNichtAktiv();
        this.layoutController.removeFromCenterAnimatedV(this.self);
        Timer timer = new Timer(500, e -> Platform.runLater(() -> this.spiel.naechsteRunde()));
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
