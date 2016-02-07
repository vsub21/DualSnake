import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Fruit {
	public Rectangle rect;
	public Color color;

	public Fruit(Color color) {
		rect = new Rectangle(((int) (Math.random() * 49)) * 10 + 10,
				((int) (Math.random() * 49)) * 10 + 10, 10, 10);
		this.color = color;

	}

	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(rect.x, rect.y, 10, 10);

	}

}