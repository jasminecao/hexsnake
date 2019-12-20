

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener, MouseListener
{
	private JFrame frame;
	private JPanel panel;
	private Point[][] grid;
	private int length = 17;
	private int width = 24;
	private boolean origin;
	private boolean start;
	private boolean gameOver;
	private int dir;
	private Snake snek;
	private Color snakecolor = new Color(221, 255, 247);
	private Color foodColor = new Color(255, 107, 107);
	private Timer time;
	private Food food;
	private int score;
	private int size;
	private Score scorebd;
	private List<Person> entries;
	
	//Constructor: creates title, grid, sets timer
	public Game() {
	    this.setLayout(null);

		JLabel title = new JLabel("hexsnake");
		title.setFont(new Font("Courier", Font.PLAIN, 36));
		title.setForeground(Color.WHITE);
		this.add(title);
		title.setBounds(140, 0, 200, 100);
		this.setOpaque(true); 
		this.setBackground(new Color(112, 160, 175));

		grid = new Point[length][width];
		createGrid();
		
		snek = new Snake(grid); //creates snake with head at origin
		food = new Food(snek, length, width);
		food.changePos();
		
		time = new Timer(200, this); 
		//time.start();
		
		origin = true; //paints hexagon at origin
		start = false; //game hasn't started until a key is pressed
		gameOver = false;
		
		score = 0;
		size = 1;
		scorebd = new Score();
	    entries = scorebd.getEntries();
	    
		JButton info = new JButton("how to play?");
		info.setFocusable(false);
	    info.setFont(new Font("Courier", Font.BOLD, 14));
	    info.setBounds(530, 675, 130, 50);
	    info.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseReleased(MouseEvent e) {
		    	String instructions = "Press any key to begin. Keys QWEASD control the snake. \n Q moves the snake to the"
		    			+ " top left. \n W moves the snake upward. \n E moves the snake to the top right. \n A moves the snake to the"
		    			+ " bottom left. \n S moves the snake downward. \n D moves the snake to the bottom right. \n When the snake collides"
		    			+ " with the food, the length increases by one. \n The game ends if the snake moves out of bounds or collides with itself."
		    			+ "\n The goal of the game is to have as long a snake as possible.";
		    	JOptionPane.showMessageDialog(frame, instructions, "how to play:", JOptionPane.PLAIN_MESSAGE);
	    	}
	    });
	    
	    this.add(info);
	    setFocusable(true); //for key listener
		addKeyListener(this); //for key listener
	}
	
	//paints an empty grid, draws the snake
	public void paintComponent(Graphics g){
	    super.paintComponent(g);
	    
	    for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				g.setColor(Color.WHITE);
				drawHex(g, grid[i][j], true);
			}
		}
	    
	    if (origin) {
	    	g.setColor(snakecolor);
	    	drawHex(g, grid[length / 2][width / 2], false);
	    	origin = false;
	    }
	    
	    if (start) {
	    	drawSnake(g);
	    	drawFood(g);
	    }
	    
	    g.setColor(Color.WHITE);
	    Font largeFont = new Font ("Courier New", 1, 30);
	    g.setFont(largeFont);
	    g.drawString("stats", 460, 100);
	    Font medFontbold = new Font ("Courier New", 1, 24);
	    Font medFont = new Font ("Courier New", 0, 24);
	    Font smallFontbold = new Font ("Courier New", 1, 18);
	    Font smallFont = new Font ("Courier New", 0, 18);

	    g.setFont(medFontbold);
	    g.drawString("score", 460, 140);
	    g.drawString("length", 460, 180);
	    g.setFont(medFont);
	    g.drawString(score + "", 600, 140);
	    g.drawString(size + "", 600, 180);
	    
	    g.setFont(largeFont);
	    g.drawString("scoreboard", 460, 275);
	    
	    for (int i = 0; i < 5; i++) {
	    	g.setFont(smallFontbold);
	    	g.drawString((i+1) + "" + "." + entries.get(i).getName(), 440, 315 + i*40);
	    	g.setFont(smallFont);
	    	g.drawString(entries.get(i).getResult() + "", 600, 315 + i*40);
	    }
	    
	    repaint();
	}
	
	//draws a hexagon at specified point or coord. if true then empty, false means filled.
	public void drawHex(Graphics g, Point p, boolean empty) {
		int x = (p.getX() * 22) + 36;
		int y = 0;
		if(p.getX() % 2 == 1) {
			y = (p.getY() * 24) + 102;
		}
		else {
			 y = (p.getY() * 24) + 90;
		}
		 
		int xvalues[] = {x, x+8, x+22, x+30, x+22, x+8};
		int yvalues[] = {y, y - 12, y - 12, y , y+12, y+12};
		
		if (empty) {
			g.drawPolygon(xvalues, yvalues, 6);
		}
		else {
			g.fillPolygon(xvalues, yvalues, 6);
		}
	}
	
	//draws snake by drawing each hexagon at point from Snake
	public void drawSnake(Graphics g) {
		g.setColor(snakecolor);
		List<Point> list = snek.getList();
		
		for (Point p : list) {
			drawHex(g, p, false);
		}
	}
	
	//draws food, fills in foodColor
	public void drawFood(Graphics g) {
		g.setColor(foodColor);
		drawHex(g, food.getPoint(), false);
	}
	
	//initiates grid array with Point objects containing each coordinate
	public void createGrid() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				grid[i][j] = new Point(i, j);
			}
		}
	}
	
	//moves snake and sets direction
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Point head = snek.getHead();
		Point f = food.getPoint();
		gameOver = snek.getGameOver();
		snek.setDir(dir);
		if (start && !gameOver) {
			snek.move();
			if (head.getX() == f.getX() && head.getY() == f.getY()) {
				snek.addPoint(f);
				food.changePos();
				score = score + 50; //inc by length
				size = size + 1;
			}
		}
		if (gameOver) {
			time.stop();
			String inputName = JOptionPane.showInputDialog(frame, "username?", "game over :(", 
					JOptionPane.PLAIN_MESSAGE);
			if (inputName == null || inputName == "") {
				inputName = "no name";
			}
			scorebd.writeData(inputName, score);
			entries = scorebd.getEntries();
		}
		score = score + 1; //inc by time passed
	}
	
	//changes direction based on key pressed
	@Override
	public void keyPressed(KeyEvent e) {
		if (!start) {
			start = true;
			time.start();
		}
		switch (e.getKeyCode()) {
			case KeyEvent.VK_Q:
				dir = 0;
				break;
			case KeyEvent.VK_W:
				dir = 1;
				break;
			case KeyEvent.VK_E:
				dir = 2;
				break;
			case KeyEvent.VK_A:
				dir = 3;
				break;
			case KeyEvent.VK_S:
				dir = 4;
				break;
			case KeyEvent.VK_D:
				dir = 5;
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	//creates frame and initiates a Game object
	public static void main(String[] args) {
		JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(500, 100, 700, 800); //bounds of frame
		frame.setName("hexsnake");
		frame.setTitle("hexsnake");
		
		Game gg = new Game();
	    frame.getContentPane().add(gg);
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}

	@Override
	public void mousePressed(MouseEvent e) {		
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}
	
}

