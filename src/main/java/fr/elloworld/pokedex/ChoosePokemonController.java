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
    private Button submitButton;
    @FXML
    private Label pokemonList;
    public static int id;

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
        ChoosePokemonController.id = id;
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

    public static int getId() {
        return id;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> pokemonList.setText(PokedexManager.listPokemon(1, 16)));
    }


    @FXML
    void onClickNext(ActionEvent e) {
        Platform.runLater(() -> pokemonList.setText(PokedexManager.listPokemon(
                PokedexManager.getStart() + 16,
                PokedexManager.getEnd() + 16
        )));
    }

    @FXML
    void onClickPrevious(ActionEvent e) {
        Platform.runLater(() -> pokemonList.setText(PokedexManager.listPokemon(
                PokedexManager.getStart() - 16,
                PokedexManager.getEnd() - 16
        )));
    }
}