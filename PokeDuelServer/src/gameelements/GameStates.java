
package gameelements;

/**
 *
 * @author rushi_000
 */
public enum GameStates {
    
    /**
     * waiting for the first player to join
     */
    WAITING_JOIN_PLAYER_1,
    /**
     * waiting for second player to join
     */
    WAITING_JOIN_PLAYER_2,
    /**
     * waiting for two teams to be received
     */
    WAITING_TWO_TEAMS,
    /**
     * waiting for the last team to be received
     */
    WAITING_ONE_TEAM,
    /**
     * waiting for two pokemon battle selection
     */
    WAITING_TWO_BATTLE,
    /**
     * waiting for one pokemon battle selection
     */
    WAITING_ONE_BATTLE,
    
    GAME_OVER;
}
