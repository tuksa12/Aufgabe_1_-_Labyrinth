package gad.maze;

import java.applet.Applet;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("deprecation")
public class Maze extends Applet {
    private static final long serialVersionUID = 1L;

    private final class Field extends JPanel {
        private static final long serialVersionUID = 1L;
        private Point p;
        private int x;
        private int y;

        private Field(int x, int y) {
            this.x = x;
            this.y = y;
            p = getLocation();
        }

        public void paint(Graphics g) {
            if (!(g instanceof Graphics2D)) {
                return;
            }

            GradientPaint wallPaint = new GradientPaint(10, 50, Color.DARK_GRAY, getWidth(), 0, Color.DARK_GRAY);
            GradientPaint floorPaint = new GradientPaint(10, 50, Color.WHITE, getWidth(), 0, Color.WHITE);
            GradientPaint pathPaint = new GradientPaint(15, 0, Color.GREEN, getWidth(), 0, Color.LIGHT_GRAY);
            GradientPaint positionPaint = new GradientPaint(15, 0, Color.RED, getWidth(), 0, Color.LIGHT_GRAY);

            super.paint(g);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (spielFeld[x][y]) {
                ((Graphics2D) g).setPaint(wallPaint);
            } else {
                ((Graphics2D) g).setPaint(floorPaint);
            }
            g.fillRect(p.x, p.y, getWidth(), getHeight());

            if (sol != null && sol[x][y]) {
                ((Graphics2D) g).setPaint(pathPaint);
                g.fillOval((int) (getWidth() * .25), (int) (getHeight() * .25), (int) (getWidth() * .5),
                        (int) (getHeight() * .5));
            }
            if (x == posx && y == posy) {
                ((Graphics2D) g).setPaint(positionPaint);
                g.fillOval((int) (getWidth() * .25), (int) (getHeight() * .25), (int) (getWidth() * .5),
                        (int) (getHeight() * .5));
            }
        }
    }

    public static void setSolution(boolean[][] sol) {
        maze.sol = sol;
    }

    private JFrame myFrame = new JFrame("Spielfeld");
    private JPanel pan = new JPanel();
    private boolean[][] spielFeld;
    private int posx;
    private int posy;

    private boolean[][] sol = null;

    public Maze() {
    }

    private Maze(int px, int py, boolean[][] feld) {
        int width = feld.length;
        int height = feld[0].length;
        spielFeld = new boolean[width][height];
        // careful: we define as x growing to the right, y growing to the bottom, while
        // GridLayout defines height first,
        // width second!
        pan.setLayout(new GridLayout(height, width));

        Field[][] field = new Field[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                field[x][y] = new Field(x, y);
                pan.add(field[x][y]);
                spielFeld[x][y] = feld[x][y];
            }
        }

        myFrame.getContentPane().add(pan);
        int windowWidth = 800;
        int windowHeight = 800;
        if (width >= height) {
            windowHeight = windowHeight * height / width;
        } else {
            windowWidth = windowWidth * width / height;
        }
        myFrame.setSize(windowWidth, windowHeight);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
        update(px, py, feld);
    }

    private void update(int px, int py, boolean[][] feld) {
        if (feld.length != spielFeld.length) {
            System.err.println("Spielfeld darf sich nicht vergroessern/verkleinern...");
        }
        if (feld[0].length != spielFeld[0].length) {
            System.err.println("Spielfeld darf sich nicht vergroessern/verkleinern...");
        }

        for (int x = 0; x < spielFeld.length; x++) {
            for (int y = 0; y < spielFeld[0].length; y++) {
                spielFeld[x][y] = feld[x][y];
            }
        }
        this.posx = px;
        this.posy = py;
        pan.repaint();
    }

    private static Maze maze;

    public static void draw(int x, int y, boolean[][] feld, boolean[][] solution) {
        if (maze == null) {
            maze = new Maze(x, y, feld);
        }

        maze.sol = solution;
        maze.update(x, y, feld);

        try {
            Thread.sleep(0);
        } catch (InterruptedException ie) {
        }
    }

    public static boolean[][] generateMaze() {
        return generateMaze(11, 11, -1);
    }

    public static boolean[][] generateMaze(int width, int height, int seed) {
        Random r = new Random();
        r.setSeed(seed);
        return generateMazeInternal(width, height, r);
    }

    public static boolean[][] generateStandardMaze() {
        return generateStandardMaze(11, 11);
    }

    public static boolean[][] generateStandardMaze(int width, int height) {
        return generateMaze(width, height, 0);
    }

    private static boolean[][] generateMazeInternal(int width, int height, Random rng) {
        if (width < 3) {
            width = 3;
        }
        if (height < 3) {
            height = 3;
        }
        boolean[][] mazeArray = new boolean[width][height];

        // borders
        for (int x = 0; x < width; x++) {
            mazeArray[x][0] = true;
            mazeArray[x][height - 1] = true;
        }
        for (int y = 0; y < height; y++) {
            mazeArray[0][y] = true;
            mazeArray[width - 1][y] = true;
        }

        // create random obstacles
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (rng.nextInt(4) == 0) {
                    mazeArray[x][y] = true;
                }
            }
        }

        // entrance and exit
        mazeArray[1][0] = false;
        mazeArray[1][1] = false;
        mazeArray[width - 1][height - 2] = false;
        mazeArray[width - 2][height - 2] = false;

        return mazeArray;
    }
}