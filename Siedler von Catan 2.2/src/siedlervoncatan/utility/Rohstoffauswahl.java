package siedlervoncatan.utility;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import siedlervoncatan.enums.Rohstoff;

public class Rohstoffauswahl
{
    public static Rohstoff getRohstoff()
    {
        return Rohstoffauswahl.getRohstoff("Wähle einen Rohstoff.");
    }

    /**
     * Erzeugt einen Alert vom Typ CONFIRMATION mit den Buttons aller Rohstoffe.
     * 
     * @param text
     * @return den geklickten Rohstoff.
     */
    public static Rohstoff getRohstoff(String text)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.getDialogPane().getScene().getStylesheets().add("siedlervoncatan/view/stylesheet.css");
        alert.setTitle("Rohstoffauswahl");
        alert.setHeaderText(text);
        alert.initStyle(StageStyle.UNDECORATED);

        ButtonType holz = new ButtonType("Holz");
        ButtonType lehm = new ButtonType("Lehm");
        ButtonType wolle = new ButtonType("Wolle");
        ButtonType korn = new ButtonType("Korn");
        ButtonType erz = new ButtonType("Erz");

        alert.getButtonTypes().setAll(holz, lehm, wolle, korn, erz);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == holz)
        {
            return Rohstoff.HOLZ;
        }
        if (result.get() == lehm)
        {
            return Rohstoff.LEHM;
        }
        if (result.get() == wolle)
        {
            return Rohstoff.WOLLE;
        }
        if (result.get() == korn)
        {
            return Rohstoff.KORN;
        }
        if (result.get() == erz)
        {
            return Rohstoff.ERZ;
        }
        return null;

    }
}
