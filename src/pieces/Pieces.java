package pieces;

import java.awt.Graphics;

public interface Pieces {
    public String getType();
    public String getPosition();
    public int getValue();
    public void move(String newPosition);
    public String getColor();
    public void render(Graphics g);
    public void update();
}
