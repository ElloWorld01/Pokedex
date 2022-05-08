package fr.elloworld.pokedex.controllers;

import fr.elloworld.pokedex.App;
import fr.elloworld.pokedex.pokedex.PokedexPrinter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AbilitiesInformationsController implements Initializable {
    @FXML
    private Button music;
    @FXML
    private Label abilitiesText;

    @FXML
    void goBackAction(ActionEvent e) {
        App.getInstance().showPokemonDescription();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> abilitiesText.setText(PokedexPrinter.printAbilitiesDescription(ChoosePokemonController.getIdPokemon())));
        Platform.runLater(() -> music.setText(App.getMusicButtonText()));
    }

    @FXML
    void onMusicClick(ActionEvent e) {
        Platform.runLater(App::musicButtonClicked);
        Platform.runLater(() -> music.setText(App.getMusicButtonTextOnClick()));
    }
}
