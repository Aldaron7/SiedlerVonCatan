package siedlervoncatan.view.controller;

import java.beans.PropertyChangeSupport;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.view.Controller;

public class AvatarController implements Controller
{
    @FXML
    private Label                 spielerL;
    @FXML
    private ImageView             avatarIV;

    private PropertyChangeSupport support;
    private Spieler               spieler;
    private Pane                  mouseOverPane;
    private Spiel                 spiel;

    public AvatarController()
    {
        this.support = new PropertyChangeSupport(this);
    }

    public void setSpieler(Spieler spieler)
    {
        this.spieler = spieler;
        this.spielerL.setText(spieler.getName());
        String farbe = spieler.getFarbe().toString().toLowerCase();
        Image image = new Image("file:bilder/avatar_" + farbe + ".png");
        this.avatarIV.setImage(image);
    }

    @FXML
    private void handleAvatarClicked()
    {
        this.support.firePropertyChange("Spieler", null, this.spieler);
    }

    @FXML
    private void handleInfosRequested()
    {
        this.mouseOverPane = this.spiel.getMenue().zeigeSpielerInfos(this.spieler);
        Timer timer = new Timer(3000, e -> Platform.runLater(() -> this.spiel.getMenue().removeFromCenterAnimatedH(this.mouseOverPane)));
        timer.setRepeats(false);
        timer.start();

    }

    @FXML
    private void handleMouseEntered()
    {
        this.mouseOverPane = this.spiel.getMenue().zeigeSpielerInfos(this.spieler);
    }

    @FXML
    private void handleMouseExited()
    {
        this.spiel.getMenue().removeFromCenterAnimatedH(this.mouseOverPane);
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        this.support.addPropertyChangeListener(spiel);
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
