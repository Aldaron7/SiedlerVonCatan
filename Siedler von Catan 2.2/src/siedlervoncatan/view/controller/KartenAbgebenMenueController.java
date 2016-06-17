package siedlervoncatan.view.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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
    private Label              labelSpieler;
    @FXML
    private Tooltip            tooltipSpieler;
    @FXML
    private ListView<Rohstoff> karten;
    @FXML
    private Label              text;
    private Spieler            spieler;
    private Stage              stage;
    private int                anzahl;

    @Override
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void setSpieler(Spieler spieler)
    {
        this.spieler = spieler;
        this.karten.setItems(spieler.getKarten());
        this.karten.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.labelSpieler.setText(spieler.toString());
        this.tooltipSpieler.setText(spieler.getFarbe().toString());
    }

    public void setAnzahl(int anzahl)
    {
        this.anzahl = anzahl;
        this.text.setText("Bitte wählen Sie " + anzahl + " Rohstoffe \naus, die Sie abgeben möchten.");
        this.text.setWrapText(true);
    }

    @FXML
    private void handleOK()
    {
        // neue Liste damit beim remove nicht auf verschiedenen Threads sich die Liste ändert.
        List<Rohstoff> rohstoffe = new ArrayList<>(this.karten.getSelectionModel().getSelectedItems());
        if (rohstoffe.size() != this.anzahl)
        {
            new Error("Bitte wählen Sie " + this.anzahl + " Rohstoffe aus.");
        }
        else
        {
            this.spieler.removeKarten(rohstoffe);
            this.stage.close();
        }
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
    }

}
