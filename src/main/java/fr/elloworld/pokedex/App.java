package fr.elloworld.pokedex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {


    public static String musicButtonName;
    public static final MediaPlayer player = new MediaPlayer(new Media(App.class.getResource("/fr/elloworld/pokedex/pokemon_dreamyard.mp3").toExternalForm()));
    private static App instance;
    public Parent root;
    public Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    public static void musicButtonClicked() {
        player.setVolume(0.05);
        if (player.getStatus() != MediaPlayer.Status.PLAYING) {
            player.play();
        } else player.pause();

    }

    public static String getMusicButtonTextOnClick() {
        if (musicButtonName.equals("Lire la musique")) musicButtonName = "Mettre en pause";
        else musicButtonName = "Lire la musique";
        return musicButtonName;
    }

    public static String getMusicButtonText() {
        return musicButtonName;
    }

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        this.primaryStage = primaryStage;
        showChoosePokemon();
        musicButtonName = "Lire la musique";
    }

    public void setRoot(String fxmlFile) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlFile));
            primaryStage.setScene(new Scene(root, 406, 587));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            fxmlLoader.load();
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showChoosePokemon() {
        try {
            setRoot("choose-pokemon.fxml");
            primaryStage.setTitle("Pokedex - Choix du Pokémon");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPokemonDescription() {
        try {
            setRoot("pokemon-description.fxml");
            primaryStage.setTitle("Pokedex - Description du Pokémon");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAbilitiesInformations() {
        try {
            setRoot("abilities-informations.fxml");
            primaryStage.setTitle("Pokedex - Informations sur les capacités");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}