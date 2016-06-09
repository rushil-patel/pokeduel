package ui;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import pokemon.Pokemon;

/**
 *
 * @author rushi_000
 */
public class PokemonRenderer extends DefaultTableCellRenderer
{

    public void setValue(Object value)
    {
        Pokemon poke = (Pokemon) value;

        if (poke != null)
        {

            ImageIcon icon = new ImageIcon("/" + poke.sprite);
            try
            {
                BufferedImage buff = ImageIO.read(this.getClass().getResourceAsStream("/"+poke.sprite));
                ImageIcon smallIcon = new ImageIcon(
                    buff.getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            setIcon(smallIcon);
            setText(null);
            } catch (IOException ex)
            {
                Logger.getLogger(PokemonRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }


        } else
        {
            setIcon(null);
            setText(null);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(getFont().deriveFont(50f));
        return this;
    }
}
