package player;

import com.lloseng.ocsf.server.ConnectionToClient;
import commands.ServerCommand;
import connectors.DBConnection;
import gameelements.Game;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import pokemon.Pokemon;
import wrappers.NetworkWrapper;

/**
 *
 * @author rushi_000
 */
public class ComputerPlayer extends Player
{

    Game game;

    public ComputerPlayer(int id, String name, Game game)
    {
        super(id, name);
        this.game = game;
        
    }

    public void send(NetworkWrapper net)
    {
        switch ((ServerCommand) net.getCommand())
        {
            // case POKEMON_DATA:
            //     doLoadPokemon((ArrayList<Pokemon>) net.getObject());
            //     break;
            //case SUCCESS_LOGIN:
            //     doLoadMenuPanel((Player) net.getObject());
            // case OPPONENT_FOUND:
            //     break;
            case START_TEAM_SELECT:
                try
                {
                    int totalCost = 0;
                    int limit = 200;
                    Random rand = new Random();
                    ArrayList<Pokemon> pokeList =
                            (ArrayList<Pokemon>) DBConnection.getAllPokeStatsAndMult();
                    ArrayList<Pokemon> team = new ArrayList();
                    
                    Pokemon recruit = pokeList.get(
                            rand.nextInt(pokeList.size()));
                    while(totalCost + recruit.cost < limit)
                    {
                        team.add(recruit);
                        pokeList.remove(recruit);
                        totalCost += recruit.cost;
                        recruit = pokeList.get(rand.nextInt(pokeList.size()));
                    }
                        
                    game.setPlayerTeam(team, this.getId());
                    
                } catch (Exception ex)
                {
                    Logger.getLogger(ComputerPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case GET_TEAM:
                break;
            case START_BATTLE:
                break;
            case GET_BATTLE_SELECT:
                Random rand = new Random();
                List<Pokemon> team = getTeam();
                int idx = rand.nextInt(team.size());
                Pokemon chosen = team.get(idx);
                while(!chosen.isAlive)
                {
                    chosen = team.get(rand.nextInt(team.size()));
                }
                game.setPlayerPokemon(chosen, this.getId());
                break;
            case GAME_OVER:
                break;
            case PLAYER_LEFT:
                break;
            case ERROR_LOGIN:
                break;
            case BATTLE_RESULT:
                break;
            case PLAYER_UPDATE:
                break;
            case OPPONENT_UPDATE:
                break;
        }
    }
}