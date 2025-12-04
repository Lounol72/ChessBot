package game;

public class Game{
	// Composantes principales du jeu 
	private final GamePanel gamePanel;
	private final GameWindow gameWindow;
	private Thread gameLoopThread;

	// Paramètres de performances du jeu
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	// FPS/UPS tracking
	private int currentFPS = 0;
	private int currentUPS = 0;

	public Game(){
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.resquestFocus();

		startGameLoop();
	}

	private void initClasses(){
		// TODO : implement this method
		throw new NotBoundException();
	}

	private void startGameLoop(){
		gameLoopThread = new Thread(this);
		gameLoopThread.start();
	}

	private void update(){
		// TODO : implement this method
		throw new NotBoundException();
	}

	public void render(Graphics g){
		// TODO : implement this method
		throw new NotBoundException();	
	}

	@Override
	public void run(){
		double timePerFrame = 1000000000.0 / FPS_SET;
		double tmePerUpdate = 1000000000.0 / UPS_SET;

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
				currentFps = frames;
				currentUPS = updates;
				frames = 0;
				updates = 0;
			}
		}
	}
}
