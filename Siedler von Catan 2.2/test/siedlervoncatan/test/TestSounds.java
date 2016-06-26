package siedlervoncatan.test;

import javafx.application.Application;
import javafx.stage.Stage;
import siedlervoncatan.sound.Sound;

public class TestSounds extends Application
{

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Sound sound = Sound.getInstanz();
        sound.playSoundeffekt(Sound.BAU_CLIP);
        System.out.println("Musik played");
    }

}
