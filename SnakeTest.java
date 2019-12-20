

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class SnakeTest {
	private Snake sn;
	
	//tests if snake is created and begins with length 1.
	@Test
	public void createdTest() {
		Point[][] grid = new Point[11][11];
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				grid[i][j] = new Point(i, j);
			}
		}
		sn = new Snake(grid);
		List<Point> list = sn.getList();
		assertEquals("snake size", 1, list.size());
	}
	
	//tests if add length increases snake's length.
	@Test
	public void increaseTest() {
		Point[][] grid = new Point[11][11];
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				grid[i][j] = new Point(i, j);
			}
		}
		sn = new Snake(grid);
		sn.addPoint(new Point(1, 1));
		List<Point> list = sn.getList();
		assertEquals("snake size", 2, list.size());
		assertEquals("snake head", list.get(1).getX(), 1);
		assertEquals("snake head", list.get(1).getY(), 1);
	}
	
	//tests if snake head moves correctly
	@Test
	public void moveHeadTest() {
		Point[][] grid = new Point[11][11];
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				grid[i][j] = new Point(i, j);
			}
		}
		sn = new Snake(grid); //head at (5,5)
		sn.setDir(1);
		sn.move();
		List<Point> list = sn.getList();
		
		assertEquals("snake head", list.get(0).getX(), 5);
		assertEquals("snake head", list.get(0).getY(), 4);
	}
	
	//tests if entire snake moves correctly
	@Test
	public void moveBodyTest() {
		Point[][] grid = new Point[11][11];
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				grid[i][j] = new Point(i, j);
			}
		}
		sn = new Snake(grid); //head at (5,5)
		sn.setDir(1);
		sn.addPoint(new Point(5, 6));
		sn.move();
		List<Point> list = sn.getList();
		
		assertEquals("snake head", list.get(1).getX(), 5);
		assertEquals("snake head", list.get(1).getY(), 5);
	}
	
	//tests if out of bounds
	@Test
	public void outOfBoundsTest() {
		Point[][] grid = new Point[2][2];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				grid[i][j] = new Point(i, j);
			}
		}
		sn = new Snake(grid);
		sn.addPoint(new Point(1, 1));
		List<Point> list = sn.getList();
		sn.move();
		sn.move();
		sn.move();
        assertTrue("snake out of bounds", sn.getGameOver());
	}
	
	//tests if snake hits itself
	@Test
	public void collisionTest() {
		Point[][] grid = new Point[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				grid[i][j] = new Point(i, j);
			}
		}
		sn = new Snake(grid); //head at point (2,2)
		sn.addPoint(new Point(2, 3));
		sn.addPoint(new Point(2, 4));
		sn.addPoint(new Point(2, 5));
		sn.setDir(4);
		sn.move();
        assertTrue("snake collides with itself", sn.getGameOver());
	}
}
