package pokeduelserver;


/**
 *
 * @author rushi_000
 */
public enum Types 
{
    NORMAL(0), FIGHT(1), FLYING(2), POISON(3), GROUND(4),
    ROCK(5), BUG(6), GHOST(7), STEEL(8), FIRE(9), WATER(10),
    GRASS(11), ELECTR(12),
    PSYCHC(13), ICE(14), DRAGON(15), DARK(16);
    
    private final int value;
    
    private Types(int value)
    {
        this.value = value;
    }
    public int getValue()
    {
        return value;
    }
}
