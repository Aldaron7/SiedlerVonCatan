package siedlervoncatan.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import siedlervoncatan.sound.Sound;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.view.Controller;

public class AudioController implements Controller
{
    @FXML
    private CheckBox             musikAnCB;
    @FXML
    private CheckBox             soundeffekteAnCB;
    @FXML
    private Slider               musikS;
    @FXML
    private Slider               soundeffekteS;

    private Sound                sound;
    private RootLayoutController layoutController;
    private Node                 self;
    private boolean              musikAn;
    private boolean              soundeffekteAn;
    private double               musikVolume;
    private double               soundeffekteVolume;

    public AudioController()
    {
        this.sound = Sound.getInstanz();
    }

    @FXML
    private void initialize()
    {
        this.musikAn = this.sound.getMusikAn();
        this.musikAnCB.setSelected(this.musikAn);
        this.soundeffekteAn = this.sound.getSoundeffekteAn();
        this.soundeffekteAnCB.setSelected(this.soundeffekteAn);
        this.musikVolume = this.sound.getMusikVolume();
        this.musikS.setValue(this.musikVolume);
        this.soundeffekteVolume = this.sound.getSoundeffekteVolume();
        this.soundeffekteS.setValue(this.soundeffekteVolume);
        this.musikAnCB.selectedProperty().addListener((obs, o, n) -> this.sound.setMusikAn(n));
        this.soundeffekteAnCB.selectedProperty().addListener((obs, o, n) -> this.sound.setSoundeffekteAn(n));
        this.musikS.valueProperty().addListener((obs, o, n) -> this.sound.changeMusikVolume(n.doubleValue()));
        this.soundeffekteS.valueProperty().addListener((obs, o, n) -> this.sound.changeSoundeffekteVolume(n.doubleValue()));
    }

    @FXML
    private void handleOK()
    {
        this.sound.playSoundeffekt(Sound.BUTTON_CLIP);
        this.layoutController.removeFromCenterAnimatedH(this.self);
    }

    @FXML
    private void handleAbbrechen()
    {
        this.sound.playSoundeffekt(Sound.BUTTON_CLIP);
        this.sound.setMusikAn(this.musikAn);
        this.sound.setSoundeffekteAn(this.soundeffekteAn);
        this.sound.changeMusikVolume(this.musikVolume);
        this.sound.changeSoundeffekteVolume(this.soundeffekteVolume);
        this.layoutController.removeFromCenterAnimatedH(this.self);
    }

    @Override
    public void setSpiel(Spiel spiel)
    {
    }

    @Override
    public void setLayoutController(RootLayoutController layoutController)
    {
        this.layoutController = layoutController;
    }

    @Override
    public void setNode(Node self)
    {
        this.self = self;
    }
}
