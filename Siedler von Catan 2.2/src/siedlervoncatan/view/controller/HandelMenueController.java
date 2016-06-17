package siedlervoncatan.view.controller;

import java.beans.PropertyChangeSupport;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.utility.Error;
import siedlervoncatan.utility.Handel;
import siedlervoncatan.view.Controller;

public class HandelMenueController implements Controller
{
    @FXML
    private TableView<Rohstoff>             angebotTable;
    @FXML
    private TableView<Rohstoff>             nachfrageTable;
    @FXML
    private TableColumn<Rohstoff, Rohstoff> angebotColumn;
    @FXML
    private TableColumn<Rohstoff, Rohstoff> nachfrageColumn;

    private Stage                           stage;
    private ObservableList<Rohstoff>        angebot;
    private ObservableList<Rohstoff>        nachfrage;
    private Spiel                           spiel;
    private PropertyChangeSupport           support;

    public HandelMenueController()
    {
        this.support = new PropertyChangeSupport(this);
        this.angebot = FXCollections.observableArrayList();
        this.nachfrage = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize()
    {
        this.angebotTable.setItems(this.angebot);
        this.nachfrageTable.setItems(this.nachfrage);
        this.angebotColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue()));
        this.nachfrageColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue()));
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
    }

    @FXML
    private void handleOK()
    {
        if (this.angebot.isEmpty() || this.nachfrage.isEmpty() || !this.spiel.getAktiverSpieler().getKarten().containsAll(this.angebot))
        {
            new Error("Ungültige Eingaben.");
        }
        else
        {
            Handel handel = new Handel(this.angebot, this.nachfrage);
            handel.setAnbieter(this.spiel.getAktiverSpieler());
            this.spiel.getMenue().zeigeSpielerHandel(handel);
            this.stage.close();
        }
    }

    @FXML
    private void handleEntfernen()
    {
        int selectedIndexAngebot = this.angebotTable.getSelectionModel().getSelectedIndex();
        int selectedIndexNachfrage = this.nachfrageTable.getSelectionModel().getSelectedIndex();
        if (selectedIndexAngebot >= 0)
        {
            this.angebotTable.getItems().remove(selectedIndexAngebot);
        }
        if (selectedIndexNachfrage >= 0)
        {
            this.nachfrageTable.getItems().remove(selectedIndexNachfrage);
        }
        if (selectedIndexAngebot < 0 && selectedIndexNachfrage < 0)
        {
            new Error("Kein Rohstoff ausgewählt.");
        }
        this.angebotTable.getSelectionModel().clearSelection();
        this.nachfrageTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleAbbrechen()
    {
        this.stage.close();
    }

    @FXML
    private void handleHolzA()
    {
        this.angebot.add(Rohstoff.HOLZ);
    }

    @FXML
    private void handleLehmA()
    {
        this.angebot.add(Rohstoff.LEHM);
    }

    @FXML
    private void handleWolleA()
    {
        this.angebot.add(Rohstoff.WOLLE);
    }

    @FXML
    private void handleKornA()
    {
        this.angebot.add(Rohstoff.KORN);
    }

    @FXML
    private void handleErzA()
    {
        this.angebot.add(Rohstoff.ERZ);
    }

    @FXML
    private void handleHolzN()
    {
        this.nachfrage.add(Rohstoff.HOLZ);
    }

    @FXML
    private void handleLehmN()
    {
        this.nachfrage.add(Rohstoff.LEHM);
    }

    @FXML
    private void handleWolleN()
    {
        this.nachfrage.add(Rohstoff.WOLLE);
    }

    @FXML
    private void handleKornN()
    {
        this.nachfrage.add(Rohstoff.KORN);
    }

    @FXML
    private void handleErzN()
    {
        this.nachfrage.add(Rohstoff.ERZ);
    }

}
