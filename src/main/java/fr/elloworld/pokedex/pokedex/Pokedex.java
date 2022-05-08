package fr.elloworld.pokedex.pokedex;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Pokedex {
    private static final int DEFAULT_START = 1;
    private static final int DEFAULT_END = 27;
    /**
     * Data from <a href="https://pokeapi.co/">https://pokeapi.co/</a>
     */
    static final String pokeapiUrl = "https://pokeapi.co/api/v2/";
    private static int start = 1;
    private static int end = 18;

    private static final HashMap<Integer, String> pokemons = new HashMap<>();


    static JSONObject getPokemonFromId(int id) {
        return getJSONObjectFromPokeAPI("pokemon/" + id);
    }

    static JSONObject getJSONObjectFromPokeAPI(String url) {
        try {
            URL finalUrl = url.contains(pokeapiUrl) ? new URL(url) : new URL(pokeapiUrl + url);
            String json = Utils.getStringFromUrl(finalUrl);
            return (JSONObject) new JSONParser().parse(json);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void fillPokemonsHashMap() {
        JSONObject listPokemons = getJSONObjectFromPokeAPI("pokemon?limit=151");
        JSONArray pokes = (JSONArray) listPokemons.get("results");
        for (int i = 0; i < pokes.size(); i++) {
            JSONObject pokemon = (JSONObject) pokes.get(i);
            pokemons.put(i + 1, (String) pokemon.get("name"));
        }
    }


    public static URL getPokemonSprite(int id) {
        JSONObject pokemon = getPokemonFromId(id);
        JSONObject sprites = (JSONObject) pokemon.get("sprites");
        String spriteUrl = (String) sprites.get("front_default");

        try {
            return new URL(spriteUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int getStart() {
        return start;
    }

    public static void setStart(int start) {
        Pokedex.start = start;
    }

    public static int getEnd() {
        return end;
    }

    public static void setEnd(int end) {
        Pokedex.end = end;
    }

    public static int getDefaultStart() {
        return DEFAULT_START;
    }

    public static int getDefaultEnd() {
        return DEFAULT_END;
    }

    public static HashMap<Integer, String> getPokemons() {
        return pokemons;
    }
}
