package fr.elloworld.pokedex.pokedex;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {

    static String getStringFromUrl(URL url) throws IOException {
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.connect();

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);

        inputStr = responseStrBuilder.toString();
        streamReader.close();
        return inputStr;
    }

    static String getNameInFrench(String name) {
        JSONObject languages = Pokedex.getJSONObjectFromPokeAPI("pokemon-species/" + name);
        return getElementInFrench(languages);
    }

    static String getElementInFrench(JSONObject languages) {
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
}
