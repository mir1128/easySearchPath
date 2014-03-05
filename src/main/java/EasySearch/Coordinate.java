package EasySearch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {
    private int y;
    private int x;

    public Coordinate() {
    }

    public Coordinate(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public static Coordinate string2Value(String coordinate){
        Pattern pattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
        Matcher mather = pattern.matcher(coordinate);

        if (mather.find()){
            return new Coordinate(Integer.valueOf(mather.group(1)), Integer.valueOf(mather.group(2)));
        }
        return null;
    }

    public static String value2String(Coordinate coordinate){
        return "(" + coordinate.getX() + "," + coordinate.getY() + ")";
    }

}
