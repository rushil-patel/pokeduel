package pokeduelserver;


/**
 *
 * @author rushi_000
 */
public class Pokemon {
    
    public int id;
    public String name;
    public int cost;
    public int[] stats;
    public String sprite;
    public int[] resistances;
    public int[] types;
    public int numTypes;
    public boolean isAlive;
    
    public Pokemon(int id, String name, int cost, int[] stats, String sprite,
            int[] resistances, int[] types, int numTypes, boolean isAlive)
    {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.stats = stats;
        this.sprite = sprite;
        this.resistances = resistances;
        this.types = types;
        this.numTypes = numTypes;
        this.isAlive = isAlive;
    }
    
}
