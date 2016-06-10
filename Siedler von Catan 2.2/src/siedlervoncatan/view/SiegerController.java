package siedlervoncatan.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import siedlervoncatan.spiel.Spiel;

public class SiegerController
{
    @FXML
    private Label labelSpieler;

    private Spiel spiel;
    private Stage stage;

    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        this.labelSpieler.setText(spiel.getSieger().getName());
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private void handleNeu()
    {
        this.stage.close();
        this.spiel.getSpielstart().neuesSpiel();
    }

    @FXML
    private void handleEnde()
    {
        this.stage.close();
        System.exit(0);
    }

}
