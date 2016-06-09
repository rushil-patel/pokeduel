package server;

import commands.ClientCommand;
import commands.ServerCommand;
import com.lloseng.ocsf.server.ObservableServer;
import com.lloseng.ocsf.server.ConnectionToClient;
import gameelements.GameManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import gameelements.Game;
import player.Player;
import connectors.DBConnection;
import gameelements.GameStates;
import java.util.logging.Level;
import java.util.logging.Logger;
import pokemon.Pokemon;
import wrappers.NetworkWrapper;

public class GameServer extends ObservableServer
{

    private GameManager gMan;
    private Map<ConnectionToClient, Player> connectionUsers;
    private Map<Player, ConnectionToClient> userConnection;

    public GameServer(int port)
    {
        super(port);
        gMan = new GameManager();

        connectionUsers = new HashMap<>();
        userConnection = new HashMap<>();
    }

    @Override
    protected void handleMessageFromClient(Object msg,
            ConnectionToClient client)
    {
        NetworkWrapper net = (NetworkWrapper) msg;

        switch ((ClientCommand) net.getCommand())
        {
            case LOAD_POKEMON:
                //doLoadPokemon
                doLoadPokemon(net, client);
                break;
            case LOGIN:
                doLogin(net, client);
                break;
            case FIND_GAME_HUMAN:
                doFindGameHuman(net, client);
                break;
            case FIND_GAME_COMPUTER:
                doFindGameComputer(net, client);
                break;
            case GIVE_TEAM:
                doTeamSelection(net, client);
                break;
            case GIVE_BATTLE_SELECT:
                doBattleSelection(net, client);
                break;
            case LEAVE_GAME:
                doLeaveGame(net, client);
                break;
        }
    }
    
        private void doLeaveGame(NetworkWrapper net, ConnectionToClient client)
    {
        Player player = connectionUsers.get(client);
        Game game = gMan.getGameForPlayer(player);
        if (game != null)
        {
            if (game.state == GameStates.GAME_OVER)
            {
                gMan.removeGame(game);
            }
        }
    }
    
    private void doLoadPokemon(NetworkWrapper net, ConnectionToClient client)
    {
        try
        {
            List<Pokemon> pokemonList = DBConnection.getAllPokeStatsAndMult();
            NetworkWrapper pokemonData = new NetworkWrapper(
                    ServerCommand.POKEMON_DATA, pokemonList);
            client.sendToClient(pokemonData);
            System.out.println("sent data");
        } catch (Exception ex)
        {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void doBattleSelection(NetworkWrapper net, ConnectionToClient client)
    {
        try
        {
            Player player = connectionUsers.get(client);
            Pokemon pmon = (Pokemon) net.getObject();

            gMan.setPlayerBattleSelection(player, pmon);
        } catch (IOException ex)
        {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doTeamSelection(NetworkWrapper net, ConnectionToClient client)
    {
        Player player = connectionUsers.get(client);
        ArrayList<Pokemon> team = (ArrayList<Pokemon>) net.getObject();
        gMan.setPlayerTeam(player, team);

    }

    private void doLogin(NetworkWrapper net, ConnectionToClient client)
    {
        //IF player exists in map with same username
        //THEN return error to client
        //ELSE IF player exists in database
        //THEN load existing player data
        //      create player object
        //      put player in user connections map
        //ELSE
        //      insert new player information into database
        //      create new player object
        //      put player into user connections map.
        String username = (String) net.getObject();
        boolean userLoggedIn = false;
        for (Player player : connectionUsers.values())
        {
            if (player.getName().equals(username))
            {
                try
                {
                    NetworkWrapper failedLogin = new NetworkWrapper(
                            ServerCommand.ERROR_LOGIN, "Profile is already logged in.");
                    client.sendToClient(failedLogin);
                    userLoggedIn = true;
                    break;
                } catch (IOException ex)
                {
                    Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (!userLoggedIn)
        {
            try
            {
                Player player;
                if (DBConnection.checkUser(username))
                {
                    player = DBConnection.getUser(username);
                    addPlayerAndConnection(player, client);
                } else
                {
                    DBConnection.createUser(username);
                    player = DBConnection.getUser(username);
                    addPlayerAndConnection(player, client);
                }
                player.client = client;
                NetworkWrapper logNet = new NetworkWrapper(
                        ServerCommand.SUCCESS_LOGIN, player.getProfile());
                client.sendToClient(logNet);
                //if user in db
                //load from db ad add to map
                //else 
                //add new user to db and and map
                //sent login success to client with player object
            } catch (Exception ex)
            {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void doFindGameHuman(NetworkWrapper net, ConnectionToClient client)
    {
        try
        {
            Player player = connectionUsers.get(client);
            gMan.addPlayerToHumanGame(player);
        } catch (IOException ex)
        {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doFindGameComputer(NetworkWrapper net, ConnectionToClient client)
    {

        Player player = connectionUsers.get(client);
        gMan.addPlayerToComputerGame(player);

    }

    public void addPlayerAndConnection(Player player, ConnectionToClient client)
    {
        userConnection.put(player, client);
        connectionUsers.put(client, player);
    }

    public void removeFromMaps(Player player)
    {
        connectionUsers.remove(userConnection.get(player));
        userConnection.remove(player);
    }

    public void removeFromMaps(ConnectionToClient client)
    {
        userConnection.remove(connectionUsers.get(client));
        connectionUsers.remove(client);
    }

    @Override
    public void clientDisconnected(ConnectionToClient client)
    {
        Player disconnectedPlayer = connectionUsers.get(client);
        Game game = gMan.getGameForPlayer(disconnectedPlayer);
        if (game != null && game.state != GameStates.GAME_OVER)
        {
            game.getPlayers().remove(disconnectedPlayer);
            if(game.getPlayers().size() > 0)
            {
                Player remainingPlayer = game.getPlayers().get(0);
                NetworkWrapper net = new NetworkWrapper(ServerCommand.PLAYER_LEFT, disconnectedPlayer + "has disconnected. You win");
                remainingPlayer.send(net);
            }
            else
            {
                gMan.removeGame(game);
            }
        }
        
        removeFromMaps(client);
    }

    public static void main(String[] args)
    {

        GameServer server = new GameServer(6666);
        try
        {
            server.listen();
        } catch (IOException ex)
        {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
}
