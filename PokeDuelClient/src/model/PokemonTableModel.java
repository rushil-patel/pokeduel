
package model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import pokemon.Pokemon;

/**
 *
 * @author rushi_000
 */
public class PokemonTableModel extends AbstractTableModel
{
    private Pokemon[][] grid;
    private int cols;
    public PokemonTableModel(List<Pokemon> pokemonList, int cols, int numRows)
    {
        this.cols = cols;
        grid = new Pokemon[numRows][cols];
        int row = -1;
        for (int idx = 0; idx < pokemonList.size(); idx++)
        {
            if(idx % cols == 0)
            {
                row += 1;
            }
            grid[row][idx % cols] = pokemonList.get(idx);
        }
    }
    @Override
    public int getRowCount()
    {
       return grid.length;
    }

    @Override
    public int getColumnCount()
    {
        return cols;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return grid[rowIndex][columnIndex];
    }

    @Override
    public Class getColumnClass(int col)
    {
        return Pokemon.class;
    }
}
