package siedlervoncatan.view;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;

public class SpielInfosController
{
    @FXML
    private Label                 spieler1;
    @FXML
    private Label                 spieler2;
    @FXML
    private Label                 spieler3;
    @FXML
    private Label                 spieler4;

    @FXML
    private Tooltip               tooltip1;
    @FXML
    private Tooltip               tooltip2;
    @FXML
    private Tooltip               tooltip3;
    @FXML
    private Tooltip               tooltip4;

    @FXML
    private Label                 siegpunkte1;
    @FXML
    private Label                 siegpunkte2;
    @FXML
    private Label                 siegpunkte3;
    @FXML
    private Label                 siegpunkte4;

    @FXML
    private Label                 karten1;
    @FXML
    private Label                 karten2;
    @FXML
    private Label                 karten3;
    @FXML
    private Label                 karten4;

    @FXML
    private Label                 ritter1;
    @FXML
    private Label                 ritter2;
    @FXML
    private Label                 ritter3;
    @FXML
    private Label                 ritter4;

    @FXML
    private GridPane              pane1;
    @FXML
    private GridPane              pane2;
    @FXML
    private GridPane              pane3;
    @FXML
    private GridPane              pane4;

    private Spiel                 spiel;
    private List<GridPane>        panes;
    private List<Label>           spieler;
    private List<Tooltip>         tooltips;
    private List<Label>           siegpunkte;
    private List<Label>           karten;
    private List<Label>           ritter;
    private PropertyChangeSupport support;

    public SpielInfosController()
    {
        this.siegpunkte = new ArrayList<>();
        this.karten = new ArrayList<>();
        this.ritter = new ArrayList<>();
        this.spieler = new ArrayList<>();
        this.tooltips = new ArrayList<>();
        this.panes = new ArrayList<>();
        this.support = new PropertyChangeSupport(this);
    }

    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        this.support.addPropertyChangeListener(spiel);

        this.siegpunkte.add(this.siegpunkte1);
        this.siegpunkte.add(this.siegpunkte2);
        this.siegpunkte.add(this.siegpunkte3);
        this.siegpunkte.add(this.siegpunkte4);

        this.karten.add(this.karten1);
        this.karten.add(this.karten2);
        this.karten.add(this.karten3);
        this.karten.add(this.karten4);

        this.ritter.add(this.ritter1);
        this.ritter.add(this.ritter2);
        this.ritter.add(this.ritter3);
        this.ritter.add(this.ritter4);

        this.panes.add(this.pane1);
        this.panes.add(this.pane2);
        this.panes.add(this.pane3);
        this.panes.add(this.pane4);

        this.spieler.add(this.spieler1);
        this.spieler.add(this.spieler2);
        this.spieler.add(this.spieler3);
        this.spieler.add(this.spieler4);

        this.tooltips.add(this.tooltip1);
        this.tooltips.add(this.tooltip2);
        this.tooltips.add(this.tooltip3);
        this.tooltips.add(this.tooltip4);

        if (spiel != null)
        {
            List<Spieler> alleSpieler = spiel.getAlleSpieler();
            if (!alleSpieler.isEmpty())
            {
                for (int i = 0; i < alleSpieler.size(); i++)
                {
                    Spieler spieler = alleSpieler.get(i);

                    this.siegpunkte.get(i).textProperty().bind(spieler.getSiegpunkte().asString());
                    this.karten.get(i).textProperty().bind(spieler.getAnzahlKarten().asString());
                    this.ritter.get(i).textProperty().bind(spieler.getRitter().asString());
                    this.spieler.get(i).setText(spieler.toString());
                    this.tooltips.get(i).setText(spieler.getFarbe().toString());
                    this.panes.get(i).setVisible(true);
                }
            }
        }
    }

    @FXML
    private void handlePane1Clicked()
    {
        if (this.spiel.getAlleSpieler().size() > 0)
        {
            Spieler spieler = this.spiel.getAlleSpieler().get(0);
            this.support.firePropertyChange("Spieler", null, spieler);
        }
    }

    @FXML
    private void handlePane2Clicked()
    {
        if (this.spiel.getAlleSpieler().size() > 1)
        {
            Spieler spieler = this.spiel.getAlleSpieler().get(1);
            this.support.firePropertyChange("Spieler", null, spieler);
        }
    }

    @FXML
    private void handlePane3Clicked()
    {
        if (this.spiel.getAlleSpieler().size() > 2)
        {
            Spieler spieler = this.spiel.getAlleSpieler().get(2);
            this.support.firePropertyChange("Spieler", null, spieler);
        }
    }

    @FXML
    private void handlePane4Clicked()
    {
        if (this.spiel.getAlleSpieler().size() > 3)
        {
            Spieler spieler = this.spiel.getAlleSpieler().get(3);
            this.support.firePropertyChange("Spieler", null, spieler);
        }
    }
}
