package pokeduelserver;

import player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private List<Player> players;
    public GameStates state;
    
    public Game(List<Player> players) {
        players = new ArrayList();
        state = GameStates.WAITING_JOIN_PLAYER_1;
    }
    
    public final List<Player> getPlayers()
    {
        return this.players;
    }
    
    public Player doBattle()
    {
        Player p1 = players.get(0);
        Player p2 = players.get(1);
        
        Pokemon poke1 = p1.currentPokemon;
        Pokemon poke2 = p2.currentPokemon;
              
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
            
            //update pokemon status
            p2.updatePokemon(poke2);
            return p1;
        }
        else if ((poke1Attack - poke2Defense) < (poke2Attack - poke1Defense))
        {
            //update pokemon status
            p1.updatePokemon(poke1);
            return p2;
        }
        else {
            int value = new Random().nextInt(2);
            
            if (value > 1)
            {
                return p2;
            }
            else
            {
                return p1;
            }
        }
        

    }
     
    public void addPlayer(Player player) {
        players.add(player);
    }
}
