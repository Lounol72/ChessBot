package board;

import java.awt.Color;
import java.awt.Graphics;

import pieces.Pawn;
import pieces.Piece;
import pieces.PieceColor;
import utilz.Constants;
import utilz.Position;

/**
 * Classe représentant l'échiquier.
 * Encapsule la logique de gestion du plateau et utilise Position pour les conversions.
 */
public class Board {
    private final Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        setupBoard();
    }

    /**
     * Récupère la pièce à une position donnée.
     * Utilise Position pour la conversion, respectant le principe DRY.
     * 
     * @param position La position en notation algébrique
     * @return La pièce à cette position, ou null si la case est vide
     */
    public Piece getPieceAt(String position) {
        Position pos = Position.fromAlgebraic(position);
        return board[pos.getRow()][pos.getCol()];
    }

    /**
     * Initialise le plateau avec les pièces en position de départ.
     * Utilise PieceColor pour la type-safety.
     */
    private void setupBoard() {
        // Initialiser les pions blancs sur la rangée 2
        for (int i = 0; i < 8; i++) {
            char col = (char) ('a' + i);
            String whitePosition = String.valueOf(col) + "2";
            Position whitePos = Position.fromAlgebraic(whitePosition);
            board[whitePos.getRow()][whitePos.getCol()] = new Pawn(whitePosition, PieceColor.WHITE);
            
            // Initialiser les pions noirs sur la rangée 7
            String blackPosition = String.valueOf(col) + "7";
            Position blackPos = Position.fromAlgebraic(blackPosition);
            board[blackPos.getRow()][blackPos.getCol()] = new Pawn(blackPosition, PieceColor.BLACK);
        }

        // Initialize black pieces
        // board[0][0] = new Rook("a8", "BLACK");
        // board[0][1] = new Knight("b8", "BLACK");
        // board[0][2] = new Bishop("c8", "BLACK");
        // board[0][3] = new Queen("d8", "BLACK");
        // board[0][4] = new King("e8", "BLACK");
        // board[0][5] = new Bishop("f8", "BLACK");
        // board[0][6] = new Knight("g8", "BLACK");
        // board[0][7] = new Rook("h8", "BLACK");

        // // Initialize white pieces
        // board[7][0] = new Rook("a1", "WHITE");
        // board[7][1] = new Knight("b1", "WHITE");
        // board[7][2] = new Bishop("c1", "WHITE");
        // board[7][3] = new Queen("d1", "WHITE");
        // board[7][4] = new King("e1", "WHITE");
        // board[7][5] = new Bishop("f1", "WHITE");
        // board[7][6] = new Knight("g1", "WHITE");
        // board[7][7] = new Rook("h1", "WHITE");
    }

    public void render(Graphics g) {
        // Render the board and pieces
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(col * Constants.SQUARE_SIZE, row * Constants.SQUARE_SIZE, Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
                if (board[row][col] != null) {
                    board[row][col].render(g);
                }
                

            }
        }
    }

    /**
     * Retourne la liste des coups possibles pour une pièce à une position donnée.
     * 
     * @param position La position de la pièce en notation algébrique
     * @return La liste des coups possibles, ou une liste vide si aucune pièce n'est présente
     */
    public java.util.List<String> getPossibleMoves(String position) {
        Piece piece = getPieceAt(position);
        if (piece == null) {
            return new java.util.ArrayList<>();
        }
        return piece.getPossibleMoves(this);
    }
    
    /**
     * Met à jour la position d'une pièce sur le plateau après un mouvement.
     * Gère la suppression de l'ancienne position et l'ajout à la nouvelle position.
     * 
     * @param oldPosition L'ancienne position en notation algébrique
     * @param newPosition La nouvelle position en notation algébrique
     * @param piece La pièce à déplacer
     */
    public void movePiece(String oldPosition, String newPosition, Piece piece) {
        Position oldPos = Position.fromAlgebraic(oldPosition);
        Position newPos = Position.fromAlgebraic(newPosition);
        
        // Retirer la pièce de l'ancienne position
        board[oldPos.getRow()][oldPos.getCol()] = null;
        
        // Placer la pièce à la nouvelle position (peut capturer une pièce adverse)
        board[newPos.getRow()][newPos.getCol()] = piece;
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
