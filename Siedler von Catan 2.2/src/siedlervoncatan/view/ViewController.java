package siedlervoncatan.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import siedlervoncatan.Spielstart;
import siedlervoncatan.spiel.Spiel;
import siedlervoncatan.view.controller.RootLayoutController;

public class ViewController
{
    private Spiel                spiel;
    private RootLayoutController layoutController;
    private FXMLLoader           loader;
    private Stage                primaryStage;

    public ViewController(Spiel spiel, RootLayoutController layoutController)
    {
        this.layoutController = layoutController;
        this.spiel = spiel;
        this.primaryStage = Spielstart.getPrimaryStage();
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

    public Stage createStage(String view, String text) throws IOException
    {
        this.loader = new FXMLLoader();
        this.loader.setLocation(Spielstart.class.getResource(view));
        Pane pane = this.loader.load();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.primaryStage);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        PopupController controller = this.loader.getController();
        if (controller != null)
        {
            controller.setStage(stage);
            controller.setText(text);
        }
        return stage;
    }

    public FXMLLoader getLoader()
    {
        return this.loader;
    }
}
