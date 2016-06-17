package siedlervoncatan.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.enums.Zustand;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.view.Controller;

public class BauMenueController implements Controller
{
    @FXML
    private ListView<Rohstoff>  karten;
    @FXML
    private VBox                bauButtons;
    @FXML
    private Label               spieler;
    @FXML
    private Tooltip             tooltipSpieler;

    private Spiel               spiel;
    private SpielfeldController controller;

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        this.spieler.setText(spiel.getAktiverSpieler().toString());
        this.tooltipSpieler.setText(spiel.getAktiverSpieler().getFarbe().toString());
        this.karten.setItems(spiel.getAktiverSpieler().getKarten());
        this.controller = spiel.getSpielstart().getSpielfeldController();
        this.bauButtons.setVisible(true);
    }

    @FXML
    private void handleStrasse()
    {
        this.controller.setMessages(this.spiel.getAktiverSpieler() + " wählen Sie einen Bauplatz für ihre Strasse.");
        this.spiel.setZustand(Zustand.STARSSE_BAUEN);
        this.bauButtons.setVisible(false);
    }

    @FXML
    private void handleSiedlung()
    {
        this.controller.setMessages(this.spiel.getAktiverSpieler() + " wählen Sie einen Bauplatz für ihre Siedlung.");
        this.spiel.setZustand(Zustand.SIEDLUNG_BAUEN);
        this.bauButtons.setVisible(false);
    }

    @FXML
    private void handleStadt()
    {
        this.controller.setMessages(this.spiel.getAktiverSpieler() + " wählen Sie einen Bauplatz für ihre Stadt.");
        this.spiel.setZustand(Zustand.STADT_BAUEN);
        this.bauButtons.setVisible(false);
    }

    @FXML
    private void handleEntwicklung()
    {
        this.spiel.entwicklungKaufen();
    }

    @FXML
    private void handleAbbrechen()
    {
        this.spiel.setZustand(null);
        this.spiel.getMenue().zeigeZug();
    }

    @Override
    public void setStage(Stage stage)
    {
    }

}
