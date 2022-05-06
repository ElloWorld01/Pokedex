package fr.elloworld.pokedex;

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

    @FXML
    private TextField idField;
    @FXML
    private Button previous;
    @FXML
    private Button next;

    @FXML
    private Button submitButton;
    @FXML
    private Label pokemonList;
    public static int idPokemon;
    public static final int DEFAULT_START = PokedexManager.getDefaultStart();
    public static final int DEFAULT_END = PokedexManager.getDefaultEnd();

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

    public static int getIdPokemon() {
        return idPokemon;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> pokemonList.setText(PokedexManager.listPokemon(DEFAULT_START, DEFAULT_END)));
    }


    @FXML
    void onClickNext(ActionEvent e) {
        Platform.runLater(() -> pokemonList.setText(PokedexManager.listPokemon(
                PokedexManager.getStart() + DEFAULT_END,
                PokedexManager.getEnd() + DEFAULT_END
        )));
    }

    @FXML
    void onClickPrevious(ActionEvent e) {
        Platform.runLater(() -> pokemonList.setText(PokedexManager.listPokemon(
                PokedexManager.getStart() - DEFAULT_END,
                PokedexManager.getEnd() - DEFAULT_END
        )));
    }
}