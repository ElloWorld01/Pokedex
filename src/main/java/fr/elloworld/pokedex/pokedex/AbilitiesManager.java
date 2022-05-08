package fr.elloworld.pokedex.pokedex;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class AbilitiesManager {
    static HashMap<String, String> getAbilitiesWithURL(JSONObject pokemon) {
        HashMap<String, String> abilities = new HashMap<>();
        JSONArray abilitiesArray = (JSONArray) pokemon.get("abilities");
        for (Object o : abilitiesArray) {
            JSONObject ability = (JSONObject) o;
            String url = (String) ((JSONObject) ability.get("ability")).get("url");
            abilities.put((String) ((JSONObject) ability.get("ability")).get("name"), url);
        }
        return abilities;
    }

    static String getAbilities(HashMap<String, String> abilities) {
        StringBuilder abilitiesSB = new StringBuilder();
        for (String ability : abilities.keySet())
            abilitiesSB.append(getAbilityInFrench(ability)).append(", ");

        abilitiesSB.delete(abilitiesSB.length() - 2, abilitiesSB.length());
        return abilitiesSB.toString();
    }

    static String getAbilityFlavorTextInFrench(String url) {
        JSONObject ability = Pokedex.getJSONObjectFromPokeAPI(url);
        JSONArray flavorTextEntries = (JSONArray) ability.get("flavor_text_entries");
        for (Object entry : flavorTextEntries) {
            JSONObject flavorEntry = (JSONObject) entry;
            JSONObject obj = (JSONObject) flavorEntry.get("language");
            if (obj.get("name").equals("fr")) {
                return (String) flavorEntry.get("flavor_text");
            }
        }
        return "";
    }

    static String getAbilityInFrench(String name) {
        JSONObject languages = Pokedex.getJSONObjectFromPokeAPI("ability/" + name);
        return Utils.getElementInFrench(languages);
    }
}
