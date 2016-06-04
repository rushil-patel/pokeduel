
package gameelements;

import com.lloseng.ocsf.server.ConnectionToClient;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import pokeduelserver.Game;
import pokeduelserver.Player;


public class GameManager {

    public List<Game> games;

    
    public GameManager()
    {
        games = new ArrayList();
        //create a new empty game
        games.add(new Game(new ArrayList()));
    }
   
    public void addPlayerToGame()
    {
        
    }
    
}
