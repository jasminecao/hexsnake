

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Food {
	private Point pt;
	private List<Point> snake;
	private int l;
	private int w;
	
	public Food(Snake s, int l, int w) {
		snake = s.getList();
		this.l = l;
		this.w = w;
	}
	
	//determines the food's point, ensures that it doesn't overlap with snake
	public void changePos() {
		int x = getRandomX();
		int y = getRandomY();
		boolean takenX = true;
		boolean takenY = true;
		
		ListIterator<Point> it = snake.listIterator();
		while (it.hasNext() && !takenX && !takenY) {
			Point p = it.next();
			if (x != p.getX()) takenX = false;
			else x = getRandomX();
			if (y != p.getY()) takenY = false;
			else y = getRandomY();
		}
		
		pt = new Point(x, y);
	}
	
	public int getRandomX() {
		return (int)(Math.random() * l);
	}
	
	public int getRandomY() {
		return (int)(Math.random() * w);
	}
	
	public Point getPoint() {
		return pt;
	}

}
