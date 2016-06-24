package siedlervoncatan.sounds;

import java.io.File;

import javafx.scene.media.AudioClip;

public class Sound
{
    private static final AudioClip MUSIK_MENUE        = new AudioClip(new File("sounds/menue.wav").toURI().toString());
    private static final AudioClip MUSIK_MEER         = new AudioClip(new File("sounds/meer.wav").toURI().toString());
    private static final AudioClip MUSIK_HANDEL       = new AudioClip(new File("sounds/handel.wav").toURI().toString());
    private static final AudioClip WUERFEL_CLIP       = new AudioClip(new File("sounds/dice.wav").toURI().toString());
    private static final AudioClip WUERFEL_SHAKE_CLIP = new AudioClip(new File("sounds/diceshake.wav").toURI().toString());
    private static final AudioClip BAU_CLIP           = new AudioClip(new File("sounds/bauen.wav").toURI().toString());
    private static final AudioClip BUTTON_CLIP        = new AudioClip(new File("sounds/button.wav").toURI().toString());
    private static final AudioClip ERROR_CLIP         = new AudioClip(new File("sounds/error.wav").toURI().toString());
    private static final AudioClip SIEGER_CLIP        = new AudioClip(new File("sounds/sieger.wav").toURI().toString());
    private static final AudioClip EVIL_LAUGH_CLIP    = new AudioClip(new File("sounds/evilLaugh.wav").toURI().toString());

    private boolean                musikAn            = true;
    private boolean                soundeffekteAn     = true;

    public void playMenue()
    {
        if (this.soundeffekteAn)
        {
            Sound.MUSIK_MENUE.play();
        }
    }

    public void playMeer()
    {
        if (this.musikAn)
        {
            Sound.MUSIK_MEER.play();
        }
    }

    public void playHandel()
    {
        if (this.musikAn)
        {
            Sound.MUSIK_HANDEL.play();
        }
    }

    public void playWuerfel()
    {
        if (this.soundeffekteAn)
        {
            Sound.WUERFEL_CLIP.play();
        }
    }

    public void playWuerfelShake()
    {
        if (this.soundeffekteAn)
        {
            Sound.WUERFEL_SHAKE_CLIP.play();
        }
    }

    public void playBauen()
    {
        if (this.soundeffekteAn)
        {
            Sound.BAU_CLIP.play();
        }
    }

    public void playButton()
    {
        if (this.soundeffekteAn)
        {
            Sound.BUTTON_CLIP.play();
        }
    }

    public void playError()
    {
        if (this.soundeffekteAn)
        {
            Sound.ERROR_CLIP.play();
        }
    }

    public void playEvilLaugh()
    {
        if (this.soundeffekteAn)
        {
            Sound.EVIL_LAUGH_CLIP.play();
        }
    }

    public void playSieger()
    {
        if (this.soundeffekteAn)
        {
            Sound.SIEGER_CLIP.play();
        }
    }

    public void musikAus()
    {
        this.musikAn = false;
    }

    public void musikAn()
    {
        this.musikAn = true;
    }

    public boolean getMusikAn()
    {
        return this.musikAn;
    }

    public void soundeffekteAus()
    {
        this.soundeffekteAn = false;
    }

    public void soundeffekteAn()
    {
        this.soundeffekteAn = true;
    }

    public boolean getSoundeffekteAn()
    {
        return this.soundeffekteAn;
    }
}
