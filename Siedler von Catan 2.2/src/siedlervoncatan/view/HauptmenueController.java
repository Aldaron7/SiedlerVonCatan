package siedlervoncatan.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import siedlervoncatan.Spielstart;

public class HauptmenueController
{
    private Spielstart spielstart;

    public void setSpielstart(Spielstart spielstart)
    {
        this.spielstart = spielstart;
    }

    @FXML
    private void handleNeu()
    {
        this.spielstart.neuesSpiel();
    }

    @FXML
    private void handleLaden()
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("SVC files", "*svc");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setInitialDirectory(new File("saves"));
        File file = fileChooser.showOpenDialog(this.spielstart.getPrimaryStage());
        if (file != null)
        {
            this.spielstart.spielLaden(file);
        }
    }

    @FXML
    private void handleBeenden()
    {
        System.exit(0);
    }

}
