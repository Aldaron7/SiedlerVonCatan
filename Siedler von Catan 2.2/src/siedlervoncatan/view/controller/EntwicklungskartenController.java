package siedlervoncatan.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spielfeld.Entwicklungskarte;
import siedlervoncatan.utility.Error;
import siedlervoncatan.view.Controller;

public class EntwicklungskartenController implements Controller
{
    @FXML
    private ListView<Entwicklungskarte> entwicklungskarten;
    @FXML
    private Text                        text;
    @FXML
    private Label                       entwicklung;

    private Node                        self;
    private RootLayoutController        layoutController;
    private Spiel                       spiel;

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        this.entwicklungskarten.setItems(spiel.getAktiverSpieler().getEntwickulungskarten());
    }

    @FXML
    private void initialize()
    {
        this.entwicklungskarten.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> this.zeigeText(newValue));
        this.entwicklung.setText("");
        this.text.setText("");
    }

    private void zeigeText(Entwicklungskarte karte)
    {
        if (karte != null)
        {
            this.entwicklung.setText(karte.toString());
            this.text.setText(karte.getEntwicklung().getText());
        }
    }

    @FXML
    private void handleSpielen()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        Entwicklungskarte selectedItem = this.entwicklungskarten.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.getDarfGespieltWerden())
        {
            this.layoutController.removeFromCenterAnimatedH(this.self);
            selectedItem.ausspielen();
        }
        else
        {
            new Error("Ungültige Auswahl.");
        }
    }

    @FXML
    private void handleAbbrechen()
    {
        this.spiel.getSound().playSoundeffekt(Sound.BUTTON_CLIP);
        this.layoutController.removeFromCenterAnimatedH(this.self);
        if (this.spiel.hatGewuerfelt())
        {
            this.spiel.getMenue().zeigeZug();
        }
        else
        {
            this.spiel.getMenue().zeigeWuerfel();
        }
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
