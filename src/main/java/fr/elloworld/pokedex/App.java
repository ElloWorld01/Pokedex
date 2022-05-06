package fr.elloworld.pokedex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static App instance;
    public Parent root;

    public Stage primaryStage;

    public void setRoot(String fxmlFile) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlFile));
            primaryStage.setScene(new Scene(root, 800, 500));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            fxmlLoader.load();
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        this.primaryStage = primaryStage;
        showChoosePokemon();
    }

    public void showPokemonDescription() {
        try {
            setRoot("pokemon-description.fxml");
            primaryStage.setTitle("Description du Pokémon");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showChoosePokemon() {
        try {
            setRoot("choose-pokemon.fxml");
            primaryStage.setTitle("Choix du Pokémon");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}