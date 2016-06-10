package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import pokemon.Pokemon;

/**
 * This class represents the contents and layout of the visual board element.
 * The board is a grid with box subsections that may hold Sprite objects.
 * @author rpatel28
 */
public class PokeTable extends JTable
{

    private ImageIcon bgImage = null;
    private static final int kROWHEIGHT = 63;
    private static final int kCOLUMNWIDTH = 63;

    /**
     * Creates a PokeTable with the specified renderer. The renderer
     * can be changed through the superclass.
     * @param renderer the renderer for processing the graphic to be displayed
     */
    public PokeTable(PokemonRenderer renderer)
    {

        setDefaultRenderer(Pokemon.class, renderer);
        setFillsViewportHeight(true);
        setRowHeight(kROWHEIGHT);        
        setOpaque(false);
    }

    /**
     * Sets and loads the background image of the table.
     * @param imageName name of the Image file.
     */
    public void setBackgroundImage(String imageName)
    {
        bgImage = new ImageIcon("res" + File.separator +imageName);
        repaint();
    }
    
    /**
     * Prepares the renderer by querying the data model for the
     * value and selection state
     * @param renderer renderer to be prepared
     * @param row number of rows in the data model
     * @param column number of columns in the data model
     * @return Component under the event location.
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
    {
        Component comp = super.prepareRenderer(renderer, row, column);
        // We want renderer component to be 
        //transparent so background image is visible 
        if (comp instanceof JComponent)
        {
            ((JComponent) comp).setOpaque(false);
        }
        return comp;
    }

    /**
     * Draws the background image for the table component.
     * @param ctxt the graphics context in which to paint.
     */
    @Override
    public void paint(Graphics ctxt)
    {
        // tile the background image 
        Dimension dim = getSize();
        //IF image is not null then fill table background with image
        if (bgImage != null && bgImage.getImage() != null)
        {
            ctxt.drawImage(bgImage.getImage(), 0, 0, null, null);
        }
        // Now let the paint do its usual work 
        super.paint(ctxt);
    }
}
