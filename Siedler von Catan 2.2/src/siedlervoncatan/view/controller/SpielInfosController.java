package siedlervoncatan.view.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.view.Controller;

public class SpielInfosController implements Controller
{
    @FXML
    private VBox spielerVB;

    private Pane self;
    private int  anzahlSpieler;

    @Override
    public void setSpiel(Spiel spiel)
    {
        ObservableList<Spieler> alleSpieler = spiel.getAlleSpieler();
        for (Spieler spieler : alleSpieler)
        {
            Pane pane = spiel.getMenue().zeigeAvatar(spieler);
            this.spielerVB.getChildren().add(pane);
        }
        this.anzahlSpieler = alleSpieler.size();
    }

    @Override
    public void setLayoutController(RootLayoutController layoutController)
    {
    }

    @Override
    public void setNode(Node self)
    {
        this.self = (Pane) self;
        double maxHeight = this.anzahlSpieler * 150;
        this.self.setMaxHeight(maxHeight);
    }

}
