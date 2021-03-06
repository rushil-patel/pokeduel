
package commands;

public enum ClientCommand implements Command {
    
    /**
     * Sending pokemon team
     */
    GIVE_TEAM,
    /**
     * Sending pokemon for battle
     */
    GIVE_BATTLE_SELECT,
    /**
     * Looking for game to join
     */
    FIND_GAME_HUMAN,
    /**
     * Login with username
     */
    LOGIN,
    /**
     * Left Game
     */
    LEAVE_GAME,
    FIND_GAME_COMPUTER,
    LOAD_POKEMON;
}
