package gameelements;

import commands.ServerCommand;
import com.lloseng.ocsf.server.ConnectionToClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import player.ComputerPlayer;
import player.Player;
import pokemon.Pokemon;
import wrappers.NetworkWrapper;

public class GameManager
{

    public List<Game> games;

    public GameManager()
    {
        games = new ArrayList();
        //create a new empty game
        games.add(new Game(new ArrayList()));
    }
    
    public void addPlayerToComputerGame(Player player)
    {
        Game game = new Game(new ArrayList());
        ComputerPlayer com = new ComputerPlayer(-1, "Computer", game);
        game.addPlayer(player);
        game.addPlayer(com);
        game.state = GameStates.WAITING_TWO_TEAMS;
        player.requestTeam();
        com.requestTeam();
        games.add(game);       
    }
    
    
    
    public void removeGame(Game game)
    {
        games.remove(game);
    }

    public void addPlayerToHumanGame(Player player) throws IOException
    {
        Game game;
        boolean addSuccess = false;

        for (int idx = 0; idx < games.size(); idx++)
        {
            game = games.get(idx);

            if (games.get(idx).state == GameStates.WAITING_JOIN_PLAYER_2)
            {
                game.addPlayer(player);
                game.state = GameStates.WAITING_TWO_TEAMS;
                addSuccess = true;

                Player p1 = game.getPlayers().get(0);
                Player p2 = game.getPlayers().get(1);

                NetworkWrapper oppNotif0 = new NetworkWrapper(
                        ServerCommand.OPPONENT_FOUND, p1.getProfile());
                NetworkWrapper oppNotif1 = new NetworkWrapper(
                        ServerCommand.OPPONENT_FOUND, p2.getProfile());
                
                p1.send(oppNotif0);
                p2.send(oppNotif1);

                p1.requestTeam();
                p2.requestTeam();
            }
        }

        if (!addSuccess)
        {
            for (int idx = 0; idx < games.size(); idx++)
            {
                game = games.get(idx);

                if (games.get(idx).state == GameStates.WAITING_JOIN_PLAYER_1)
                {
                    game.addPlayer(player);
                    game.state = GameStates.WAITING_JOIN_PLAYER_2;
                    addSuccess = true;
                }
            }
        }

        if (!addSuccess)
        {
            List<Player> list = new ArrayList();
            Game openGame = new Game(list);
            openGame.addPlayer(player);
            openGame.state = GameStates.WAITING_JOIN_PLAYER_2;
            games.add(openGame);

        }
    }

    public void setPlayerBattleSelection(Player player, Pokemon pmon) throws IOException
    {
        Game game = getGameForPlayer(player);

        List<Player> players = game.getPlayers();
        for (int idx = 0; idx < players.size(); idx++)
        {
            if (player.getId() == players.get(idx).getId())
            {
                players.get(idx).currentPokemon = pmon;
                game.setPlayerPokemon(pmon, player.getId());
                return;
            }
        }
    }

    public void setPlayerTeam(Player player, ArrayList<Pokemon> team)
    {
        Game game = getGameForPlayer(player);
        List<Player> players = game.getPlayers();
        for (int idx = 0; idx < players.size(); idx++)
        {
            if (player.getId() == players.get(idx).getId())
            {
                
                game.setPlayerTeam(team, player.getId());
                //players.get(idx).setTeam(team);
//                if (game.state == GameStates.WAITING_ONE_TEAM)
//                {
//                    game.state = GameStates.WAITING_TWO_BATTLE;
//                } else if (game.state == GameStates.WAITING_TWO_TEAMS)
//                {
//                    game.state = GameStates.WAITING_ONE_TEAM;
//                }
                return;
            }
        }
    }

    public Game getGameForPlayer(Player player)
    {
        for (Game game : games)
        {
            for(Player p: game.getPlayers())
                if(p.getId() == player.getId())
                {
                    return game;
                }
        }
        return null;
    }
}
