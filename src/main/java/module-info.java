module fr.elloworld.pokedex {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens fr.elloworld.pokedex to javafx.fxml;
    exports fr.elloworld.pokedex;
}