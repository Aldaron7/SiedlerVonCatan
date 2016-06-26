package siedlervoncatan.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import siedlervoncatan.Spielstart;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.view.controller.RootLayoutController;

public class ViewController
{
    private Spiel                spiel;
    private RootLayoutController layoutController;
    private FXMLLoader           loader;

    public ViewController(Spiel spiel, RootLayoutController layoutController)
    {
        this.layoutController = layoutController;
        this.spiel = spiel;
    }

    public Pane initPane(String view) throws IOException
    {
        this.loader = new FXMLLoader();
        this.loader.setLocation(Spielstart.class.getResource(view));
        Pane pane = this.loader.load();
        Controller controller = this.loader.getController();
        if (controller != null)
        {
            controller.setSpiel(this.spiel);
            controller.setNode(pane);
            controller.setLayoutController(this.layoutController);
        }
        return pane;
    }

    // public Stage createStage(String view, String titel) throws IOException
    // {
    // Pane pane = this.initPane(view);
    // Scene scene = new Scene(pane);
    // Stage stage = new Stage();
    // stage.setTitle(titel);
    // stage.initModality(Modality.WINDOW_MODAL);
    // stage.initOwner(this.primaryStage);
    // stage.initStyle(StageStyle.UNDECORATED);
    // stage.setScene(scene);
    // Controller controller = this.loader.getController();
    // if (controller != null)
    // {
    // controller.setStage(stage);
    // }
    // return stage;
    // }

    public FXMLLoader getLoader()
    {
        return this.loader;
    }
}
