package pieces;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import board.Board;
import utilz.Constants;
import utilz.Position;

/**
 * Classe représentant un pion aux échecs.
 * Implémente toutes les règles spécifiques aux pions de manière optimisée et maintenable.
 * 
 * Principes POO respectés:
 * - Encapsulation: Les détails de validation sont privés
 * - Single Responsibility: Chaque méthode a une responsabilité unique
 * - DRY: Utilise les classes Position et PieceColor pour éviter la duplication
 * - Open/Closed: Extensible sans modification grâce à l'héritage
 */
public class Pawn extends Piece {
    private static final int PAWN_VALUE = 1;
    private boolean hasMoved; // Track if pawn has moved (for two-square initial move)

    /**
     * Constructeur principal utilisant PieceColor pour la type-safety.
     * 
     * @param position La position initiale en notation algébrique
     * @param color La couleur de la pièce
     */
    public Pawn(String position, PieceColor color) {
        super(position, color);
        this.hasMoved = false;
    }
    
    /**
     * Constructeur de compatibilité acceptant String pour la couleur.
     * 
     * @param position La position initiale en notation algébrique
     * @param color La couleur de la pièce en String
     */
    public Pawn(String position, String color) {
        super(position, color);
        this.hasMoved = false;
    }
    
    @Override
    public String getType() {
        return "PAWN";
    }

    @Override
    public int getValue() {
        return PAWN_VALUE;
    }
    
    /**
     * Déplace le pion vers une nouvelle position si le mouvement est valide.
     * Respecte le principe de responsabilité unique en déléguant la validation.
     * 
     * @param newPosition La nouvelle position en notation algébrique (ex: "e4")
     * @param board Le plateau de jeu pour valider les mouvements
     */
    @Override
    public void move(String newPosition, Board board) {
        if (isValidMove(newPosition, board) && isPathClear(newPosition, board)) {
            this.position = newPosition;
            this.hasMoved = true;
        }
    }
    
    /**
     * Vérifie si le mouvement du pion est valide selon les règles des échecs.
     * 
     * Règles des pions:
     * - Avancer d'une case vers l'avant (vers le haut pour les blancs, vers le bas pour les noirs)
     * - Avancer de deux cases au premier mouvement (rangée 2 pour blancs, 7 pour noirs)
     * - Capturer en diagonale d'une case vers l'avant
     * - Ne peuvent pas reculer
     * - Ne peuvent pas capturer en avançant droit
     * 
     * @param newPosition La position cible en notation algébrique
     * @param board Le plateau de jeu
     * @return true si le mouvement est valide selon les règles du pion
     */
    private boolean isValidMove(String newPosition, Board board) {
        // Utiliser Position pour encapsuler la logique de conversion
        Position currentPos = getPositionObject();
        Position targetPos = Position.fromAlgebraic(newPosition);
        
        // Vérifier que la position cible est valide
        if (!targetPos.isValid()) {
            return false;
        }
        
        // Obtenir la direction de mouvement depuis PieceColor (encapsulation)
        int direction = color.getMoveDirection();
        
        // Calculer les différences de position
        int colDiff = currentPos.getColDifference(targetPos);
        int rowDiff = currentPos.getRowDifference(targetPos);
        
        // Vérifier si c'est un mouvement vers l'avant
        if (rowDiff * direction <= 0) {
            return false; // Le pion ne peut pas reculer ou rester sur la même rangée
        }
        
        // Vérifier si c'est un mouvement en diagonale (capture)
        if (isDiagonalCapture(colDiff, rowDiff, direction)) {
            Piece targetPiece = board.getPieceAt(newPosition);
            return targetPiece != null && isOppositeColor(targetPiece);
        }
        
        // Vérifier si c'est un mouvement droit vers l'avant
        if (colDiff == 0) {
            return isValidForwardMove(targetPos, currentPos, rowDiff, direction, board);
        }
        
        return false;
    }
    
    /**
     * Vérifie si le mouvement est une capture diagonale valide.
     * 
     * @param colDiff La différence de colonne
     * @param rowDiff La différence de ligne
     * @param direction La direction de mouvement
     * @return true si c'est une capture diagonale valide
     */
    private boolean isDiagonalCapture(int colDiff, int rowDiff, int direction) {
        return Math.abs(colDiff) == 1 && rowDiff == direction;
    }
    
    /**
     * Vérifie si le mouvement droit vers l'avant est valide.
     * 
     * @param targetPos La position cible
     * @param currentPos La position actuelle
     * @param rowDiff La différence de ligne
     * @param direction La direction de mouvement
     * @param board Le plateau de jeu
     * @return true si le mouvement droit est valide
     */
    private boolean isValidForwardMove(Position targetPos, Position currentPos, 
                                       int rowDiff, int direction, Board board) {
        // Mouvement d'une case vers l'avant
        if (rowDiff == direction) {
            // La case cible doit être vide pour un mouvement droit
            return board.getPieceAt(targetPos.toAlgebraic()) == null;
        }
        
        // Mouvement de deux cases vers l'avant (premier mouvement uniquement)
        if (rowDiff == 2 * direction && !hasMoved) {
            // Vérifier que le pion est sur sa rangée de départ
            if (isOnStartingRow(currentPos)) {
                // Les deux cases doivent être vides
                return board.getPieceAt(targetPos.toAlgebraic()) == null && 
                       isPathClear(targetPos.toAlgebraic(), board);
            }
        }
        
        return false;
    }
    
    /**
     * Vérifie si le pion est sur sa rangée de départ.
     * 
     * @param pos La position actuelle
     * @return true si le pion est sur sa rangée de départ
     */
    private boolean isOnStartingRow(Position pos) {
        return pos.getRow() == color.getStartingRow();
    }
    
    /**
     * Vérifie si le chemin entre la position actuelle et la nouvelle position est libre.
     * Pour les pions, cela concerne principalement le mouvement de deux cases.
     * 
     * @param newPosition La position cible
     * @param board Le plateau de jeu
     * @return true si le chemin est libre
     */
    private boolean isPathClear(String newPosition, Board board) {
        Position currentPos = getPositionObject();
        Position targetPos = Position.fromAlgebraic(newPosition);
        
        int colDiff = currentPos.getColDifference(targetPos);
        int rowDiff = Math.abs(currentPos.getRowDifference(targetPos));
        
        // Pour un mouvement de deux cases vers l'avant, vérifier la case intermédiaire
        if (colDiff == 0 && rowDiff == 2) {
            // Calculer la case intermédiaire
            int intermediateRow = currentPos.getRow() + (targetPos.getRow() - currentPos.getRow()) / 2;
            int intermediateCol = currentPos.getCol();
            Position intermediatePos = Position.fromIndices(intermediateRow, intermediateCol);
            
            // La case intermédiaire doit être vide
            return board.getPieceAt(intermediatePos.toAlgebraic()) == null;
        }
        
        // Pour les autres mouvements (une case ou diagonale), le chemin est toujours libre
        return true;
    }
    
    @Override
    public void render(Graphics g) {
        // Utiliser Position pour calculer les coordonnées graphiques à la volée
        Position pos = getPositionObject();
        
        // Déterminer la couleur de rendu selon la couleur de la pièce
        Color renderColor = (color == PieceColor.WHITE) ? Color.BLUE : Color.RED;
        g.setColor(renderColor);
        g.fillOval(pos.getX(), pos.getY(), Constants.SQUARE_SIZE, Constants.SQUARE_SIZE);
    }
    
    /**
     * Retourne la liste de tous les coups possibles pour ce pion.
     * Respecte le principe de responsabilité unique en centralisant la logique de génération des coups.
     * 
     * @param board Le plateau de jeu
     * @return Une liste de positions en notation algébrique représentant les coups possibles
     */
    @Override
    public List<String> getPossibleMoves(Board board) {
        List<String> possibleMoves = new ArrayList<>();
        Position currentPos = getPositionObject();
        int direction = color.getMoveDirection();
        
        // Générer tous les coups possibles pour un pion
        // 1. Mouvement d'une case vers l'avant
        addForwardMoveIfValid(possibleMoves, currentPos, direction, board);
        
        // 2. Mouvement de deux cases vers l'avant (premier mouvement uniquement)
        if (!hasMoved && isOnStartingRow(currentPos)) {
            addDoubleForwardMoveIfValid(possibleMoves, currentPos, direction, board);
        }
        
        // 3. Captures diagonales
        addDiagonalCapturesIfValid(possibleMoves, currentPos, direction, board);
        
        return possibleMoves;
    }
    
    /**
     * Ajoute le mouvement d'une case vers l'avant si valide.
     * 
     * @param moves La liste des coups possibles à modifier
     * @param currentPos La position actuelle
     * @param direction La direction de mouvement
     * @param board Le plateau de jeu
     */
    private void addForwardMoveIfValid(List<String> moves, Position currentPos, int direction, Board board) {
        int newRow = currentPos.getRow() + direction;
        if (newRow >= 0 && newRow <= 7) {
            Position newPos = Position.fromIndices(newRow, currentPos.getCol());
            // La case doit être vide pour un mouvement droit
            if (board.getPieceAt(newPos.toAlgebraic()) == null) {
                moves.add(newPos.toAlgebraic());
            }
        }
    }
    
    /**
     * Ajoute le mouvement de deux cases vers l'avant si valide.
     * 
     * @param moves La liste des coups possibles à modifier
     * @param currentPos La position actuelle
     * @param direction La direction de mouvement
     * @param board Le plateau de jeu
     */
    private void addDoubleForwardMoveIfValid(List<String> moves, Position currentPos, int direction, Board board) {
        int newRow = currentPos.getRow() + 2 * direction;
        if (newRow >= 0 && newRow <= 7) {
            Position newPos = Position.fromIndices(newRow, currentPos.getCol());
            // Les deux cases doivent être vides
            if (board.getPieceAt(newPos.toAlgebraic()) == null && 
                isPathClear(newPos.toAlgebraic(), board)) {
                moves.add(newPos.toAlgebraic());
            }
        }
    }
    
    /**
     * Ajoute les captures diagonales si valides.
     * 
     * @param moves La liste des coups possibles à modifier
     * @param currentPos La position actuelle
     * @param direction La direction de mouvement
     * @param board Le plateau de jeu
     */
    private void addDiagonalCapturesIfValid(List<String> moves, Position currentPos, int direction, Board board) {
        int newRow = currentPos.getRow() + direction;
        if (newRow < 0 || newRow > 7) {
            return; // Hors limites
        }
        
        // Vérifier les deux diagonales possibles (gauche et droite)
        for (int colOffset = -1; colOffset <= 1; colOffset += 2) {
            int newCol = currentPos.getCol() + colOffset;
            if (newCol >= 0 && newCol <= 7) {
                Position newPos = Position.fromIndices(newRow, newCol);
                Piece targetPiece = board.getPieceAt(newPos.toAlgebraic());
                // Pour capturer, il doit y avoir une pièce adverse
                if (targetPiece != null && isOppositeColor(targetPiece)) {
                    moves.add(newPos.toAlgebraic());
                }
            }
        }
    }
    
    @Override
    public void update() {
        // Les coordonnées graphiques sont calculées à la volée dans render()
        // Cette méthode peut être utilisée pour d'autres mises à jour si nécessaire
    }
}
