package game;

import java.awt.Graphics;

import states.GameState;
import states.Menu;
import states.Playing;

public class Game implements Runnable {
	// Composantes principales du jeu 
	private final GamePanel gamePanel;
	private final GameWindow gameWindow;
	private Thread gameLoopThread;

	// Paramètres de performances du jeu
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	// States
	private Playing playing;
	private Menu menu;

	// FPS/UPS tracking
	private int currentFPS = 0;
	private int currentUPS = 0;

	public Game(){
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();

		startGameLoop();
	}

	private void initClasses(){
		playing = new Playing();
		menu = new Menu();
	}

	private void startGameLoop(){
		gameLoopThread = new Thread(this);
		gameLoopThread.start();
	}

	private void update(){
		switch(GameState.currentState){
			case GAME -> playing.update();
			case MENU -> menu.update();
			default -> {
                }
		}
	}

	public void render(Graphics g){
		switch(GameState.currentState){
			case GAME -> playing.draw(g);
			case MENU -> menu.draw(g);
			default -> {
				throw new IllegalArgumentException("Invalid game state: " + GameState.currentState);
                }
		}
	}

	@Override
	public void run(){
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while(true){
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;

			if (deltaU >= 1){
				update();
				updates++;
				deltaU--;
			}
			if (deltaF >=1){
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			if (System.currentTimeMillis() - lastCheck >= 1000){
				lastCheck = System.currentTimeMillis();
				currentFPS = frames;
				currentUPS = updates;
				frames = 0;
				updates = 0;
			}
		}
	}

	public Playing getPlaying() {
		return playing;
	}

	public Menu getMenu() {
		return menu;
	}
}
