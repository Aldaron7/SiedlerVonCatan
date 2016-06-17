package siedlervoncatan.view.controller;

import java.beans.PropertyChangeSupport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Error;
import siedlervoncatan.utility.Handel;
import siedlervoncatan.view.Controller;

public class SpielerHandelAuswahlController implements Controller
{
    @FXML
    private ChoiceBox<Spieler>      spieler;
    @FXML
    private ListView<Rohstoff>      angebotListView;
    @FXML
    private ListView<Rohstoff>      nachfrageListView;

    private ObservableList<Spieler> andereSpieler;
    private PropertyChangeSupport   support;
    private Stage                   stage;
    private Spiel                   spiel;
    private Handel                  handel;

    public SpielerHandelAuswahlController()
    {
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        this.support.addPropertyChangeListener(spiel);
        this.andereSpieler = FXCollections.observableArrayList(spiel.getAlleSpieler());
        this.andereSpieler.remove(spiel.getAktiverSpieler());
        this.spieler.setItems(this.andereSpieler);
    }

    public void setAngebotNachfrage(Handel handel)
    {
        this.angebotListView.setItems(handel.getAngebot());
        this.nachfrageListView.setItems(handel.getNachfrage());
        this.handel = handel;
    }

    @FXML
    private void handleOK()
    {
        Spieler andererSpieler = this.spieler.getSelectionModel().getSelectedItem();

        if (andererSpieler != null)
        {
            if (andererSpieler.getKarten().containsAll(this.handel.getNachfrage()))
            {
                this.handel.setNachfrager(andererSpieler);
                this.handel.handeln();
                this.stage.close();
            }
            else
            {
                new Error(andererSpieler + " hat nicht genügend Rohstoffe in seinem Besitz.");
            }
        }
        else
        {
            new Error("Ungültige Auswahl.");
        }
    }

    @FXML
    private void handleAbbrechen()
    {
        this.stage.close();
    }

}
