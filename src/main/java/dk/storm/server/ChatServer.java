package dk.storm.server;

import dk.storm.interfaces.IObservable;
import dk.storm.server.handlers.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer implements IObservable {

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
            //Thread pool
            ExecutorService executorService = Executors.newCachedThreadPool();
            while (true) {
                Socket client = server.accept();
                ClientHandler clientHandler = new ClientHandler(client, this, clients);
                //new Thread(clientHandler).start();
                executorService.submit(clientHandler);

                clients.add(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ClientHandler> getClients(){
        return clients;
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.startServer(7070);
    }

    @Override
    public void broadcast(String message) {
        System.out.println("[DEBUG] Broadcasting: " + message);
        for (ClientHandler ch : clients) {
            ch.notify(message);
        }
    }

    public void removeClient(ClientHandler client){
        clients.remove(client);
    }
}
