package pokeduelserver;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private int playerOneWins;
    private int playerTwoWins;
    
    public Game(List<Player> players) {
        players = new ArrayList();
        playerOneWins = 0;
        playerTwoWins = 0;
    }
    
    public int doBattle(Selection s1, Selection s2) {
        Pokemon poke1 = null, poke2 = null;
        
        for (Player player: players) {
            if (s1.playerId == player.getId()) {
                for (Pokemon poke: player.team) {
                    if (s1.pId == poke.id)
                        poke1 = poke;
                }
                if (poke1 == null)
                    return s2.playerId;
            }
            else
                return s2.playerId;
        }
        
        for (Player player: players) {
            if (s2.playerId == player.getId()) {
                for (Pokemon poke: player.team) {
                    if (s2.pId == poke.id)
                        poke2 = poke;
                }
                if (poke2 == null)
                    return s1.playerId;
            }
            else
                return s1.playerId;
        }
        
        int poke1Attack = 0, poke1Defense = 0;
        int poke2Attack = 0, poke2Defense = 0;
        for (int type: poke1.types) {
            poke1Attack +=  poke1.stats[Stats.ATTACK.getValue()]*poke2.resistances[type];
            poke2Defense += poke2.stats[Stats.DEFENSE.getValue()]*poke1.resistances[type];     
        }
        
        poke1Attack = poke1Attack * (1/poke1.numTypes);
        poke2Defense = poke2Defense * (1/poke2.numTypes);
        
        for (int type: poke2.types) {
            poke2Attack +=  poke2.stats[Stats.ATTACK.getValue()]*poke1.resistances[type];
            poke1Defense += poke1.stats[Stats.DEFENSE.getValue()]*poke2.resistances[type]; 
        }
        
        poke2Attack = poke2Attack * (1/poke2.numTypes);
        poke1Defense = poke1Defense * (1/poke1.numTypes);
        
        if ((poke1Attack - poke2Defense) > (poke2Attack - poke1Defense)) {
            playerOneWins++;
            return s1.playerId;
        }
        else {
            playerTwoWins++;
            return s2.playerId;
        }
    }
    
    public void addPlayer(Player player) {
        players.add(player);
    }
}
