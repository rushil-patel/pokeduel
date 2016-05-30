package pokeduelserver;
import java.util.ArrayList;

public class Player {
    private int id;
    private String username;
    private int win;
    private int loss;
    public ArrayList<Pokemon> team;
    
    public Player(int id, String username, int w, int l) {
        this.id = id;
        this.username = username;
        win = win;
        loss = loss;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return username;
    }
    
    public int getWin() {
        return win;
    }
    
    public int getLoss() {
        return loss;
    }
    
    public void setTeam(ArrayList<Pokemon> team) {
        
    }
}
