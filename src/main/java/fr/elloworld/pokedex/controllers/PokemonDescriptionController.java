package fr.elloworld.pokedex.controllers;

import fr.elloworld.pokedex.App;
import fr.elloworld.pokedex.pokedex.Pokedex;
import fr.elloworld.pokedex.pokedex.PokedexPrinter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class PokemonDescriptionController implements Initializable {
    @FXML
    private Button music;

    @FXML
    private ImageView pokemonImage;
    @FXML
    private Label pokemonName;

    @FXML
    void goBackAction(ActionEvent e) {
        App.getInstance().showChoosePokemon();
    }

    @FXML
    void goToInfosCapacitesAction(ActionEvent e) {
        App.getInstance().showAbilitiesInformations();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int pokemonId = ChoosePokemonController.getIdPokemon();
        Platform.runLater(() -> {
                    pokemonName.setText(PokedexPrinter.printPokemonById(pokemonId));
                    Image image = new Image(String.valueOf(Pokedex.getPokemonSprite(pokemonId)));
                    pokemonImage.setImage(image);
                }
        );
        Platform.runLater(() -> music.setText(App.getMusicButtonText()));
    }

    @FXML
    void onMusicClick(ActionEvent e) {
        Platform.runLater(App::musicButtonClicked);
        Platform.runLater(() -> music.setText(App.getMusicButtonTextOnClick()));
    }
}
