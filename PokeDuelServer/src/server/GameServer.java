package server;

import Commands.ClientCommand;
import com.lloseng.ocsf.server.ObservableServer;
import com.lloseng.ocsf.server.ConnectionToClient;
import gameelements.GameManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pokeduelserver.Game;
import pokeduelserver.Player;
import wrappers.NetworkWrapper;


public class GameServer  extends ObservableServer {

    private GameManager gMan;
    
    public List<Game> games;
    private Map<ConnectionToClient, Player> connectionUser;
    private Map<Player, ConnectionToClient> userConnection;
    
    public GameServer(int port)
    {
        super(port);
        gMan = new GameManager();
        
        connectionUser = new HashMap<>();
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
                break;
            case GIVE_BATTLE_SELECT:
                break;
            case LEAVE_GAME:
                break;
        }
    }
    
    private void doLogin(NetworkWrapper net)
    {
        String username = (String) net.getObject();
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
    }
    
    private void doFindGame(NetworkWrapper net)
    {
        //GameManager.addPlayerToGame()
    }
    
    public void addPlayerAndConnection(Player player, ConnectionToClient client)
    {
        userConnection.put(player, client);
        connectionUser.put(client, player);
    }
    
    public void removeFromMaps(Player player)
    {
        connectionUser.remove(userConnection.get(player));
        userConnection.remove(player);
    }
    public void removeFromMaps(ConnectionToClient client)
    {
        userConnection.remove(connectionUser.get(client));
        connectionUser.remove(client);
    }
    
    @Override
    public void clientDisconnected(ConnectionToClient client)
    {
        removeFromMaps(client);
    }
}
