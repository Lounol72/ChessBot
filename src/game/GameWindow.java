package game;

import javax.swing.JFrame;
import java.awt.event.WindowFocusListener;

public class GameWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private JFrame frame;

    public GameWindow(GamePanel gamePanel) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.setTitle("Chess");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}