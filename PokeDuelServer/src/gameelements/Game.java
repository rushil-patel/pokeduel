package gameelements;

import commands.ServerCommand;
import pokemon.Pokemon;
import pokemon.Stats;
import player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import wrappers.NetworkWrapper;

public class Game {
    private List<Player> players;
    public GameStates state;
    
    public Game(List<Player> players) {
        this.players = players;
        state = GameStates.WAITING_JOIN_PLAYER_1;
    }
    
    public final List<Player> getPlayers()
    {
        return this.players;
    }
    
    public void setPlayerPokemon(Pokemon pokemon, int pId)
    {
        if(players.get(0).getId() == pId)
        {
            players.get(0).currentPokemon = pokemon;
        }
        else
        {
            players.get(1).currentPokemon = pokemon;
        }
        
        if (state == GameStates.WAITING_TWO_BATTLE)
                {
                    state = GameStates.WAITING_ONE_BATTLE;
                } 
                else if (state == GameStates.WAITING_ONE_BATTLE)
                {
                    players.get(0).send(
                            new NetworkWrapper(ServerCommand.OPPONENT_UPDATE,
                            players.get(1).getProfile()));
                    players.get(1).send(
                            new NetworkWrapper(ServerCommand.OPPONENT_UPDATE,
                            players.get(0).getProfile()));
                    
                    Player winner = doBattle();

                    players.get(0).send(
                            new NetworkWrapper(ServerCommand.BATTLE_RESULT,
                            winner));
                    players.get(1).send(
                            new NetworkWrapper(ServerCommand.BATTLE_RESULT,
                            winner));
                    
                    players.get(0).send(
                            new NetworkWrapper(ServerCommand.PLAYER_UPDATE,
                            players.get(0).getProfile()));
                    players.get(1).send(
                            new NetworkWrapper(ServerCommand.PLAYER_UPDATE,
                            players.get(1).getProfile()));
                    
                    state = GameStates.WAITING_TWO_BATTLE;
                    
                    players.get(0).requestSelection();
                    players.get(1).requestSelection();
                }
        
    }
    public void setPlayerTeam(ArrayList<Pokemon> team, int pId)
    {
        if(players.get(0).getId() == pId)
        {
            players.get(0).setTeam(team);
        }
        else
        {
            players.get(1).setTeam(team);
        }
        
        if (state == GameStates.WAITING_ONE_TEAM)
        {
            state = GameStates.WAITING_TWO_BATTLE;
            NetworkWrapper net = new NetworkWrapper(ServerCommand.START_BATTLE, null);
            NetworkWrapper update1 = new NetworkWrapper(ServerCommand.PLAYER_UPDATE, players.get(0).getProfile());
            NetworkWrapper update2 = new NetworkWrapper(ServerCommand.PLAYER_UPDATE, players.get(1).getProfile());
            NetworkWrapper update3 = new NetworkWrapper(ServerCommand.PLAYER_UPDATE, players.get(1).getProfile());
            NetworkWrapper update4 = new NetworkWrapper(ServerCommand.PLAYER_UPDATE, players.get(0).getProfile());
            players.get(0).send(net);
            players.get(1).send(net);
            
            players.get(0).send(update1);
            players.get(1).send(update2);
            players.get(0).send(update3);
            players.get(1).send(update4);
            players.get(0).requestSelection();
            players.get(1).requestSelection();
        } else if (state == GameStates.WAITING_TWO_TEAMS)
        {
            state = GameStates.WAITING_ONE_TEAM;
        }

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
