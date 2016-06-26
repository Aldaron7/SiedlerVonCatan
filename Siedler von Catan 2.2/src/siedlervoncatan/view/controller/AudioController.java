package siedlervoncatan.view.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import siedlervoncatan.Spielstart;
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

    @FXML
    private void handleApply()
    {
        this.sound.playSoundeffekt(Sound.BUTTON_CLIP);
        boolean musikAn = this.musikAnCB.isSelected();
        this.sound.setMusikAn(musikAn);
        boolean soundeffekteAn = this.soundeffekteAnCB.isSelected();
        this.sound.setSoundeffekteAn(soundeffekteAn);
        double musikVolume = this.musikS.getValue();
        this.sound.changeMusikVolume(musikVolume);
        double soundeffekteVolume = this.soundeffekteS.getValue();
        this.sound.changeSoundeffekteVolume(soundeffekteVolume);
    }

    @FXML
    private void handleOK()
    {
        this.sound.playSoundeffekt(Sound.BUTTON_CLIP);
        this.handleApply();
        this.layoutController.removeFromCenter(this.self);
    }

    @FXML
    private void handleAbbrechen()
    {
        this.sound.playSoundeffekt(Sound.BUTTON_CLIP);
        this.layoutController.removeFromCenter(this.self);
    }

    public void setSpielstart(Spielstart spielstart)
    {
        this.sound = spielstart.getSound();
        this.musikAnCB.setSelected(this.sound.getMusikAn());
        this.soundeffekteAnCB.setSelected(this.sound.getSoundeffekteAn());
        this.musikS.setValue(this.sound.getMusikVolume());
        this.soundeffekteS.setValue(this.sound.getSoundeffekteVolume());
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
