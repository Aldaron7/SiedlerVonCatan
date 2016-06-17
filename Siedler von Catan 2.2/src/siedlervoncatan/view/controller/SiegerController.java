package siedlervoncatan.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.view.Controller;

public class SiegerController implements Controller
{
    @FXML
    private Label labelSpieler;

    private Spiel spiel;
    private Stage stage;

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        this.labelSpieler.setText(spiel.getSieger().getName());
    }

    @Override
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
