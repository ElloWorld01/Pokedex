package fr.elloworld.pokedex;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PokedexManager {
    private static int start = 1;
    private static int end = 16;
    static HashMap<Integer, String> pokemons = new HashMap<>();

    private static JSONObject getPokemonFromId(int id) {
        return connectToPokeAPI("pokemon/" + id);
    }

    private static JSONObject connectToPokeAPI(String url) {
        try {
            String pokeapi = "https://pokeapi.co/api/v2/";
            URL hp = new URL(pokeapi + url);
            HttpURLConnection hpCon = (HttpURLConnection) hp.openConnection();
            hpCon.connect();

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(hpCon.getInputStream()));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            inputStr = responseStrBuilder.toString();
            streamReader.close();

            return (JSONObject) new JSONParser().parse(inputStr);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String printPokemon(JSONObject pokemon) {
        List<String> abilities = new ArrayList<>();
        JSONArray abilitiesArray = (JSONArray) pokemon.get("abilities");
        for (int i = 0; i < abilitiesArray.size(); i++) {
            JSONObject ability = (JSONObject) abilitiesArray.get(i);
            if (i == 0)
                abilities.add((String) ((JSONObject) ability.get("ability")).get("name"));
            else
                abilities.add((String) ((JSONObject) ability.get("ability")).get("name"));
        }

        StringBuilder abilitiesString = new StringBuilder();
        for (String ability : abilities)
            abilitiesString.append(getAbilityInFrench(ability)).append(", ");
        abilitiesString.delete(abilitiesString.length() - 2, abilitiesString.length());

        return ("""
                Nom du Pokémon : %s
                Id : %s
                Poids : %s
                Taille : %s
                Capacités : %s
                """).formatted(
                getNameInFrench((String) pokemon.get("name")),
                pokemon.get("id"),
                pokemon.get("weight"),
                pokemon.get("height"),
                abilitiesString);
    }

    public static String printPokemonById(int id) {
        JSONObject p = getPokemonFromId(id);
        if (p != null)
            return printPokemon(p);
        return "";
    }

    public static String listPokemon(int start, int end) {
        if (!pokemons.isEmpty()) {
            if (start < end && start >= 1 && end <= pokemons.size()) {
                PokedexManager.start = start;
                PokedexManager.end = end;
            } else if (end > pokemons.size())
                return printListPokemon(151 - 16, 151);
            else if (start < 1)
                return printListPokemon(1, 16);
            return printListPokemon(start, end);
        }

        JSONObject listPokemons = connectToPokeAPI("pokemon?limit=151");
        JSONArray pokes = (JSONArray) listPokemons.get("results");
        for (int i = 0; i < pokes.size(); i++) {
            JSONObject pokemon = (JSONObject) pokes.get(i);
            pokemons.put(i + 1, (String) pokemon.get("name"));
        }
        return printListPokemon(start, end);
    }

    //print the list of pokemon in the intervale [start, end]
    public static String printListPokemon(int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if ((i-1) % 4 == 0)
                sb.append("\n");
            sb.append(printPokemon(i));
        }
        return sb.toString();
    }


    private static String printPokemon(int id) {
        String name = getNameInFrench(pokemons.get(id));
        return id + " : " + name + " | ";
    }

    private static String getNameInFrench(String name) {
        JSONObject languages = connectToPokeAPI("pokemon-species/" + name);
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
        return null;
    }

    private static String getAbilityInFrench(String name) {
        JSONObject languages = connectToPokeAPI("ability/" + name);
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
        return null;
    }

    public static int getStart() {
        return start;
    }

    public static int getEnd() {
        return end;
    }
}
