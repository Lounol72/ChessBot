package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import game.GamePanel;
import states.GameState;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch(GameState.currentState){
            case GAME -> gamePanel.getGame().getPlaying().mouseClicked(e);
            case MENU -> gamePanel.getGame().getMenu().mouseClicked(e);
            default -> {
                throw new IllegalArgumentException("Invalid game state: " + GameState.currentState);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch(GameState.currentState){
            case GAME -> gamePanel.getGame().getPlaying().mousePressed(e);
            case MENU -> gamePanel.getGame().getMenu().mousePressed(e);
            default -> {
                throw new IllegalArgumentException("Invalid game state: " + GameState.currentState);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch(GameState.currentState){
            case GAME -> gamePanel.getGame().getPlaying().mouseReleased(e);
            case MENU -> gamePanel.getGame().getMenu().mouseReleased(e);
            default -> {
                throw new IllegalArgumentException("Invalid game state: " + GameState.currentState);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch(GameState.currentState){
            case GAME -> gamePanel.getGame().getPlaying().mouseDragged(e);
            case MENU -> gamePanel.getGame().getMenu().mouseDragged(e);
            default -> {
                throw new IllegalArgumentException("Invalid game state: " + GameState.currentState);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch(GameState.currentState){
            case GAME -> gamePanel.getGame().getPlaying().mouseMoved(e);
            case MENU -> gamePanel.getGame().getMenu().mouseMoved(e);
            default -> {
                throw new IllegalArgumentException("Invalid game state: " + GameState.currentState);
            }
        }
    }
}
