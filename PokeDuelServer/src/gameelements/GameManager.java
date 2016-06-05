package gameelements;

import Commands.ServerCommand;
import com.lloseng.ocsf.server.ConnectionToClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import pokeduelserver.Game;
import pokeduelserver.GameStates;
import player.Player;
import pokeduelserver.Pokemon;
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

    public void addPlayerToGame(Player player) throws IOException
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
                        ServerCommand.OPPONENT_FOUND, p1);
                NetworkWrapper oppNotif1 = new NetworkWrapper(
                        ServerCommand.OPPONENT_FOUND, p2);
                //SEND team select command to both players
                game.getPlayers().get(1).client.sendToClient(oppNotif0);
                game.getPlayers().get(2).client.sendToClient(oppNotif1);

                NetworkWrapper teamCommand = new NetworkWrapper(null,
                        ServerCommand.START_TEAM_SELECT);
                p1.client.sendToClient(teamCommand);
                p2.client.sendToClient(teamCommand);
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

                if (game.state == GameStates.WAITING_TWO_BATTLE)
                {
                    game.state = GameStates.WAITING_ONE_BATTLE;
                } 
                else if (game.state == GameStates.WAITING_ONE_BATTLE)
                {
                    players.get(0).client.sendToClient(
                            new NetworkWrapper(ServerCommand.OPPONENT_UPDATE,
                            players.get(1)));
                    players.get(1).client.sendToClient(
                            new NetworkWrapper(ServerCommand.OPPONENT_UPDATE,
                            players.get(0)));
                    
                    Player winner = game.doBattle();

                    players.get(0).client.sendToClient(
                            new NetworkWrapper(ServerCommand.BATTLE_RESULT,
                            winner));
                    players.get(1).client.sendToClient(
                            new NetworkWrapper(ServerCommand.BATTLE_RESULT,
                            winner));
                    
                    players.get(0).client.sendToClient(
                            new NetworkWrapper(ServerCommand.PLAYER_UPDATE,
                            players.get(0)));
                    players.get(1).client.sendToClient(
                            new NetworkWrapper(ServerCommand.PLAYER_UPDATE,
                            players.get(1)));
                    
                    game.state = GameStates.WAITING_TWO_BATTLE;
                }
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
                players.get(idx).setTeam(team);
                if (game.state == GameStates.WAITING_ONE_TEAM)
                {
                    game.state = GameStates.WAITING_TWO_BATTLE;
                } else if (game.state == GameStates.WAITING_TWO_TEAMS)
                {
                    game.state = GameStates.WAITING_ONE_TEAM;
                }
                return;
            }
        }
    }

    private Game getGameForPlayer(Player player)
    {
        for (Game game : games)
        {
            int pid1 = game.getPlayers().get(0).getId();
            int pid2 = game.getPlayers().get(1).getId();
            if (pid1 == player.getId() || pid2 == player.getId())
            {
                return game;
            }
        }
        return null;
    }
}
