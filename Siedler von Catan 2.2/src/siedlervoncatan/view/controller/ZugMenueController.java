package siedlervoncatan.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.view.Controller;

public class ZugMenueController implements Controller
{
    @FXML
    private ListView<Rohstoff> karten;
    @FXML
    private Label              spieler;
    @FXML
    private Tooltip            tooltipSpieler;

    private Spiel              spiel;

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        Spieler aktiverSpieler = this.spiel.getAktiverSpieler();
        this.spieler.setText(aktiverSpieler.toString());
        this.tooltipSpieler.setText(aktiverSpieler.getFarbe().toString());
        this.karten.setItems(aktiverSpieler.getKarten());
    }

    @FXML
    private void handleEntwicklungskarte()
    {
        this.spiel.getMenue().zeigeEntwicklungskarten();
    }

    @FXML
    private void handleBauen()
    {
        this.spiel.getMenue().zeigeBau();
    }

    @FXML
    private void handleSeehandel()
    {
        this.spiel.getAktiverSpieler().seehandel();
    }

    @FXML
    private void handleHandeln()
    {
        this.spiel.getMenue().zeigeHandel();
    }

    @FXML
    private void handleEnde()
    {
        this.spiel.getAktiverSpieler().erhoeheGespielteRunden();
        this.spiel.getAktiverSpieler().setNichtAktiv();
        this.spiel.naechsteRunde();
    }

    @Override
    public void setStage(Stage stage)
    {
    }
}
