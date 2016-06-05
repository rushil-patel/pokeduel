
package client;
import ui.Display;
import com.lloseng.ocsf.client.ObservableClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.Login;

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
        displayFrame.setContentPane(new Login());
    }
    
    
    
    public static void main(String[] args)
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
        
        
    }
}
