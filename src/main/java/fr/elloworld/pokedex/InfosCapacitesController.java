package fr.elloworld.pokedex;

import fr.elloworld.pokedex.pokedex.Pokedex;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class InfosCapacitesController implements Initializable {

    @FXML
    private Label capacitesText;

    @FXML
    void goBackAction(ActionEvent e) {
        App.getInstance().showPokemonDescription();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
                    capacitesText.setText(Pokedex.printAbilitiesDescription(ChoosePokemonController.getIdPokemon()));
                }
        );
    }
}
