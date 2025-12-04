package game;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import inputs.MouseInputs;
import utilz.Constants;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;

    public GamePanel(Game game) {
        this.mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(Constants.WIDTH, Constants.HEIGHT);
        setPreferredSize(size);
    }

    public void updateGame() {
     
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
