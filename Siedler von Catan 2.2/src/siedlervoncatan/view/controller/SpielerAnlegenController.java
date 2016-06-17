package siedlervoncatan.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import siedlervoncatan.enums.Farbe;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Error;
import siedlervoncatan.view.Controller;

public class SpielerAnlegenController implements Controller
{
    private Spiel           spiel;
    private Stage           stage;

    @FXML
    private TextField       name;
    @FXML
    private ComboBox<Farbe> farbe;

    @FXML
    private void initialize()
    {
        this.farbe.getItems().addAll(Spiel.farben);
    }

    @Override
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @FXML
    private void handleOK()
    {
        Farbe farbe = this.farbe.getValue();
        Spiel.farben.remove(farbe);
        String name = this.name.getText().toString();
        if (name != "" && farbe != null)
        {
            Spieler spieler = new Spieler(name, farbe, this.spiel);
            this.spiel.addSpieler(spieler);
            this.stage.close();
        }
        else
        {
            new Error("Ungültige Eingaben!");
        }

    }

    @FXML
    private void handleAbbrechen()
    {
        this.stage.close();
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
    }
}
