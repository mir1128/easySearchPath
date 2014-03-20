package EasySearch;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static int MAP_SIZE = 25;

    public static Coordinate translateInputMessage(String message, Coordinate currentPosition){
        Coordinate coordinate = null;
        if (message.equalsIgnoreCase("w")){
            coordinate = new Coordinate(currentPosition.getX(), currentPosition.getY()+1);
        }else if (message.equalsIgnoreCase("a")){
            coordinate = new Coordinate(currentPosition.getX()-1, currentPosition.getY());
        }else if (message.equalsIgnoreCase("s")){
            coordinate = new Coordinate(currentPosition.getX(), currentPosition.getY()-1);
        }else if (message.equalsIgnoreCase("d")){
            coordinate = new Coordinate(currentPosition.getX()+1, currentPosition.getY());
        }

        return coordinate;
    }

    public static void main(String[] args) throws IOException {
        MapAutoGenerator mapAutoGenerator = new MapAutoGenerator();
        MapInfo mapInfo = mapAutoGenerator.generate(MAP_SIZE);

        String mapMaze = mapInfo.toMazeMap();

        Random random = new Random();
        int currentPositionX = random.nextInt(MAP_SIZE-2)+1;
        int currentPositionY = random.nextInt(MAP_SIZE-2)+1;

        int targetPositionX = random.nextInt(MAP_SIZE-2)+1;
        int targetPositionY = random.nextInt(MAP_SIZE-2)+1;

        String message = "from:(" + currentPositionX + "," + currentPositionY + ") "
                       + "to:(" + targetPositionX + "," + targetPositionY + ") "
                       + "map:" + mapMaze;

        MapViewer.display(mapInfo, currentPositionX, currentPositionY, targetPositionX, targetPositionY);

        Server server = new Server();
        if (!server.startAndAccept()){
            System.out.println("error");
        }

        server.sendMessage(message);

        String receiveMessage = server.receiveMessage();
        Coordinate coordinate = translateInputMessage(receiveMessage.trim(), new Coordinate(currentPositionX, currentPositionY));
        while (coordinate == null){
            server.sendMessage("invalid movement.");
        }

        boolean isFinished = false;
        while (!isFinished){
            String currentPosition = Coordinate.value2String(new Coordinate(currentPositionX, currentPositionY));
            if (mapInfo.isMovementValid(currentPosition, Coordinate.value2String(coordinate), 1)) {
                MapViewer.display(mapInfo, coordinate.getX(), coordinate.getY(), targetPositionX, targetPositionY);
                currentPositionX      = coordinate.getX();
                currentPositionY   = coordinate.getY();

                if (currentPositionX == targetPositionX && currentPositionY == targetPositionY){
                    isFinished = true;
                    server.sendMessage("Great!!!!! You made it!");
                }
            } else {
                server.sendMessage("invalid movement.");
            }
            receiveMessage = server.receiveMessage();
            coordinate = translateInputMessage(receiveMessage.trim(), new Coordinate(currentPositionX, currentPositionY));
            server.sendMessage("finished.");
        }
    }
}
