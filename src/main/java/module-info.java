module fr.elloworld.pokedex.pokedex {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens fr.elloworld.pokedex.pokedex to javafx.fxml;
    exports fr.elloworld.pokedex.pokedex;
}