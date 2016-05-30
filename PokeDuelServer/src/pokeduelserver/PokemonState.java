package pokeduelserver;

public enum PokemonState {
    DEAD(0), ALIVE(0);

    private int state;

    private PokemonState(int s) {
        state = s;
    }
    
    public int getState() {
        return state;
    }
}