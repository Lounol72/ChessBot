package pieces;

import static utilz.HelpMethods.verifyParameters;
import utilz.Position;

/**
 * Classe abstraite de base pour toutes les pièces d'échecs.
 * Implémente les fonctionnalités communes et définit le template pour les sous-classes.
 * Respecte le principe DRY et le Template Method Pattern.
 */
public abstract class Piece implements PieceInterface {
    protected String position; // Position en notation algébrique
    protected PieceColor color; // Couleur de la pièce (type-safe)
    
    /**
     * Constructeur protégé pour les sous-classes.
     * 
     * @param position La position initiale en notation algébrique
     * @param color La couleur de la pièce
     */
    protected Piece(String position, PieceColor color) {
        verifyParameters(position, color);
        this.position = position;
        this.color = color;
    }
    
    /**
     * Constructeur de compatibilité acceptant String pour la couleur.
     * Convertit automatiquement en PieceColor.
     * 
     * @param position La position initiale en notation algébrique
     * @param color La couleur de la pièce en String
     */
    protected Piece(String position, String color) {
        verifyParameters(position, color);
        this.position = position;
        this.color = PieceColor.fromString(color);
    }

    @Override
    public String getPosition() {
        return position;
    }
    
    /**
     * Retourne la Position encapsulée pour faciliter les calculs.
     * 
     * @return Un objet Position représentant la position actuelle
     */
    protected Position getPositionObject() {
        return Position.fromAlgebraic(position);
    }

    @Override
    public PieceColor getColor() {
        return color;
    }
    
    /**
     * Vérifie si cette pièce est de la même couleur qu'une autre pièce.
     * 
     * @param other L'autre pièce à comparer
     * @return true si les deux pièces sont de la même couleur
     */
    protected boolean isSameColor(Piece other) {
        return other != null && this.color == other.color;
    }
    
    /**
     * Vérifie si cette pièce est de couleur opposée à une autre pièce.
     * 
     * @param other L'autre pièce à comparer
     * @return true si les deux pièces sont de couleurs opposées
     */
    protected boolean isOppositeColor(Piece other) {
        return other != null && this.color == other.color.opposite();
    }

    @Override
    public abstract String getType();
    
    @Override
    public abstract int getValue();
    
    @Override
    public abstract void move(String newPosition, board.Board board);
    
    @Override
    public abstract void render(java.awt.Graphics g);
    
    @Override
    public abstract void update();
    
    @Override
    public abstract java.util.List<String> getPossibleMoves(board.Board board);
}
