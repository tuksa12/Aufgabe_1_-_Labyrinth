package gad.maze;

public class StudentResult implements Result {

    @Override
    public void addLocation(int x, int y) {
        System.out.println("Koordinate (" + x + ", " + y + ") hinzugefügt");
    }

}
