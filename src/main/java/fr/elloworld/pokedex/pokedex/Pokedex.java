package fr.elloworld.pokedex.pokedex;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Pokedex {

    private static final int DEFAULT_START = 1;
    private static final int DEFAULT_END = 27;
    private static int start = 1;
    private static int end = 18;

    static HashMap<Integer, String> pokemons = new HashMap<>();

    public static String listPokemon(int start, int end) {
        if (!pokemons.isEmpty()) {
            if (start < end && start >= DEFAULT_START && end <= pokemons.size()) {
                Pokedex.start = start;
                Pokedex.end = end;
            } else if (end > pokemons.size())
                return printListPokemon(151 - DEFAULT_END, 151);
            else if (start < 1)
                return printListPokemon(DEFAULT_START, DEFAULT_END);
            return printListPokemon(start, end);
        }
        fillPokemonsHashMap();
        return printListPokemon(start, end);
    }

    private static String printListPokemon(int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if ((i - 1) % 3 == 0)
                sb.append("\n");
            sb.append(printPokemon(i));
        }
        return sb.toString();
    }

    static JSONObject getPokemonFromId(int id) {
        return getJSONObjectFromPokeAPI("pokemon/" + id);
    }

    static JSONObject getJSONObjectFromPokeAPI(String url) {
        try {
            URL hp;
            String pokeapi = "https://pokeapi.co/api/v2/";
            if (url.contains("https://pokeapi.co/api/v2/"))
                hp = new URL(url);
            else
                hp = new URL(pokeapi + url);
            HttpURLConnection hpCon = (HttpURLConnection) hp.openConnection();
            hpCon.connect();

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(hpCon.getInputStream()));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            inputStr = responseStrBuilder.toString();
            streamReader.close();

            return (JSONObject) new JSONParser().parse(inputStr);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void fillPokemonsHashMap() {
        JSONObject listPokemons = getJSONObjectFromPokeAPI("pokemon?limit=151");
        JSONArray pokes = (JSONArray) listPokemons.get("results");
        for (int i = 0; i < pokes.size(); i++) {
            JSONObject pokemon = (JSONObject) pokes.get(i);
            pokemons.put(i + 1, (String) pokemon.get("name"));
        }
    }

    private static String printPokemon(JSONObject pokemon) {
        HashMap<String, String> abilitiesString = AbilitiesManager.getAbilitiesWithURL(pokemon);
        return ("""
                Nom du Pokémon : %s
                Id : %s
                Poids : %s
                Taille : %s
                Capacité(s) : %s
                """).formatted(
                getNameInFrench((String) pokemon.get("name")),
                pokemon.get("id"),
                pokemon.get("weight"),
                pokemon.get("height"),
                AbilitiesManager.getAbilitiesString(abilitiesString));
    }

    public static String printAbilitiesDescription(int id) {
        HashMap<String, String> abilities = AbilitiesManager.getAbilitiesWithURL(Pokedex.getPokemonFromId(id));
        StringBuilder abilitiesSB = new StringBuilder();
        for (String ability : abilities.keySet()) {
            abilitiesSB.append(AbilitiesManager.getAbilityInFrench(ability)).append(" :\n")
                    .append(AbilitiesManager.getAbilityFlavorTextInFrench(abilities.get(ability))).append("\n\n");
        }
        return abilitiesSB.toString();
    }

    public static String printPokemonById(int id) {
        JSONObject p = getPokemonFromId(id);
        if (p != null)
            return printPokemon(p);
        return "";
    }

    private static String printPokemon(int id) {
        String name = getNameInFrench(pokemons.get(id));
        return id + " : " + name + " | ";
    }

    private static String getNameInFrench(String name) {
        JSONObject languages = getJSONObjectFromPokeAPI("pokemon-species/" + name);
        return getSearchedElement(languages);
    }


    static String getSearchedElement(JSONObject languages) {
        for (Object key : languages.keySet()) {
            if (key.equals("names")) {
                JSONArray names = (JSONArray) languages.get(key);
                for (Object o : names) {
                    JSONObject object = (JSONObject) o;
                    String pokemonName = (String) object.get("name");

                    JSONObject language = (JSONObject) object.get("language");

                    String langue = (String) language.get("name");
                    if (langue.equalsIgnoreCase("fr"))
                        return pokemonName;
                }
            }
        }
        return "";
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

    public static int getEnd() {
        return end;
    }

    public static int getDefaultStart() {
        return DEFAULT_START;
    }

    public static int getDefaultEnd() {
        return DEFAULT_END;
    }
}
