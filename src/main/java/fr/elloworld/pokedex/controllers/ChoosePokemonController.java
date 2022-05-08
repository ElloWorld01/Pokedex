package fr.elloworld.pokedex.controllers;

import fr.elloworld.pokedex.App;
import fr.elloworld.pokedex.pokedex.Pokedex;
import fr.elloworld.pokedex.pokedex.PokedexPrinter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChoosePokemonController implements Initializable {

    public static final int DEFAULT_START = Pokedex.getDefaultStart();
    public static final int DEFAULT_END = Pokedex.getDefaultEnd();
    public static int idPokemon;
    @FXML
    private TextField idField;
    @FXML
    private Button music;
    @FXML
    private Button submitButton;
    @FXML
    private Label pokemonList;

    public static int getIdPokemon() {
        return idPokemon;
    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent e) {
        if (idField.getText().isEmpty()) {
            alert("Veuillez rentrer un identifiant.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idField.getText());
        } catch (NumberFormatException ex) {
            alert("Veuillez rentrer un identifiant valide.");
            return;
        }
        if (id < 1 || id > 151) {
            alert("L'identifiant doit être compris entre 1 et 151.");
            return;
        }
        idPokemon = id;
        App.getInstance().showPokemonDescription();
    }

    private void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Une erreur est survenue");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(submitButton.getScene().getWindow());
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> pokemonList.setText(PokedexPrinter.printListPokemon(DEFAULT_START, DEFAULT_END)));
        Platform.runLater(() -> music.setText(App.getMusicButtonText()));
    }


    @FXML
    void onClickNext(ActionEvent e) {
        Platform.runLater(() -> pokemonList.setText(PokedexPrinter.printListPokemon(
                Pokedex.getStart() + DEFAULT_END,
                Pokedex.getEnd() + DEFAULT_END
        )));
    }

    @FXML
    void onClickPrevious(ActionEvent e) {
        Platform.runLater(() -> pokemonList.setText(PokedexPrinter.printListPokemon(
                Pokedex.getStart() - DEFAULT_END,
                Pokedex.getEnd() - DEFAULT_END
        )));
    }

    @FXML
    void onMusicClick(ActionEvent e) {
        Platform.runLater(App::musicButtonClicked);
        Platform.runLater(() -> music.setText(App.getMusicButtonTextOnClick()));
    }
}