package fr.elloworld.pokedex.pokedex;

import org.json.simple.JSONObject;

import java.util.HashMap;


public class PokedexPrinter {

    private static final HashMap<Integer, String> pokemons = Pokedex.getPokemons();

    public static String printAbilitiesDescription(int id) {
        HashMap<String, String> abilities = PokedexAbilitiesManager.getAbilitiesWithURL(Pokedex.getPokemonFromId(id));
        StringBuilder abilitiesSB = new StringBuilder();
        for (String ability : abilities.keySet()) {
            abilitiesSB.append(PokedexAbilitiesManager.getAbilityInFrench(ability)).append(" :\n")
                    .append(PokedexAbilitiesManager.getAbilityFlavorTextInFrench(abilities.get(ability))).append("\n\n");
        }
        return abilitiesSB.toString();
    }

    public static String printPokemonById(int id) {
        JSONObject p = Pokedex.getPokemonFromId(id);
        if (p != null)
            return printPokemon(p);
        return "";
    }

    public static String printListPokemon(int start, int end) {
        if (!pokemons.isEmpty()) {
            if (start < end && start >= Pokedex.getStart() && end <= pokemons.size()) {
                Pokedex.setStart(start);
                Pokedex.setEnd(end);
            } else if (end > pokemons.size())
                return listPokemon(151 - Pokedex.getEnd(), 151);
            else if (start < 1)
                return listPokemon(Pokedex.getStart(), Pokedex.getEnd());
            return listPokemon(start, end);
        }
        Pokedex.fillPokemonsHashMap();
        return listPokemon(start, end);
    }

    static String listPokemon(int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if ((i - 1) % 3 == 0)
                sb.append("\n");
            sb.append(printPokemon(i));
        }
        return sb.toString();
    }

    private static String printPokemon(int id) {
        String name = PokedexUtils.getNameInFrench(pokemons.get(id));
        return id + " : " + name + " | ";
    }

    static String printPokemon(JSONObject pokemon) {
        HashMap<String, String> abilitiesString = PokedexAbilitiesManager.getAbilitiesWithURL(pokemon);
        return ("""
                Nom du Pokémon : %s
                Id : %s
                Poids : %s
                Taille : %s
                Capacité(s) : %s
                """).formatted(
                PokedexUtils.getNameInFrench((String) pokemon.get("name")),
                pokemon.get("id"),
                pokemon.get("weight"),
                pokemon.get("height"),
                PokedexAbilitiesManager.getAbilitiesString(abilitiesString));
    }
}
