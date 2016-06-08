
package model;

import commands.ServerCommand;
import java.util.ArrayList;
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
    public String roundWinner = null;
    
    public BattleModel()
    {
        playerTeam = new ArrayList();
        opponentTeam = new ArrayList();
    }
    
    
    public void doNewRound()
    {
        opponentSelection = null;
        playerSelection = null;
        setChanged();
        notifyObservers(ServerCommand.GET_BATTLE_SELECT);
    }
    public void setRoundWinner(Profile profile)
    {
        roundWinner = profile.name;
        setChanged();
        notifyObservers(ServerCommand.BATTLE_RESULT);
    }
    public void setPlayerProfile(Profile profile)
    {
        playerName = profile.name;
        playerId = profile.id;
        playerTeam = profile.team;
        playerSelection = profile.currentPokemon;
        setChanged();
        notifyObservers(ServerCommand.PLAYER_UPDATE);
        
    }
       
    public void setOpponentProfile(Profile profile)
    {
        opponentName = profile.name;
        opponentId = profile.id;
        opponentTeam = profile.team;
        opponentSelection = profile.currentPokemon;
        setChanged();
        notifyObservers(ServerCommand.OPPONENT_UPDATE);
    }
}
