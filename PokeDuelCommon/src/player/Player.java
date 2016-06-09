package player;

import com.lloseng.ocsf.server.ConnectionToClient;
import commands.ServerCommand;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pokemon.Pokemon;
import wrappers.NetworkWrapper;

public class Player implements Serializable
{

    private int id;
    private String username;
    private ArrayList<Pokemon> team;
    public ConnectionToClient client;
    public Pokemon currentPokemon;
    public int wins;
    public int losses;

    public Player(int id, String username, ConnectionToClient client, int wins, int losses)
    {
        this.id = id;
        this.username = username;
        this.client = client;
        this.team = new ArrayList<>();
        this.wins = wins;
        this.losses = losses;
    }

    public Player(int id, String username, int wins, int losses)
    {
        this.id = id;
        this.username = username;
        this.client = null;
        this.team = new ArrayList<>();
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return username;
    }

    public void requestTeam()
    {

        NetworkWrapper teamCommand = new NetworkWrapper(ServerCommand.START_TEAM_SELECT,
                null);
        send(teamCommand);

    }

    public void requestSelection()
    {

        NetworkWrapper teamCommand = new NetworkWrapper(ServerCommand.GET_BATTLE_SELECT,
                null);
        send(teamCommand);

    }

    public void setTeam(ArrayList<Pokemon> newTeam)
    {

        this.team.clear();
        for (Pokemon pokemon : newTeam)
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
        for (Pokemon pokemon : team)
        {
            if (pokemon.isAlive)
            {
                numAlive += 1;
            }
        }
        return numAlive;
    }

    public void updatePokemon(Pokemon newPokemon)
    {
        for (int idx = 0; idx < team.size(); idx++)
        {
            if (team.get(idx).id == newPokemon.id)
            {
                newPokemon.isAlive = false;
                team.set(idx, newPokemon);
            }
        }
    }

    public void send(NetworkWrapper wrapper)
    {
        try
        {
            client.sendToClient(wrapper);
        } catch (IOException ex)
        {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Profile getProfile()
    {
        return new Profile(this);
    }
    
    
}
