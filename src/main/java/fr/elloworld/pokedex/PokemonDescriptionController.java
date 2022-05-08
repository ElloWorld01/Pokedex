package fr.elloworld.pokedex;

import fr.elloworld.pokedex.pokedex.Pokedex;
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
    private ImageView pokemonImage;
    @FXML
    private Button goBack;
    @FXML
    private Button infosCapacites;
    @FXML
    private Label pokemonName;

    @FXML
    void goBackAction(ActionEvent e) {
        App.getInstance().showChoosePokemon();
    }

    @FXML
    void goToInfosCapacitesAction(ActionEvent e) {
        App.getInstance().showInfosCapacite();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int pokemonId = ChoosePokemonController.getIdPokemon();
        Platform.runLater(() -> {
                    pokemonName.setText("Description du Pokémon n°" + pokemonId + "\n\n" +
                            Pokedex.printPokemonById(pokemonId));
                    Image image = new Image(String.valueOf(Pokedex.getPokemonSprite(pokemonId)));
                    pokemonImage.setImage(image);
                }
        );
    }
}
