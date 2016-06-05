package player;
import com.lloseng.ocsf.server.ConnectionToClient;
import java.util.ArrayList;
import pokemon.Pokemon;

public class Player {
    private int id;
    private String username;
    private ArrayList<Pokemon> team;
    public final ConnectionToClient client;
    public Pokemon currentPokemon;
    
    public Player(int id, String username, ConnectionToClient client) {
        this.id = id;
        this.username = username;
        this.client = client;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return username;
    }
    
    public void setTeam(ArrayList<Pokemon> newTeam) {
        
        this.team.clear();
        for(Pokemon pokemon: newTeam)
        {
            team.add(pokemon);
        }
    }
    
    public ArrayList<Pokemon> getTeam()
    {
        return team;
    }
    
    public int alivePokemons()
    {
        int numAlive = 0;
        for(Pokemon pokemon: team)
        {
            if(pokemon.isAlive)
            {
                numAlive += 1;    
            }
        }
        return numAlive;
    }
    
    public void updatePokemon(Pokemon newPokemon)
    {
        for(int idx = 0; idx < team.size(); idx++)
        {
            if(team.get(idx).id == newPokemon.id)
            {
                team.get(idx).isAlive = false;
            }
        }
    }
}
