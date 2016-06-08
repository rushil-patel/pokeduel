package ui;

import client.GameClient;
import commands.ClientCommand;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import player.Player;
import player.Profile;
import wrappers.NetworkWrapper;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author seongbo
 */
public class RequestBattlePanel extends javax.swing.JPanel {

    GameClient client;
    /**
     * Creates new form newJPanel
     */
    public RequestBattlePanel(GameClient client, Profile player) {
        this.client = client;
        initComponents();
        
        this.usernameLabel.setText(player.name);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        usernameLabel = new javax.swing.JLabel();
        winText = new javax.swing.JLabel();
        lossText = new javax.swing.JLabel();
        winCount = new javax.swing.JLabel();
        lossCount = new javax.swing.JLabel();
        usersnameText = new javax.swing.JLabel();
        humanBattleButton = new javax.swing.JButton();
        computerBattleButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setName(""); // NOI18N

        usernameLabel.setText("Username:  ");

        winText.setText("Win:  ");

        lossText.setText("Loss: ");

        winCount.setText("0");

        lossCount.setText("0");

        usersnameText.setText(" ");

        humanBattleButton.setText("Battle Human");
        humanBattleButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                humanBattleButtonActionPerformed(evt);
            }
        });

        computerBattleButton.setText("Battle Computer");
        computerBattleButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                computerBattleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(163, Short.MAX_VALUE)
                .addComponent(humanBattleButton)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(winText)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lossText)
                                    .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(usersnameText, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(winCount, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lossCount, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(207, 207, 207)
                        .addComponent(computerBattleButton)))
                .addGap(222, 222, 222))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(230, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usersnameText))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(winText)
                    .addComponent(winCount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lossText)
                    .addComponent(lossCount))
                .addGap(99, 99, 99)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(humanBattleButton)
                    .addComponent(computerBattleButton))
                .addGap(182, 182, 182))
        );

        usernameLabel.getAccessibleContext().setAccessibleName("usernameLabel");
        usersnameText.getAccessibleContext().setAccessibleName("usernameValue");
    }// </editor-fold>//GEN-END:initComponents

    private void humanBattleButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_humanBattleButtonActionPerformed
    {//GEN-HEADEREND:event_humanBattleButtonActionPerformed
        try
        {
            ((JButton)evt.getSource()).setEnabled(false);
            client.sendToServer(new NetworkWrapper(
                    ClientCommand.FIND_GAME_HUMAN, null));
        } catch (IOException ex)
        {
            Logger.getLogger(RequestBattlePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_humanBattleButtonActionPerformed

    private void computerBattleButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_computerBattleButtonActionPerformed
    {//GEN-HEADEREND:event_computerBattleButtonActionPerformed
         try
        {
            ((JButton)evt.getSource()).setEnabled(false);
            client.sendToServer(new NetworkWrapper(
                    ClientCommand.FIND_GAME_COMPUTER, null));
        } catch (IOException ex)
        {
            Logger.getLogger(RequestBattlePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_computerBattleButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton computerBattleButton;
    private javax.swing.JButton humanBattleButton;
    private javax.swing.JLabel lossCount;
    private javax.swing.JLabel lossText;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JLabel usersnameText;
    private javax.swing.JLabel winCount;
    private javax.swing.JLabel winText;
    // End of variables declaration//GEN-END:variables
}
