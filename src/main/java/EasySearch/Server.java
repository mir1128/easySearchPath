package EasySearch;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private BufferedReader reader = null;
    private PrintWriter writer = null;
    private ServerSocket server = null;
    Socket client = null;

    public Server() {
        try {
            server = new ServerSocket(4321);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean startAndAccept(){
        try {
            client = server.accept();
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void stop(){
        try {
            reader.close();
            client.close();
            writer.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        writer.println(message);
    }

    public String receiveMessage() throws IOException{
        int SIZE = 1024;
        char buffer[] = new char[SIZE];

        StringBuilder stringBuilder = new StringBuilder();

        int thunk;
        while ((thunk = reader.read(buffer, 0, SIZE)) == 1024){
            stringBuilder.append(buffer);
        }
        if (thunk != -1){
            stringBuilder.append(buffer, 0, thunk);
        }

        return stringBuilder.toString();
    }
}
