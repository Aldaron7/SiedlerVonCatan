package siedlervoncatan.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import siedlervoncatan.enums.Farbe;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.view.Controller;

/**
 * Controller zur Anzeige des SpielerAnlegen Menüs
 * 
 * @author mvr
 *
 */
public class SpielerAnlegenController implements Controller
{
    private Spiel                spiel;
    private Node                 self;
    private RootLayoutController layoutController;

    @FXML
    private TextField            name;
    @FXML
    private ComboBox<Farbe>      farbe;

    @FXML
    private void initialize()
    {
        this.farbe.getItems().addAll(Spiel.farben);
        this.name.requestFocus();
    }

    @FXML
    private void handleOK()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        Farbe farbe = this.farbe.getValue();
        Spiel.farben.remove(farbe);
        String name = this.name.getText().toString();
        if (name != "" && farbe != null)
        {
            Spieler spieler = new Spieler(name, farbe, this.spiel);
            this.spiel.addSpieler(spieler);
            this.layoutController.removeFromCenterAnimatedH(this.self);
        }
        else
        {
            this.spiel.getUserInterface().zeigeError("Ungültige Eingaben!");
        }

    }

    @FXML
    private void handleAbbrechen()
    {
        Sound.getInstanz().playSoundeffekt(Sound.BUTTON_CLIP);
        this.layoutController.removeFromCenterAnimatedH(this.self);
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
        this.spiel = spiel;
        this.name.requestFocus();
    }

    @Override
    public void setLayoutController(RootLayoutController layoutController)
    {
        this.layoutController = layoutController;
        this.name.requestFocus();
    }

    @Override
    public void setNode(Node self)
    {
        this.self = self;
        this.name.requestFocus();
    }
}
