
package player;

import java.io.Serializable;
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
    
    public Profile(Player player)
    {
        this.id = player.getId();
        this.name = player.getName();
        this.team = player.getTeam();
        this.currentPokemon = player.currentPokemon;
    }
}
