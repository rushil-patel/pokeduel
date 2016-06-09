
package player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import pokemon.Pokemon;

/**
 *
 * @author rushi_000
 */
public class Profile implements Serializable
{

    public final String name;
    public final int id;
    public final List<Pokemon> team;
    public final Pokemon currentPokemon;
    public final int wins;
    public final int losses;
    
    public Profile(Player player)
    {
        this.id = player.getId();
        this.name = player.getName();
        team = new ArrayList();
        for(Pokemon pokemon: player.getTeam())
        {
            team.add(new Pokemon(pokemon));
        }
        this.currentPokemon = player.currentPokemon;
        wins = player.wins;
        losses = player.losses;
    }
}
