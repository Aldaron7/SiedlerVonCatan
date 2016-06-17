package siedlervoncatan.utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import siedlervoncatan.enums.Rohstoff;
import siedlervoncatan.spiel.Spieler;
import siedlervoncatan.view.View;

public class Handel
{
    private ObservableList<Rohstoff> angebot;
    private ObservableList<Rohstoff> nachfrage;
    private Spieler                  nachfrager;
    private Spieler                  anbieter;

    public Handel()
    {
        this(FXCollections.observableArrayList(), FXCollections.observableArrayList());
    }

    public Handel(ObservableList<Rohstoff> angebot, ObservableList<Rohstoff> nachfrage)
    {
        this.angebot = angebot;
        this.nachfrage = nachfrage;
    }

    /**
     * Nach Bestätigung eines Confirmation Alerts wird der Handel ausgeführt.
     */
    public void handeln()
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.getDialogPane().getScene().getStylesheets().add(View.STYLESHEET.getPfad());
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText(String.format("%s wollen Sie %s gegen %s mit %s tauschen?", this.anbieter, this.angebot, this.nachfrage, this.nachfrager));
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK)
            {
                this.anbieter.removeKarten(this.angebot);
                this.anbieter.addKarten(this.nachfrage);
                this.nachfrager.removeKarten(this.nachfrage);
                this.nachfrager.addKarten(this.angebot);
            }
        });

    }

    public void addAngebot(Rohstoff rohstoff)
    {
        this.angebot.add(rohstoff);
    }

    public boolean removeAngebot(Rohstoff rohstoff)
    {
        if (this.angebot.contains(rohstoff))
        {
            this.angebot.remove(rohstoff);
            return true;
        }
        return false;
    }

    public void addNachfrage(Rohstoff rohstoff)
    {
        this.nachfrage.add(rohstoff);
    }

    public boolean removeNachfrage(Rohstoff rohstoff)
    {
        if (this.nachfrage.contains(rohstoff))
        {
            this.nachfrage.remove(rohstoff);
            return true;
        }
        return false;
    }

    public ObservableList<Rohstoff> getAngebot()
    {
        return this.angebot;
    }

    public ObservableList<Rohstoff> getNachfrage()
    {
        return this.nachfrage;
    }

    public Spieler getNachfrager()
    {
        return this.nachfrager;
    }

    public void setNachfrager(Spieler nachfrager)
    {
        this.nachfrager = nachfrager;
    }

    public Spieler getAnbieter()
    {
        return this.anbieter;
    }

    public void setAnbieter(Spieler anbieter)
    {
        this.anbieter = anbieter;
    }

}
