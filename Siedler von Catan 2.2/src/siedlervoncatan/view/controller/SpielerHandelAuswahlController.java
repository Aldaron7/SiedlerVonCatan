package siedlervoncatan.view.controller;

import java.beans.PropertyChangeSupport;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    private ChoiceBox<Spieler>      spielerCB;
    @FXML
    private ListView<HBox>          angebotLV;
    @FXML
    private ListView<HBox>          nachfrageLV;
    @FXML
    private Label                   textL;

    private ObservableList<Spieler> andereSpieler;
    private PropertyChangeSupport   support;
    private Stage                   stage;
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
        this.support.addPropertyChangeListener(spiel);
        this.andereSpieler = FXCollections.observableArrayList(spiel.getAlleSpieler());
        this.andereSpieler.remove(spiel.getAktiverSpieler());
        this.spielerCB.setItems(this.andereSpieler);
        this.textL.setText("Wähle den Mitspieler der mit " + spiel.getAktiverSpieler() + " handeln möchte.");
    }

    public void setAngebotNachfrage(Handel handel)
    {
        this.angebotLV.setItems(this.createListViewInlay(handel.getAngebot()));
        this.nachfrageLV.setItems(this.createListViewInlay(handel.getNachfrage()));
        this.handel = handel;
    }

    private ObservableList<HBox> createListViewInlay(List<Rohstoff> rohstoffe)
    {
        ObservableList<HBox> listHBox = FXCollections.observableArrayList();
        for (Rohstoff rohstoff : rohstoffe)
        {
            Label label = new Label(rohstoff.toString());
            ImageView imageView = new ImageView(rohstoff.getImage());
            imageView.setFitHeight(35);
            imageView.setFitWidth(35);
            HBox hBox = new HBox(5);
            hBox.getChildren().addAll(imageView, label);
            listHBox.add(hBox);
        }
        return listHBox;
    }

    @FXML
    private void handleOK()
    {
        Spieler andererSpieler = this.spielerCB.getSelectionModel().getSelectedItem();

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
