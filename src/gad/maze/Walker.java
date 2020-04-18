package gad.maze;

public class Walker {

    public Walker(boolean[][] maze, Result result) {

    }

    public boolean walk() {
        return false;
    }

    public static void main(String[] args) {
        boolean[][] maze = Maze.generateStandardMaze(10, 10);
        Walker walker = new Walker(maze, new StudentResult());
        System.out.println(walker.walk());
        int xPosition = 1;
        int yPosition = 0;
        Maze.draw(xPosition, yPosition, maze, new boolean[maze.length][maze[0].length]);
    }
}
