
package client;
import ui.Display;
import com.lloseng.ocsf.client.ObservableClient;
import commands.ClientCommand;
import commands.ServerCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pokemon.Pokemon;
import ui.LoginPanel;
import wrappers.NetworkWrapper;

/**
 *
 * @author rushi_000
 */
public class GameClient extends ObservableClient
{
    
    Display displayFrame;
    public GameClient(String host, int port)
    {
        super(host, port);
        
        displayFrame = new Display();
        displayFrame.setVisible(true);
        displayFrame.setContentPane(new LoginPanel());
    }
    
    @Override 
    public void handleMessageFromServer(Object message)
    {
        NetworkWrapper net = (NetworkWrapper) message;
        
         switch ((ServerCommand) net.getCommand())
        {
            case POKEMON_DATA:
                doLoadPokemon((ArrayList<Pokemon>) net.getObject());
                break;
           
        }
    }

    private void doLoadPokemon(List<Pokemon> list)
    {
        // finish up
        //store data  in model class
    }
    
    
    public static void main(String[] args) throws IOException
    {
        String hostaddr = "localhost";
        int port = 6666;
        GameClient client = new GameClient(hostaddr, port);
        try
        {
            client.openConnection();
        } catch (IOException ex)
        {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        client.sendToServer(new NetworkWrapper(ClientCommand.LOAD_POKEMON, port));
        
        
    }
}
