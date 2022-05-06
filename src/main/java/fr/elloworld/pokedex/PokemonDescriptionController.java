package fr.elloworld.pokedex;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PokemonDescriptionController implements Initializable {

    @FXML
    private Button goBack;

    @FXML
    private Label pokemonName;

    @FXML
    void goBackAction(ActionEvent e) {
        App.getInstance().showChoosePokemon();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int pokemonId = ChoosePokemonController.getId();
        Platform.runLater(() -> pokemonName.setText("Description du Pokémon n°" + pokemonId + "\n\n" +
                PokedexManager.printPokemonById(pokemonId)
        ));
    }
}
