package client;

import ui.Display;
import com.lloseng.ocsf.client.ObservableClient;
import commands.ClientCommand;
import commands.ServerCommand;
import java.awt.BorderLayout;
import java.io.IOException;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import model.BattleModel;
import model.PokemonTableModel;
import player.Player;
import player.Profile;
import pokemon.Pokemon;
import ui.GameScreenPanel;
import ui.LoginPanel;
import ui.PokeTable;
import ui.PokemonRenderer;
import ui.RequestBattlePanel;
import ui.TeamSelectionPanel;
import wrappers.NetworkWrapper;

/**
 *
 * @author rushi_000
 */
public class GameClient extends ObservableClient
{

    Display displayFrame;
    PokemonTableModel pokeTableModel;
    BattleModel bModel;
    public GameClient(String host, int port)
    {
        super(host, port);
        bModel = new BattleModel();
        displayFrame = new Display();
        displayFrame.setVisible(true);
        displayFrame.setContentPane(new LoginPanel(this));
        displayFrame.pack();
        
        displayFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        displayFrame.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                if (JOptionPane.showConfirmDialog(displayFrame,
                        "Are you sure to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    try
                    {
                        closeConnection();
                        System.exit(0);

                    } catch (IOException ex)
                    {
                        Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });


    }

    @Override
    public void handleMessageFromServer(Object message)
    {
        try
        {
            NetworkWrapper net = (NetworkWrapper) message;
            System.out.println(net.getCommand().toString());
            switch ((ServerCommand) net.getCommand())
            {
                case POKEMON_DATA:
                    doLoadPokemon((ArrayList<Pokemon>) net.getObject());
                    break;
                case SUCCESS_LOGIN:
                    doLoadMenuPanel((Profile) net.getObject());
                case OPPONENT_FOUND:
                    break;
                case START_TEAM_SELECT:
                    doLoadTeamSelectionPanel();
                    break;
                case GET_TEAM:
                    break;
                case START_BATTLE:
                    doLoadGameScreenPanel();
                    //bModel.setPlayerProfile((Profile)net.getObject());
                    break;
                case GET_BATTLE_SELECT:
                    bModel.doNewRound();
                    break;
                case GAME_OVER:
                    JOptionPane.showMessageDialog(displayFrame.getContentPane(), (String)net.getObject());
                    this.sendToServer(new NetworkWrapper(ClientCommand.LEAVE_GAME, null));
                    break;
                case PLAYER_LEFT:
                    break;
                case ERROR_LOGIN:
                    break;
                case BATTLE_RESULT:
                    bModel.setRoundWinner((Profile) net.getObject());
                    break;
                case PLAYER_UPDATE:
                    bModel.setPlayerProfile((Profile) net.getObject());
                    break;
                case OPPONENT_UPDATE:
                    bModel.setOpponentProfile((Profile) net.getObject());
                    break;
            }
        } catch (IOException ex)
        {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void doLoadGameScreenPanel()
    {
        GameScreenPanel gsPanel = new GameScreenPanel(this);
        bModel.deleteObservers();
        bModel.addObserver(gsPanel);
        displayFrame.setContentPane(gsPanel);
        displayFrame.pack();
    }

    public void doLoadMenuPanel(Profile playerProfile)
    {
        displayFrame.setContentPane(new RequestBattlePanel(this, playerProfile));
        
        displayFrame.pack();
    }
    
    private void doLoadTeamSelectionPanel()
    {
        displayFrame.getContentPane().removeAll();
        displayFrame.setContentPane(
                new TeamSelectionPanel(this, pokeTableModel, new PokemonRenderer()));
        displayFrame.pack();
        
        
        PokeTable table = new PokeTable(new PokemonRenderer());
        table.setModel(pokeTableModel);
        table.setOpaque(false);
        //Display temp = new Display();
        //temp.setVisible(true);

        //temp.setLayout(new BorderLayout());
       // temp.getContentPane().add(table);
       // temp.pack();
       
       // displayFrame.dispose();
        
        
    }

    private void doLoadPokemon(List<Pokemon> list)
    {
        pokeTableModel = new PokemonTableModel(list, 5, 31);
        // finish up
        //store data  in model class
        
    }

    public static void main(String[] args) throws IOException
    {
        String hostaddr = "localhost";
        int port = 6666;
        GameClient client = new GameClient(hostaddr, port);
        try
        {
            client.openConnection();
        } catch (IOException ex)
        {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        client.sendToServer(new NetworkWrapper(ClientCommand.LOAD_POKEMON, port));


    }
}
