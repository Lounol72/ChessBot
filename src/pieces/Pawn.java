package pieces;

import java.awt.Graphics;
import utilz.*;

public class Pawn extends Piece implements PieceInterface {
    private final int value = 1;

    public Pawn(String position, String color) {
        super(position, color);
    }
    @Override
    public String getType() {
        return "PAWN";
    }
    @Override
    public String getPosition() {
        return position;
    }

    @Override
    public int getValue() {
        return value;
    }
    @Override
    public void move(String newPosition) {
        if (isValidMove(newPosition) && isPathClear(newPosition)) {
            this.position = newPosition;
        }
    }
    private boolean isValidMove(String newPosition) {
        // TODO Implement pawn-specific movement rules
        return true;
    }
    private boolean isPathClear(String newPosition) {
        // TODO Implement path checking logic
        return true;
    }
    @Override
    public String getColor() {
        return color;
    }
    @Override
    public void render(Graphics g) {
        // TODO Implement rendering logic
    }
    @Override
    public void update() {
        // TODO Implement update logic if necessary
    }
}
