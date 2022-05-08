package fr.elloworld.pokedex.pokedex;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TypesManager {

    public static String getTypes(JSONObject pokemon) {
        List<String> typeNames = new ArrayList<>();

        JSONArray typesList = (JSONArray) pokemon.get("types");
        StringBuilder types = new StringBuilder();
        for (Object t : typesList) {
            JSONObject type = (JSONObject) t;
            JSONObject ty = (JSONObject) type.get("type");
            String typeName = (String) ty.get("name");
            typeNames.add(typeName);

            StringBuilder typesSB = new StringBuilder();
            for (String tn : typeNames)
                typesSB.append(getTypeInFrench(tn)).append(", ");

            typesSB.delete(typesSB.length() - 2, typesSB.length());
            return typesSB.toString();
        }
        return types.substring(0, types.length() - 3);
    }

    private static String getTypeInFrench(String name) {
        JSONObject languages = Pokedex.getJSONObjectFromPokeAPI("type/" + name);
        return Utils.getElementInFrench(languages);
    }
}
