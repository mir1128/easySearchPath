package EasySearch;

import java.util.Random;

public class MapAutoGenerator{
    private MapInfo mapInfo = new MapInfo();

    private boolean[][] visited;
    private boolean done = false;

    public MapInfo generate(int n) {
        mapInfo.setMapSize(n);
        init();
        Random random = new Random();
        generate(1, 1);
        return mapInfo;
    }

    private void init() {
        int n = mapInfo.getMapSize();
        visited = new boolean[n][n];
        for (int y = 0; y < n; y++) visited[0][y] = visited[n-1][y] = true;
        for (int x = 0; x < n; x++) visited[x][0] = visited[x][n-1] = true;

        for (int y = 0; y < n ; y++)
            for (int x = 0; x < n ; x++)
                mapInfo.setCell(x, y, new Cell(true, true, true, true));
    }

    private void generate(int x, int y) {
        visited[x][y] = true;

        while (!visited[x][y+1] || !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y]) {
            while (true) {
                double r = Math.random();
                if (r < 0.25 && !visited[x][y+1]) {
                    mapInfo.getCell(x, y).setNorth(false);
                    mapInfo.getCell(x, y+1).setSouth(false);
                    generate(x, y + 1);
                    break;
                }
                else if (r >= 0.25 && r < 0.50 && !visited[x+1][y]) {
                    mapInfo.getCell(x, y).setEast(false);
                    mapInfo.getCell(x+1, y).setWest(false);
                    generate(x + 1, y);
                    break;
                }
                else if (r >= 0.5 && r < 0.75 && !visited[x][y-1]) {
                    mapInfo.getCell(x, y).setSouth(false);
                    mapInfo.getCell(x, y-1).setNorth(false);
                    generate(x, y - 1);
                    break;
                }
                else if (r >= 0.75 && r < 1.00 && !visited[x-1][y]) {
                    mapInfo.getCell(x,y).setWest(false);
                    mapInfo.getCell(x-1, y).setEast(false);
                    generate(x - 1, y);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        try{
            MapAutoGenerator mapGenerator = new MapAutoGenerator();
            MapInfo mapInfo = mapGenerator.generate(25);
            MapViewer.display(mapInfo, 1, 1, 2, 3);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
