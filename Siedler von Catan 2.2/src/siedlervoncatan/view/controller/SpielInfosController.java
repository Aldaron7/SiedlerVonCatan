package siedlervoncatan.view.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.view.Controller;

/**
 * Controller für die Anzeige der SpielInfos, einem Container für die Spieleravatare
 * 
 * @author mvr
 *
 */
public class SpielInfosController implements Controller
{
    @FXML
    private VBox       spielerVB;
    @FXML
    private Group      groupG;
    @FXML
    private AnchorPane anchorP;

    @Override
    public void setSpiel(Spiel spiel)
    {
        ObservableList<Spieler> alleSpieler = spiel.getAlleSpieler();
        for (Spieler spieler : alleSpieler)
        {
            Pane pane = spiel.getUserInterface().zeigeAvatar(spieler);
            this.spielerVB.getChildren().add(pane);
        }
    }

    @Override
    public void setLayoutController(RootLayoutController layoutController)
    {
    }

    @Override
    public void setNode(Node self)
    {
    }

}
