package utilz;
public class HelpMethods {
    
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


}