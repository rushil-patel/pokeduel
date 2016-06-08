
package model;

import java.util.List;
import java.util.Observable;
import player.Profile;
import pokemon.Pokemon;

/**
 *
 * @author rushi_000
 */
public class BattleModel extends Observable
{

    public String playerName;
    public int playerId;
    public List<Pokemon> playerTeam;
    public Pokemon playerSelection;
    
    public String opponentName;
    public int opponentId;
    public List<Pokemon> opponentTeam;
    public Pokemon opponentSelection;
    
    public void setPlayerProfile(Profile profile)
    {
        playerName = profile.name;
        playerId = profile.id;
        playerTeam = profile.team;
        playerSelection = profile.currentPokemon;
        setChanged();
        notifyObservers();
        
    }
       
    public void setOpponentProfile(Profile profile)
    {
        opponentName = profile.name;
        opponentId = profile.id;
        opponentTeam = profile.team;
        opponentSelection = profile.currentPokemon;
        setChanged();
        notifyObservers();
    }
}
