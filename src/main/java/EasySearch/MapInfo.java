package EasySearch;


import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MapInfo {
    private Cell    cells[][];

    private String policeStationPosition;
    private String thiefPosition;

    private Map<String, Integer> bankInfo = new HashMap<String, Integer>();

    private int N;

    public MapInfo() {
    }

    public MapInfo(int n) {
        N = n;
        cells = new Cell[n][n];
    }

    public void setCell(int x, int y, Cell cell){
        cells[x][y] = cell;
    }

    public Cell getCell(int x, int y){
        //System.out.println("getCell: x: " + x + "******y: " + y );
        return cells[x][y];
    }

    public void setMapSize(int n){
        N = n;
        cells = new Cell[n][n];
    }

    public int getMapSize(){
        return N;
    }

    //(0,0),true,true,true,true,0"
    public String toMazeMap(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (int x = 0; x < getMapSize(); ++x){
            for (int y = 0; y < getMapSize(); ++y){
                stringBuffer.append("(" + x + "," + y + "),");

                stringBuffer.append(Boolean.toString(getCell(x,y).isEast())+",");
                stringBuffer.append(Boolean.toString(getCell(x,y).isSouth())+",");
                stringBuffer.append(Boolean.toString(getCell(x,y).isWest())+",");
                stringBuffer.append(Boolean.toString(getCell(x,y).isNorth())+",");

                stringBuffer.append(""+ getCell(x, y).getCharacter());
                stringBuffer.append(" ");
            }
        }
        stringBuffer.append("],");
        return stringBuffer.toString();
    }

    public String toEasyMap(){
        StringBuffer stringBuffer = new StringBuffer();
        for (int x = 0; x < getMapSize(); ++x){
            for (int y = 0; y < getMapSize(); ++y){
                stringBuffer.append(getCell(x, y).getCharacter());
            }
            stringBuffer.append("\r\n");
        }
        return stringBuffer.toString();
    }

    private boolean isValid(int x, int y){
        return  0 <= x && x < getMapSize() && 0 <= y && y < getMapSize();
    }

    private static String cellToString(int x, int y){
        return "[" + x + "," + y + "]";
    }

    public boolean isMovementValid(String currentPosition, String movement, int distance) {
        Coordinate current = Coordinate.string2Value(currentPosition);
        Coordinate target = Coordinate.string2Value(movement);

        return isMovementValid(current, target, distance);
    }

    private boolean isMovementValid(Coordinate current, Coordinate target, int distance){

        int xt = target.getX();
        int yt = target.getY();

        Vector<Coordinate> surrounds = new Vector<Coordinate>();
        surrounds.add(current);
        int layerPoints = 1;
        while(distance > 0 && !surrounds.isEmpty()){
            Coordinate cur= surrounds.get(0);
            int x = cur.getX();
            int y = cur.getY();
            surrounds.remove(0);
            --layerPoints;

            if (isValid(x, y + 1)) {
                if (!cells[x][y].isNorth() && !cells[x][y + 1].isSouth()) {
                    if (x == xt && y + 1 == yt) {
                        return true;
                    }
                    surrounds.add(new Coordinate(x, y + 1));
                }
            }

            if (isValid(x+1, y)){
                if (!cells[x][y].isEast() && !cells[x+1][y].isWest()){
                    if (x+1 == xt && y == yt){
                        return true;
                    }
                    surrounds.add(new Coordinate(x+1, y));
                }
            }

            if (isValid(x, y-1)){
                if (!cells[x][y].isSouth() && !cells[x][y-1].isNorth()){
                    if (x == xt && y-1 == yt){
                        return true;
                    }
                    surrounds.add(new Coordinate(x, y-1));
                }
            }

            if (isValid(x-1, y)){
                if (!cells[x][y].isWest() && !cells[x-1][y].isEast()){
                    if (x-1 == xt && y == yt){
                        return true;
                    }
                    surrounds.add(new Coordinate(x-1, y));
                }
            }

            if (layerPoints == 0){
                --distance;
                layerPoints = surrounds.size();
            }
        }

        return false;
    }
}
