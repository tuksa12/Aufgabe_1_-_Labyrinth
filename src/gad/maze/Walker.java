package gad.maze;

public class Walker {
    private boolean[][] maze;
    private Result result;

    public Walker(boolean[][] maze, Result result) {
        this.maze = maze;
        this.result = result;
    }

    public boolean walk() {
        int xPosition = 1;
        int yPosition = 0;
        String direction = "Down";
        boolean walking = false;

        try{
        while (xPosition != 1 || yPosition != 0 || !walking) {
            if(direction.equals("Down")){
                if(maze[xPosition-1][yPosition]) {
                    if (!maze[xPosition][yPosition+1]){
                        result.addLocation(xPosition,yPosition);
                        yPosition++;
                        walking = true;
                    }else{
                        direction = "Right";
                    }
                } else{
                    direction = "Left";
                    result.addLocation(xPosition,yPosition);
                    xPosition--;
                    walking = true;
                }
            }
            else if(direction.equals("Right")){
                if(maze[xPosition][yPosition+1]) {
                    if (!maze[xPosition+1][yPosition]){
                        result.addLocation(xPosition,yPosition);
                        xPosition++;
                        walking = true;
                    }else{
                        direction = "Up";
                    }
                } else{
                    direction = "Down";
                    result.addLocation(xPosition,yPosition);
                    yPosition++;
                    walking = true;
                }
            }
            else if(direction.equals("Up")){
                if(maze[xPosition+1][yPosition]) {
                    if (!maze[xPosition][yPosition-1]){
                        result.addLocation(xPosition,yPosition);
                        yPosition--;
                        walking = true;
                    }else{
                        direction = "Left";
                    }
                } else{
                    direction = "Right";
                    result.addLocation(xPosition,yPosition);
                    xPosition++;
                    walking = true;
                }
            }
            else if(direction.equals("Left")){
                if(maze[xPosition][yPosition-1]) {
                    if (!maze[xPosition-1][yPosition]){
                        result.addLocation(xPosition,yPosition);
                        xPosition--;
                        walking = true;
                    }else{
                        direction = "Down";
                    }
                } else{
                    direction = "Up";
                    result.addLocation(xPosition,yPosition);
                    yPosition--;
                    walking = true;
                }
            }
        }
        }catch (Exception e){
            result.addLocation(xPosition,yPosition);
            return true;
        }
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
