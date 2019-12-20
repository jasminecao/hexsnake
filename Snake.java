

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Snake {
	private List<Point> list;
	private int dir;
	private int l;
	private int w;
	private Point[][] grid;
	private boolean gameOver; //gameOver
	
	public Snake(Point[][] grid) {
		list = new LinkedList<Point>();
		
		this.grid = grid;
		w = grid[0].length;
		l = grid.length;
		
		list.add(grid[l / 2][w / 2]);
		dir = 1;
		gameOver = false;
	}
	
	public void move() {
		int x0 = list.get(0).getX();
		int y0 = list.get(0).getY();
		if (!gameOver) {
			moveHead();
		}
		if (!gameOver) { //in case moveHead changes gameOver to true
			for (int i = list.size() - 1; i > 1; i--) { //shifts index 2 to end up one
				Point temp = list.get(i-1);
				list.set(i, new Point(temp.getX(), temp.getY()));
			}
			if (list.size() > 1) { //sets index 1 equal to previous head
				list.set(1, new Point(x0, y0));
			}
		}
	}
	
	public void moveHead() {
		Point p = getHead();
		int x = p.getX();
		int y = p.getY();
		
		if (!gameOver) {
			if (x % 2 == 0) {
				switch(dir)
				{
					case 0:
						if (!isInvalid(x-1, y-1)) {
							p.setX(x-1);
							p.setY(y-1);
						} break; 
					case 1:
						if (!isInvalid(x, y-1)) {
							p.setY(y-1);
						} break;
					case 2:
						if (!isInvalid(x + 1, y-1)) {
							p.setX(x+1);
							p.setY(y-1);
						} break;
					case 3:
						if (!isInvalid(x-1, y)) {
							p.setX(x-1);
						} break;
					case 4:
						if (!isInvalid(x, y+1)) {
							p.setY(y+1);
						} break;
					case 5:
						if (!isInvalid(x+1, y)) {
							p.setX(x+1);
						} break;
				}
			}
			else {
				switch(dir)
				{
					case 0:
						if (!isInvalid(x-1, y)) {
							p.setX(x-1);
						} break;
					case 1:
						if (!isInvalid(x, y-1)) {
							p.setY(y-1);
						} break;
					case 2:
						if (!isInvalid(x+1, y)) {
							p.setX(x+1);
						} break;
					case 3:
						if (!isInvalid(x-1, y+1)) {
							p.setX(x-1);
							p.setY(y+1);
						} break;
					case 4:
						if (!isInvalid(x, y+1)) {
							p.setY(y+1);
						} break;
					case 5:
						if (!isInvalid(x+1, y+1)) {
							p.setX(x+1);
							p.setY(y+1);
						} break;
				}
			}
		}
				
	}
	
	//checks if snake out-of-bounds or collide w/ itself. true = invalid.
	public boolean isInvalid(int x, int y) {
		if (x < 0 || x >= l || y < 0 || y >= w) { 
			gameOver = true;
			return gameOver;
		}
		for (int i = 1; i < list.size(); i++) {
			int x0 = list.get(i).getX();
			int y0 = list.get(i).getY();
			if (x == x0 && y == y0) { 
				gameOver = true;
				return gameOver;
			}
		}
		
		return gameOver;
	}
	
	public void addPoint(Point pt) {
		list.add(pt);
	}
	
	public void printList() {
		for (Point p : list) {
			System.out.print("(" + p.getX() + " ," + p.getY() + ") ");
		}
		System.out.println();
	}
	
	public Point getHead() {
		return list.get(0);
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	public void setDir(int d) {
		dir = d;
	}
	
	public List<Point> getList() {
		return list;
	}

}
