package dk.storm.server.handlers;


import dk.storm.interfaces.IObserver;
import dk.storm.strategies.MessageStrategyFactory;
import dk.storm.server.ChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class ClientHandler implements Runnable, IObserver {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private ChatServer server;
    private String name = "User";
    private List<ClientHandler> clients;

    public ClientHandler(Socket client, ChatServer server, List<ClientHandler> clients) throws IOException {
        this.client = client;
        this.server = server;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }

    @Override
    public void run(){
        String message;

        try {

            while ((message = in.readLine()) != null) {
                System.out.println(message);
                String[] strs = message.split(" ");
                String command = strs[0];


                String restMessage = strs.length > 1 ? message.substring(command.length()+1) : message;
                MessageStrategyFactory.getStrategy(command).execute(restMessage, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notify(String message) {
        out.println(message);
    }

    public void closeClient() {
        try {

            in.close();
            out.close();
            client.close();
            server.removeClient(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ChatServer getServer(){
        return this.server;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public List<ClientHandler> getClients(){
        return clients;
    }
}