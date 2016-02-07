import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;
import java.util.Vector;
import javax.swing.JPanel;

public class DualSnake extends Applet implements KeyListener, ActionListener {
	int x;
	int y;
	int dx;
	int dy;

	Graphics bufferGraphics;
	Image offscreen;
	Dimension dim;
	int curX, curY;

	// int prevPosition[][] = new int[50][50]; // creating array for data
	// // collection of last position of
	// // snakes (UNUSED)

	int redlastX, redlastY;
	int redplacementDistance;
	int bluelastX, bluelastY;
	int blueplacementDistance;
	Button pauseButton, restartButton;

	public static SnakePlayer redSnake, blueSnake;
	public static Fruit fruitA, fruitB;
	public static int redScore;
	public static int blueScore;
	public static boolean paused;
	public static boolean restart;

	String bgPath = "../snake-bgFINAL.png";
	Image bg;

	public void init() {
		resize(502, 930);
		addKeyListener(this);
		// constructors below
		redSnake = new SnakePlayer(380, 380, 10, 10, Color.red); // creating Red
																	// Snake and
																	// starting
																	// position
		blueSnake = new SnakePlayer(120, 120, 10, 10, Color.blue); // creating
																	// Blue
																	// Snake and
																	// starting
																	// position
		fruitA = new Fruit(Color.white); // creating first FruitA
		fruitB = new Fruit(Color.white); // creating first FruitB

		bg = getImage(getDocumentBase(), bgPath);
		setLayout(null);

		pauseButton = new Button("Pause");
		restartButton = new Button("Restart");

		pauseButton.addActionListener(this);
		restartButton.addActionListener(this);

//		add(pauseButton);
//		add(restartButton);
		pauseButton.setBounds(0, 572, 250, 23);
		restartButton.setBounds(251, 572, 250, 23);
		dim = getSize();

		offscreen = createImage(dim.width, dim.height);
		bufferGraphics = offscreen.getGraphics();
		// add(pause);
		// add(restart);
		// pause.setBounds(100, 650, 100, 50);
		// restart.setBounds(300, 650, 100, 50);
	}

	public void paint(Graphics g) {
		try {

			bufferGraphics.drawImage(bg, 0, 0, this);
			bufferGraphics.setColor(Color.green);
			bufferGraphics.drawRect(0, 0, 501, 501);
			redSnake.paint(bufferGraphics);
			blueSnake.paint(bufferGraphics);
			fruitA.paint(bufferGraphics);
			fruitB.paint(bufferGraphics);
			
			
			Font scoreFont = new Font("Delfino", 2, 20);
			bufferGraphics.setFont(scoreFont);
			bufferGraphics.setColor(Color.red);
			bufferGraphics.drawString("Red Snake Score: " + redScore, 0, 545);
			bufferGraphics.setColor(Color.blue);
			bufferGraphics.drawString("Blue Snake Score: " + blueScore, 0, 565);
			bufferGraphics.setColor(Color.white);
			bufferGraphics.drawString("Total Score: " + (redScore + blueScore),
					350, 555);
			
			if ((redSnake.rect.intersects(fruitA.rect)) || (redSnake.rect.intersects(fruitB.rect))) {
				redScore++;
			}
			
			if ((blueSnake.rect.intersects(fruitA.rect)) || (blueSnake.rect.intersects(fruitB.rect))) {
				blueScore++;
			}
			
			
			g.drawImage(offscreen, 0, 0, this);

			if (SnakePlayer.gameover == false) {
				x += dx;
				y += dy;
			}

			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			if (SnakePlayer.fruitAmove == true) { // Makes Fruit A move after
													// SnakePlayer.intersects(fruitA)
				fruitA.rect.x = ((int) (Math.random() * 49)) * 10 + 10;
				fruitA.rect.y = ((int) (Math.random() * 49)) * 10 + 10;
				fruitA.paint(g);
				System.out.println("Fruit A moved.");
				SnakePlayer.fruitAmove = false;
				System.out.println("Fruit A is done moving.");
			}

			if (SnakePlayer.fruitBmove == true) { // Makes Fruit B move after
													// SnakePlayer.intersects(fruitB)
				fruitB.rect.x = ((int) (Math.random() * 49)) * 10 + 10;
				fruitB.rect.y = ((int) (Math.random() * 49)) * 10 + 10;
				fruitB.paint(g);
				System.out.println("Fruit B moved.");
				SnakePlayer.fruitBmove = false;
				System.out.println("Fruit B is done moving.");

			}

			// if ((redSnake.rect.intersects(blueSnake.rectangles)) ||
			// (redSnake.rect.intersects(blueSnake)) ||
			// (blueSnake.rect.intersects(redSnake.rectangles))){
			// SnakePlayer.gameover = true;
			// }

			if (restart == true) {
				redSnake.rect.x = redSnake.rect.y = 380;
				blueSnake.rect.x = blueSnake.rect.y = 120;

				redSnake.size = 0;
				blueSnake.size = 0;

				redSnake.dx = 0;
				redSnake.dy = 0;
				blueSnake.dx = 0;
				blueSnake.dy = 0;

				redScore = 0;
				blueScore = 0;

				fruitA.rect.x = ((int) (Math.random() * 49)) * 10 + 10;
				fruitA.rect.y = ((int) (Math.random() * 49)) * 10 + 10;
				fruitB.rect.x = ((int) (Math.random() * 49)) * 10 + 10;
				fruitB.rect.y = ((int) (Math.random() * 49)) * 10 + 10;

				SnakePlayer.fruitAmove = false;
				SnakePlayer.fruitBmove = false;

				SnakePlayer.gameover = false;

				System.out.println("Red Snake Size: " + redSnake.size);
				System.out.println("Blue Snake Size: " + blueSnake.size);

				restart = false;
			}

			if (DualSnake.redSnake.rect.intersects(DualSnake.blueSnake.rect)) {
				SnakePlayer.gameover = true;
			}

			if (SnakePlayer.gameover == true) {
				Font usedFont = new Font("IMPACT", 2, 75);
				bufferGraphics.setFont(usedFont);
				bufferGraphics.setColor(Color.red);
				bufferGraphics.drawString("GAME OVER", 83, 260);
			}
			if (DualSnake.paused) {
				redSnake.dx = 0;
				redSnake.dy = 0;
				blueSnake.dx = 0;
				blueSnake.dy = 0;
			}
			g.drawImage(offscreen, 0, 0, this);
			Thread.sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		repaint();
	}
	
	public void update (Graphics g){
		paint(g);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getKeyCode()) {

		case KeyEvent.VK_LEFT:
			if (redSnake.dx == 0) {
				redSnake.dx = -10;
				redSnake.dy = 0;
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (redSnake.dx == 0) {
				redSnake.dx = 10;
				redSnake.dy = 0;
			}
			break;
		case KeyEvent.VK_UP:
			if (redSnake.dy == 0) {
				redSnake.dx = 0;
				redSnake.dy = -10;
			}
			break;
		case KeyEvent.VK_DOWN:
			if (redSnake.dy == 0) {
				redSnake.dx = 0;
				redSnake.dy = 10;
			}
			break;
		case KeyEvent.VK_A:
			if (blueSnake.dx == 0) {
				blueSnake.dx = -10;
				blueSnake.dy = 0;
			}
			break;
		case KeyEvent.VK_D:
			if (blueSnake.dx == 0) {
				blueSnake.dx = 10;
				blueSnake.dy = 0;
			}
			break;
		case KeyEvent.VK_W:
			if (blueSnake.dy == 0) {
				blueSnake.dx = 0;
				blueSnake.dy = -10;
			}
			break;
		case KeyEvent.VK_S:
			if (blueSnake.dy == 0) {
				blueSnake.dx = 0;
				blueSnake.dy = 10;
			}
			break;
		case KeyEvent.VK_P:
			redSnake.dx = 0;
			redSnake.dy = 0;
			blueSnake.dx = 0;
			blueSnake.dy = 0;
			break;
		case KeyEvent.VK_R:
			restart = true;
			System.out.println("R Key pressed.");
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == pauseButton) {
			DualSnake.paused = !DualSnake.paused;
			System.out.println("Game Paused.");
		}
		if (arg0.getSource() == restartButton) {
			restart = true;
			System.out.println("Restart Button pressed.");
		}
	}
}