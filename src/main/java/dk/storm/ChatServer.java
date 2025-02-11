package dk.storm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer implements IObservable{

    //Singleton design pattern
    private ChatServer(){};

    public static synchronized IObservable getInstance(){
        if(server == null){
            server = new ChatServer();
        }
        return server;
    }

    private static volatile IObservable server = getInstance();


    private List<ClientHandler> clients = new ArrayList<>();

    public void startServer(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                Socket client = server.accept();
                ClientHandler clientHandler = new ClientHandler(client, this);
                new Thread(clientHandler).start();
                clients.add(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.startServer(7070);
    }

    @Override
    public void broadcast(String message) {
        for(ClientHandler ch : clients){
            ch.notify(message);
        }
    }

    private static class ClientHandler implements Runnable, IObserver{
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private IObservable server;
        private String name = "User";

        public ClientHandler(Socket client, IObservable server) throws IOException {
            this.client = client;
            this.server = server;
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        }

        @Override
        public void run(){
            String message;

            try {
                while ((message = in.readLine()) != null) {
                    //System.out.println(message);
                    if(message.startsWith("#JOIN")){
                        String name = message.split(" ")[1];
                        server.broadcast("A new person joined the chat. Welcome to " + name);
                        this.name = name;
                    }
                    else{
                        server.broadcast(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void notify(String message) {
            out.println(name + ": " + message);
        }
    }
}
