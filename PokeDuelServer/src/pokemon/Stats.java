package pokemon;

public enum Stats {
    HP(0), ATTACK(1), DEFENSE(2), S_ATTACK(3), S_DEFENSE(4), SPEED(5);

    private int value;

    private Stats(int v) {
        value = v;
    }
    
    public int getValue() {
        return value;
    }
}