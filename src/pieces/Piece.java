package pieces;

import static utilz.HelpMethods.verifyParameters;

public abstract class Piece implements PieceInterface {
    protected String position;
    protected String color;

    public Piece(String position, String color) {
        verifyParameters(position, color);
        this.position = position;
        this.color = color;
    }

    public String getPosition() {
        return position;
    }

    public String getColor() {
        return color;
    }

    public abstract String getType();
    public abstract int getValue();
    public abstract void move(String newPosition);
    public abstract void render(java.awt.Graphics g);
    public abstract void update();
}
