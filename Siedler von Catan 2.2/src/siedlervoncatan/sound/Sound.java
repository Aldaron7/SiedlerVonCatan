package siedlervoncatan.sound;

import java.io.File;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound
{
    public static final MediaPlayer MUSIK_MENUE        = new MediaPlayer(new Media(new File("sounds/menue.wav").toURI().toString()));
    public static final MediaPlayer MUSIK_MEER         = new MediaPlayer(new Media(new File("sounds/island.wav").toURI().toString()));
    public static final MediaPlayer MUSIK_HANDEL       = new MediaPlayer(new Media(new File("sounds/handel.wav").toURI().toString()));
    public static final AudioClip   WUERFEL_CLIP       = new AudioClip(new File("sounds/dice.wav").toURI().toString());
    public static final AudioClip   WUERFEL_SHAKE_CLIP = new AudioClip(new File("sounds/diceshake.wav").toURI().toString());
    public static final AudioClip   BAU_CLIP           = new AudioClip(new File("sounds/bauen.wav").toURI().toString());
    public static final AudioClip   BUTTON_CLIP        = new AudioClip(new File("sounds/click.wav").toURI().toString());
    public static final AudioClip   ERROR_CLIP         = new AudioClip(new File("sounds/error.wav").toURI().toString());
    public static final AudioClip   SIEGER_CLIP        = new AudioClip(new File("sounds/sieger.wav").toURI().toString());
    public static final AudioClip   EVIL_LAUGH_CLIP    = new AudioClip(new File("sounds/evilLaugh.wav").toURI().toString());

    private static Sound            SOUND;

    static
    {
        Sound.MUSIK_HANDEL.setCycleCount(MediaPlayer.INDEFINITE);
        Sound.MUSIK_MEER.setCycleCount(MediaPlayer.INDEFINITE);
        Sound.MUSIK_MENUE.setCycleCount(MediaPlayer.INDEFINITE);
    }

    private boolean     musikAn;
    private boolean     soundeffekteAn;
    private double      musikVolume;
    private double      soundeffekteVolume;
    private MediaPlayer musik;
    private AudioClip   soundeffekt;

    public static Sound getInstanz()
    {
        if (Sound.SOUND == null)
        {
            Sound.SOUND = new Sound();
        }
        return Sound.SOUND;
    }

    private Sound()
    {
        this.musikAn = true;
        this.soundeffekteAn = true;
        this.musikVolume = 0.5;
        this.soundeffekteVolume = 1.0;
    }

    public void changeMusikVolume(double newVolume)
    {
        if (this.musik != null)
        {
            this.musik.setVolume(newVolume);
            this.musikVolume = newVolume;
        }
    }

    public double getMusikVolume()
    {
        return this.musikVolume;
    }

    public double getSoundeffekteVolume()
    {
        return this.soundeffekteVolume;
    }

    public void changeSoundeffekteVolume(double newVolume)
    {
        if (this.soundeffekt != null)
        {
            this.soundeffekt.setVolume(newVolume);
            this.soundeffekteVolume = newVolume;
        }
    }

    public void playMusik(MediaPlayer musik)
    {
        if (this.musik != null && !musik.equals(this.musik))
        {
            this.musik.stop();
        }
        this.musik = musik;
        if (this.musikAn)
        {
            musik.setVolume(this.musikVolume);
            musik.play();
        }
    }

    public void playSoundeffekt(AudioClip clip)
    {
        this.soundeffekt = clip;
        if (this.soundeffekteAn)
        {
            clip.play(this.soundeffekteVolume);
        }
    }

    public void setMusikAn(boolean an)
    {
        this.musikAn = an;
        if (this.musik != null)
        {
            if (an)
            {
                this.playMusik(this.musik);
            }
            else
            {
                this.musik.stop();
            }
        }
    }

    public boolean getMusikAn()
    {
        return this.musikAn;
    }

    public void setSoundeffekteAn(boolean an)
    {
        this.soundeffekteAn = an;
        if (!an && this.soundeffekt != null)
        {
            this.soundeffekt.stop();
        }
    }

    public boolean getSoundeffekteAn()
    {
        return this.soundeffekteAn;
    }

}
