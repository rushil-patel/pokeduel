package ui;

import client.GameClient;
import commands.ClientCommand;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import model.PokemonTableModel;
import pokemon.Pokemon;
import wrappers.NetworkWrapper;

/**
 *
 * @author rushi_000
 */
public class TeamSelectionPanel extends javax.swing.JPanel
{

    private PokeTable allPokemonTable;
    private JScrollPane allScrollPane;
    private JPanel titlePanel;
    private JPanel bottomPanel;
    private PokemonTableModel model;
    public List<Pokemon> selectedPokemon;
    private PokemonTableModel selectedModel;
    private PokeTable selectedTable;
    private JLabel teamCostLabel;
    private JButton lockInButton;
    private final GameClient client;
    private boolean hasLockedIn = false;
    
    /**
     * Creates new form TeamSelectionPanel
     */
    public TeamSelectionPanel(final GameClient client,
            PokemonTableModel model, PokemonRenderer renderer)
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.model = model;
        this.client = client;
        selectedPokemon = new ArrayList<Pokemon>();
        selectedModel = new PokemonTableModel(selectedPokemon, 6, 1);
        selectedTable = new PokeTable(renderer);
        selectedTable.setRowHeight(100);
        selectedTable.setShowGrid(false);
        selectedTable.setShowHorizontalLines(false);
        selectedTable.setShowVerticalLines(false);
        
        teamCostLabel = new JLabel("Cost: 0/200");
        lockInButton = new JButton("Lock In");
        
        lockInButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    if (selectedPokemon.size() > 0)
                    {
                        hasLockedIn = true;

                        client.sendToServer(new NetworkWrapper(ClientCommand.GIVE_TEAM,
                                selectedPokemon));
                        ((JButton)e.getSource()).setEnabled(false);
                    }
                    else{
                        JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(),
                                "...just why?? Select at least 1 Pokemon.");
                    }
                } catch (IOException ex)
                {
                    Logger.getLogger(TeamSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        titlePanel = new JPanel();
        titlePanel.add(new JLabel("Choose your Team"));
        
        this.add(titlePanel);

        allPokemonTable = new PokeTable(renderer);
        allPokemonTable.setModel(model);
        allPokemonTable.setTableHeader(null);
        allPokemonTable.setShowGrid(false);
        allPokemonTable.setShowHorizontalLines(false);
        allPokemonTable.setShowVerticalLines(false);
        allPokemonTable.setPreferredScrollableViewportSize(new Dimension(800, 400));
        allPokemonTable.setPreferredScrollableViewportSize(new Dimension(800, 400));
        allPokemonTable.addMouseListener(new MouseAdapter()
        
        {
            @Override
            public void mousePressed(MouseEvent evt)
            {
                Point clickPoint = evt.getPoint();
                JTable table = (JTable) evt.getSource();
                int row = table.rowAtPoint(clickPoint);
                int col = table.columnAtPoint(clickPoint);
                if (!hasLockedIn)
                {
                    selectPokemonAt(row, col);
                }

            }
        });

        
        allScrollPane = new JScrollPane(allPokemonTable);
        this.add(allScrollPane);
        Component padding = Box.createRigidArea(new Dimension(this.getWidth(), 50));
        this.add(padding);
        model.fireTableChanged(null);
        
        this.add(lockInButton);
        this.add(teamCostLabel);

        bottomPanel = new JPanel();
        bottomPanel.add(selectedTable);
        
        bottomPanel.validate();
        this.add(selectedTable);
        validate();
        model.fireTableChanged(null);

    }

    private void selectPokemonAt(int row, int col)
    {
        Pokemon selection = (Pokemon) model.getValueAt(row, col);
        
        int totalCost = 0;
        for(Pokemon poke: selectedPokemon)
        {
            totalCost += poke.cost;
        }
        
        
        
            if (selectedPokemon.contains(selection))
            {
                selectedPokemon.remove(selection);
                totalCost -= selection.cost;
            } else if(totalCost + selection.cost < 200)
            {
                selectedPokemon.add(selection);
                totalCost += selection.cost;
            }
            
            teamCostLabel.setText("Cost: "+totalCost+"/200");

        selectedModel = new PokemonTableModel(selectedPokemon, 6, 1);
        selectedTable.setModel(selectedModel);
        selectedModel.fireTableChanged(null);


    }
}