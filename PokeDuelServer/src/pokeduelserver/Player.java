package pokeduelserver;
import java.util.ArrayList;

public class Player {
    private int id;
    private String username;
    public ArrayList<Pokemon> team;
    
    public Player(int id, String username) {
        this.id = id;
        this.username = username;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return username;
    }
    
    public void setTeam(ArrayList<Pokemon> team) {
        
    }
}
