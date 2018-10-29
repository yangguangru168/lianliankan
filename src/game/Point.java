package game;

public class Point extends java.awt.Point {
    public Point point;

    public Point(int x, int y, Point point){
        super(x,y);
        this.point = point;
    }

    public Point(int x, int y){
        super(x,y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
