package siedlervoncatan.view.controller;

import java.beans.PropertyChangeSupport;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.utility.Error;
import siedlervoncatan.utility.Handel;
import siedlervoncatan.view.Controller;

public class HandelMenueController implements Controller
{
    @FXML
    private TableView<Rohstoff>         angebotTable;
    @FXML
    private TableView<Rohstoff>         nachfrageTable;
    @FXML
    private TableColumn<Rohstoff, HBox> angebotColumn;
    @FXML
    private TableColumn<Rohstoff, HBox> nachfrageColumn;

    private ObservableList<Rohstoff>    angebot;
    private ObservableList<Rohstoff>    nachfrage;
    private Spiel                       spiel;
    private Node                        self;
    private RootLayoutController        layoutController;
    private PropertyChangeSupport       support;

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

        this.angebotColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(this.createColumnInlay(c.getValue())));
        this.nachfrageColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(this.createColumnInlay(c.getValue())));

        this.angebot.addListener((ListChangeListener.Change<? extends Rohstoff> c) -> {
            while (c.next())
            {
                if (c.wasAdded())
                {
                    this.angebot.sort(null);
                }
            }
        });
        this.nachfrage.addListener((ListChangeListener.Change<? extends Rohstoff> c) -> {
            while (c.next())
            {
                if (c.wasAdded())
                {
                    this.nachfrage.sort(null);
                }
            }
        });
    }

    private HBox createColumnInlay(Rohstoff rohstoff)
    {
        Image image = rohstoff.getImage();
        Label rohstoffL = new Label(rohstoff.toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(35);
        imageView.setFitWidth(35);
        HBox hBox = new HBox(5);
        hBox.getChildren().add(imageView);
        hBox.getChildren().add(rohstoffL);
        return hBox;
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        this.support.addPropertyChangeListener(spiel);
        spiel.getSound().playMusik(Sound.MUSIK_HANDEL);
    }

    @FXML
    private void handleOK()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        if (this.angebot.isEmpty() || this.nachfrage.isEmpty() || !this.spiel.getAktiverSpieler().getKarten().containsAll(this.angebot))
        {
            new Error("Ungültige Eingaben.");
        }
        else
        {
            Handel handel = new Handel(this.angebot, this.nachfrage);
            handel.setAnbieter(this.spiel.getAktiverSpieler());
            this.spiel.getMenue().zeigeSpielerHandel(handel);
            this.layoutController.removeFromCenter(this.self);
        }
    }

    @FXML
    private void handleEntfernen()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
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
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        this.spiel.getSound().playMusik(Sound.MUSIK_MEER);
        this.layoutController.removeFromCenter(this.self);
        this.spiel.getMenue().zeigeZug();
    }

    @FXML
    private void handleHolzA()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        this.angebot.add(Rohstoff.HOLZ);
    }

    @FXML
    private void handleLehmA()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        this.angebot.add(Rohstoff.LEHM);
    }

    @FXML
    private void handleWolleA()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        this.angebot.add(Rohstoff.WOLLE);
    }

    @FXML
    private void handleKornA()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        this.angebot.add(Rohstoff.KORN);
    }

    @FXML
    private void handleErzA()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        this.angebot.add(Rohstoff.ERZ);
    }

    @FXML
    private void handleHolzN()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        this.nachfrage.add(Rohstoff.HOLZ);
    }

    @FXML
    private void handleLehmN()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        this.nachfrage.add(Rohstoff.LEHM);
    }

    @FXML
    private void handleWolleN()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        this.nachfrage.add(Rohstoff.WOLLE);
    }

    @FXML
    private void handleKornN()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        this.nachfrage.add(Rohstoff.KORN);
    }

    @FXML
    private void handleErzN()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        this.nachfrage.add(Rohstoff.ERZ);
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
