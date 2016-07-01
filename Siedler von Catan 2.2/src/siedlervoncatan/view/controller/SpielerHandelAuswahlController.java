package siedlervoncatan.view.controller;

import java.beans.PropertyChangeSupport;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.sound.Sound;
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
    private Handel                  handel;
    private Spiel                   spiel;
    private Node                    self;
    private RootLayoutController    layoutController;

    public SpielerHandelAuswahlController()
    {
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
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
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        Spieler andererSpieler = this.spielerCB.getSelectionModel().getSelectedItem();

        if (andererSpieler != null)
        {
            if (andererSpieler.getKarten().containsAll(this.handel.getNachfrage()))
            {
                this.handel.setNachfrager(andererSpieler);
                this.handel.handeln();
                Sound.getInstanz().playMusik(Sound.MUSIK_MEER);
                this.layoutController.removeFromCenterAnimatedH(this.self);
                this.spiel.getUserInterface().zeigeZug();
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
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        Sound.getInstanz().playMusik(Sound.MUSIK_MEER);
        this.layoutController.removeFromCenterAnimatedH(this.self);
        this.spiel.getUserInterface().zeigeZug();
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
