
package Commands;

public enum ServerCommand implements Command {
    
    OPPONENT_FOUND,
    START_TEAM_SELECT,
    GET_TEAM,
    START_BATTLE,
    GET_BATTLE_SELECT,
    GAME_OVER,
    PLAYER_LEFT,
    ERROR_LOGIN,
    SUCCESS_LOGIN,
    BATTLE_RESULT,
    PLAYER_UPDATE,
    OPPONENT_UPDATE;
}
