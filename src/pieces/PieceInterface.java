package pieces;

import java.awt.Graphics;
import java.util.List;

import board.Board;

/**
 * Interface définissant le contrat pour toutes les pièces d'échecs.
 * Utilise le principe d'abstraction pour définir les comportements communs.
 */
public interface PieceInterface {
    public String getType();
    public String getPosition();
    public int getValue();
    public void move(String newPosition, Board board);
    public PieceColor getColor();
    public void render(Graphics g);
    public void update();
    public List<String> getPossibleMoves(Board board);
}
