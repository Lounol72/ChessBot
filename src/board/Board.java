package board;

import pieces.Pawn;
import pieces.PieceInterface;
import pieces.Piece;
import java.awt.Graphics;

public class Board {
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        setupBoard();
    }

    public Piece getPieceAt(String position) {
        int row = 8 - Character.getNumericValue(position.charAt(1));
        int col = position.charAt(0) - 'a';
        return board[row][col];
    }

    private void setupBoard() {
        // Initialize pieces on the board
        StringBuilder position = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            position.setLength(0);
            position.append((char) ('a' + i)).append("2");
            board[1][i] = new Pawn(position.toString(), "WHITE");
            position.setLength(0);
            position.append((char) ('a' + i)).append("7");
            board[6][i] = new Pawn(position.toString(), "BLACK");
        }
    }

    public void render(Graphics g) {
        // Render the board and pieces
    }

    public void update() {
        // Update board state if necessary
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != null) {
                    sb.append(board[row][col].getType().charAt(0));
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
