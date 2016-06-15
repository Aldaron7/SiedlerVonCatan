package siedlervoncatan.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spielfeld.Entwicklungskarte;
import siedlervoncatan.utility.Error;

public class EntwicklungskartenController
{
    @FXML
    private ListView<Entwicklungskarte> entwicklungskarten;
    @FXML
    private Text                        text;
    @FXML
    private Label                       entwicklung;

    private Stage                       stage;

    public void setSpiel(Spiel spiel)
    {
        this.entwicklungskarten.setItems(spiel.getAktiverSpieler().getEntwickulungskarten());
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
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
        Entwicklungskarte selectedItem = this.entwicklungskarten.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.getDarfGespieltWerden())
        {
            selectedItem.ausspielen();
            this.stage.close();
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
