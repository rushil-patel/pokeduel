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
import pokemon.Pokemon;
import wrappers.NetworkWrapper;


public class GameServer  extends ObservableServer {

    private GameManager gMan;
    
    public List<Game> games;
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
        
        switch((ClientCommand)net.getCommand())
        {
            case LOGIN:
                //doLogin
                break;
            case FIND_GAME:
                //doFindGame
                break;
            case GIVE_TEAM:
                //doTeamSelect
                break;
            case GIVE_BATTLE_SELECT:
                //doBattleSelection
                break;
            case LEAVE_GAME:
                break;
        }
    }
    

    
    private void doBattleSelection(NetworkWrapper net, ConnectionToClient client) throws IOException
    {
        Player player = connectionUsers.get(client);
        Pokemon pmon = (Pokemon) net.getObject();
        
        gMan.setPlayerBattleSelection(player, pmon);
    }
    
    private void doTeamSelection(NetworkWrapper net, ConnectionToClient client)
    {
        Player player = connectionUsers.get(client);
        ArrayList<Pokemon> team  = (ArrayList<Pokemon>) net.getObject();
        gMan.setPlayerTeam(player, team);
        
    }
    

    private void doLogin(NetworkWrapper net, ConnectionToClient client) throws IOException
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
                NetworkWrapper failedLogin = new NetworkWrapper(
                        ServerCommand.ERROR_LOGIN, "Username is taken");
                client.sendToClient(failedLogin);
                userLoggedIn = true;
                break;
            }
        }
        
        if (!userLoggedIn)
        {
            //WAIT FOR SEONG 
            //if user in db
                //load from db ad add to map
            //else 
                //add new user to db and and map
            
            //sent login success to client with player object
            
        }
    }
    
    private void doFindGame(NetworkWrapper net, ConnectionToClient client) throws IOException
    {
        Player player = connectionUsers.get(client);
        gMan.addPlayerToGame(player);
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
        removeFromMaps(client);
    }
}
