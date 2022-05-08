module fr.elloworld.pokedex {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires javafx.media;


    opens fr.elloworld.pokedex to javafx.fxml;
    exports fr.elloworld.pokedex;
    exports fr.elloworld.pokedex.pokedex;
    opens fr.elloworld.pokedex.pokedex to javafx.fxml;
    exports fr.elloworld.pokedex.controllers;
    opens fr.elloworld.pokedex.controllers to javafx.fxml;
}