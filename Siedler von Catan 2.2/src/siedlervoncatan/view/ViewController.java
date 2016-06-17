package siedlervoncatan.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import siedlervoncatan.Spielstart;
import siedlervoncatan.spiel.Spiel;

public class ViewController
{
    private Stage      primaryStage;
    private Spiel      spiel;
    private FXMLLoader loader;

    public ViewController(Stage primaryStage, Spiel spiel)
    {
        this.primaryStage = primaryStage;
        this.spiel = spiel;
    }

    public Pane initPane(View view) throws IOException
    {
        this.loader = new FXMLLoader();
        this.loader.setLocation(Spielstart.class.getResource(view.getPfad()));
        Pane pane = this.loader.load();
        Controller controller = this.loader.getController();
        controller.setSpiel(this.spiel);
        return pane;
    }

    public Stage createStage(View view, String titel) throws IOException
    {
        Pane pane = this.initPane(view);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setTitle(titel);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.primaryStage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        Controller controller = this.loader.getController();
        controller.setStage(stage);
        return stage;
    }

    public FXMLLoader getLoader()
    {
        return this.loader;
    }
}
