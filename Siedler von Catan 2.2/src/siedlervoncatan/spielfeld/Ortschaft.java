package siedlervoncatan.spielfeld;

import javafx.scene.image.Image;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.utility.Position;

/**
 * Interface für Siedlung und Stadt
 * 
 * @author mvr
 *
 */
public interface Ortschaft
{

    public Spieler getBesitzer();

    public int getErtrag();

    public Position getPosition();

    public Image getImage();
}
