package siedlervoncatan.view.controller;

import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Error;
import siedlervoncatan.view.Controller;

public class KartenAbgebenMenueController implements Controller
{
    @FXML
    private Label                    labelSpieler;
    @FXML
    private Tooltip                  tooltipSpieler;
    @FXML
    private Label                    text;
    @FXML
    private Label                    anzahlHolzKartenL;
    @FXML
    private Label                    anzahlLehmKartenL;
    @FXML
    private Label                    anzahlWolleKartenL;
    @FXML
    private Label                    anzahlKornKartenL;
    @FXML
    private Label                    anzahlErzKartenL;
    @FXML
    private Label                    gesamtAbgabeL;
    @FXML
    private Label                    anzahlHolzAbgabeL;
    @FXML
    private Label                    anzahlLehmAbgabeL;
    @FXML
    private Label                    anzahlWolleAbgabeL;
    @FXML
    private Label                    anzahlKornAbgabeL;
    @FXML
    private Label                    anzahlErzAbgabeL;

    private Spieler                  spieler;
    private Stage                    stage;
    private int                      anzahl;
    private ObservableList<Rohstoff> abgabe;
    private ObservableList<Rohstoff> karten;

    @Override
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void setSpieler(Spieler spieler)
    {
        this.spieler = spieler;
        this.labelSpieler.setText(spieler.toString());
        this.tooltipSpieler.setText(spieler.getFarbe().toString());
        this.karten = FXCollections.observableArrayList(spieler.getKarten());

        this.setAnzahlRohstoffe(spieler);

    }

    private void setAnzahlRohstoffe(Spieler spieler)
    {
        this.anzahlHolzKartenL.setText(this.getAnzahlAsString(spieler.getKarten(), Rohstoff.HOLZ));
        this.anzahlLehmKartenL.setText(this.getAnzahlAsString(spieler.getKarten(), Rohstoff.LEHM));
        this.anzahlWolleKartenL.setText(this.getAnzahlAsString(spieler.getKarten(), Rohstoff.WOLLE));
        this.anzahlKornKartenL.setText(this.getAnzahlAsString(spieler.getKarten(), Rohstoff.KORN));
        this.anzahlErzKartenL.setText(this.getAnzahlAsString(spieler.getKarten(), Rohstoff.ERZ));

        this.karten.addListener((ListChangeListener.Change<? extends Rohstoff> c) -> {
            while (c.next())
            {
                if (!c.wasPermutated())
                {
                    this.anzahlHolzKartenL.setText(this.getAnzahlAsString(c.getList(), Rohstoff.HOLZ));
                    this.anzahlLehmKartenL.setText(this.getAnzahlAsString(c.getList(), Rohstoff.LEHM));
                    this.anzahlWolleKartenL.setText(this.getAnzahlAsString(c.getList(), Rohstoff.WOLLE));
                    this.anzahlKornKartenL.setText(this.getAnzahlAsString(c.getList(), Rohstoff.KORN));
                    this.anzahlErzKartenL.setText(this.getAnzahlAsString(c.getList(), Rohstoff.ERZ));

                    this.anzahlHolzAbgabeL.setText(this.getAnzahlAsString(this.abgabe, Rohstoff.HOLZ));
                    this.anzahlLehmAbgabeL.setText(this.getAnzahlAsString(this.abgabe, Rohstoff.LEHM));
                    this.anzahlWolleAbgabeL.setText(this.getAnzahlAsString(this.abgabe, Rohstoff.WOLLE));
                    this.anzahlKornAbgabeL.setText(this.getAnzahlAsString(this.abgabe, Rohstoff.KORN));
                    this.anzahlErzAbgabeL.setText(this.getAnzahlAsString(this.abgabe, Rohstoff.ERZ));
                    this.gesamtAbgabeL.setText(Integer.toString(this.abgabe.size()));
                }
            }
        });
    }

    private String getAnzahlAsString(ObservableList<? extends Rohstoff> observableList, Rohstoff rohstoff)
    {
        return Integer.toString(Collections.frequency(observableList, rohstoff));
    }

    public void setAnzahl(int anzahl)
    {
        this.anzahl = anzahl;
        this.text.setText("Sie müssen " + anzahl + " Karten abgeben.");
        this.text.setWrapText(true);
    }

    @FXML
    private void initialize()
    {
        this.abgabe = FXCollections.observableArrayList();
    }

    @FXML
    private void handleOK()
    {
        // neue Liste damit beim remove nicht auf verschiedenen Threads sich die Liste ändert.
        if (this.abgabe.size() != this.anzahl)
        {
            new Error("Bitte wählen Sie " + this.anzahl + " Karten aus.");
        }
        else
        {
            this.spieler.removeKarten(this.abgabe);
            this.stage.close();
        }
    }

    @FXML
    private void handleKarteClicked(Event event)
    {
        Button button = (Button) event.getSource();
        String rohstoffString = button.getText();
        Rohstoff rohstoff = Rohstoff.getRohstoff(rohstoffString);
        if (this.karten.contains(rohstoff))
        {
            this.abgabe.add(rohstoff);
            this.karten.remove(rohstoff);
        }
    }

    @FXML
    private void handleAbgabeClicked(Event event)
    {
        Button button = (Button) event.getSource();
        String rohstoffString = button.getText();
        Rohstoff rohstoff = Rohstoff.getRohstoff(rohstoffString);
        if (this.abgabe.contains(rohstoff))
        {
            this.abgabe.remove(rohstoff);
            this.karten.add(rohstoff);
        }
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
    }

}
