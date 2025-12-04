package utilz;

import pieces.PieceColor;

/**
 * Classe utilitaire contenant des méthodes helper pour la validation et les conversions.
 * Respecte le principe DRY (Don't Repeat Yourself) en centralisant la logique commune.
 */
public class HelpMethods {
    
    /**
     * Vérifie que les paramètres de position et de couleur sont valides.
     * 
     * @param position La position en notation algébrique
     * @param color La couleur de la pièce
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    public static void verifyParameters(String position, String color) {
        if (position == null || color == null) {
            throw new IllegalArgumentException("Position and color cannot be null");
        }
        if (!color.equals("WHITE") && !color.equals("BLACK")) {
            throw new IllegalArgumentException("Color must be either WHITE or BLACK");
        }
        if (!position.matches("^[a-h][1-8]$")) {
            throw new IllegalArgumentException("Position must be in the format a1 to h8");
        }
    }
    
    /**
     * Vérifie que les paramètres de position et de couleur sont valides.
     * Surcharge utilisant PieceColor pour la type-safety.
     * 
     * @param position La position en notation algébrique
     * @param color La couleur de la pièce
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    public static void verifyParameters(String position, PieceColor color) {
        if (position == null || color == null) {
            throw new IllegalArgumentException("Position and color cannot be null");
        }
        if (!position.matches("^[a-h][1-8]$")) {
            throw new IllegalArgumentException("Position must be in the format a1 to h8");
        }
    }
    
    /**
     * Vérifie si une position est valide sur l'échiquier.
     * 
     * @param position La position en notation algébrique
     * @return true si la position est valide
     */
    public static boolean isValidPosition(String position) {
        return position != null && position.matches("^[a-h][1-8]$");
    }
}