package EasySearch;

public class MapViewer {
    public static void display(MapInfo mapInfo, int currentX, int currentY, int targetX, int targetY) {
        StdDraw.clear();
        StdDraw.setXscale(0, mapInfo.getMapSize());
        StdDraw.setYscale(0, mapInfo.getMapSize());

        StdDraw.setPenColor(StdDraw.RED);

        StdDraw.setPenColor(StdDraw.BLACK);
        for (int x = 1; x < mapInfo.getMapSize()-1; x++) {
            for (int y = 1; y < mapInfo.getMapSize()-1; y++) {
                if (mapInfo.getCell(x, y).isSouth()) StdDraw.line(x, y, x + 1, y);
                if (mapInfo.getCell(x, y).isNorth()) StdDraw.line(x, y+1 , x+1 , y + 1);
                if (mapInfo.getCell(x, y).isWest())  StdDraw.line(x, y, x, y+1 );
                if (mapInfo.getCell(x, y).isEast())  StdDraw.line(x+1 , y + 1, x + 1, y);
            }
        }

        StdDraw.circle(currentX+0.5, currentY+0.5,0.5);
        StdDraw.filledCircle(targetX+0.5, targetY+0.5,  0.5);

        StdDraw.show(1);
    }
}
