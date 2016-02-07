import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

public class SnakePlayer {
	public Rectangle rect;
	public int dx, dy;
	public Color color;
	public static boolean gameover;
	public static boolean fruitAmove;
	public static boolean fruitBmove;

	public int size;
	int lastX, lastY;
	int placementDistance;

	public Vector<Rectangle> rectangles = new Vector<Rectangle>();

	public SnakePlayer(int x, int y, int width, int height, Color color) {
		rect = new Rectangle(x, y, width, height);
		this.color = color;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (Math.abs(rect.x - lastX) > placementDistance
				|| Math.abs(rect.y - lastY) > placementDistance) {
			// draw rectangle at last position
			rectangles.add(new Rectangle(lastX, lastY, 10, 10));
			lastX = rect.x;
			lastY = rect.y;
		}
		for (Rectangle r : rectangles) {
			g.setColor(color);
			g.fillRect(r.x, r.y, r.height, r.width);

		}
		if (rectangles.size() > size) {
			// REMOVE RECTANGLE
			rectangles.remove(0);
		}
		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (gameover == false) {
			rect.x += dx;
			rect.y += dy;
			// g.fillRect(rect.x, rect.y, 10, 10);
		}
		if (rect.y <= -10) {
			rect.y = 0;
			gameover = true;
		}
		if (rect.x >= 500) {
			rect.x = 489;
			gameover = true;
		}
		if (rect.x <= -10) {
			rect.x = 0;
			gameover = true;
		}
		if (rect.y >= 500) {
			rect.y = 489;
			gameover = true;
		}

		if (rect.intersects(DualSnake.fruitA.rect)) {
			size = size + 3;
			System.out.println("Size: " + size);
			fruitAmove = true;

		}
		if (rect.intersects(DualSnake.fruitB.rect)) {
			size = size + 3;
			// System.out.println("Size: ", size);
			fruitBmove = true;
		}
		if ((checkIntersections(DualSnake.blueSnake.rectangles) && DualSnake.blueSnake != this)
				|| (checkIntersections(DualSnake.redSnake.rectangles) && DualSnake.redSnake != this)) {
			// Caught intersection
			SnakePlayer.gameover = true;
			dx = 0;
			dy = 0;
			System.out.println("Intersection found!");
			gameover = true;
		}

	}

	public boolean checkIntersections(Vector<Rectangle> rects) {
		// for (Rectangle o : rects) {
		// // if(o != this){ //Don't intersect with myself
		// if (rect.intersects(o))
		// return true;
		// }
		for (Rectangle o : rects) {
			if (rect.intersects(o))
				return true;
			for (Rectangle r : rectangles) {
				// if(o != this){ //Don't intersect with myself
				if (r.intersects(o))
					return true;
			}
		}
		return false;
	}
}
