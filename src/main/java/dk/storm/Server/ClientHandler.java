package dk.storm.Server;


import dk.storm.Interfaces.IObserver;
import dk.storm.Strategies.MessageStrategyFactory;

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

                String command = message.split(" ")[0];
                String restMessage = message.substring(command.length()+1);

                MessageStrategyFactory.getStrategy(command).execute(restMessage, this);

//                if(message.startsWith("#JOIN")){
//                    String name = message.split(" ")[1];
//                    server.broadcast("A new person joined the chat. Welcome to " + name);
//                    this.name = name;
//                }
//                else if(message.equals("#LEAVE")){
//                    server.broadcast(name + " just left the chat server. Bye bye.....");
//                    client.close();
//                }
//                else if(message.startsWith("#PRIVATE")){
//                    String[] msgSplit  = message.split(" ", 3);
//
//                    String name = msgSplit[1];
//                    String privateMessage = msgSplit[2];
//
//                    sendPrivateMessage(name, privateMessage);
//                }

//
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notify(String message) {
        out.println(message);
    }

    public void sendPrivateMessage(String name, String privateMessage){
        Random random = new Random();
        int randomDelay = random.nextInt(5000); // Random delay between 0-5000ms

        ClientHandler targetClient = null;
        for (ClientHandler ch : clients) {
            if (ch.name.equals(name)) {
                targetClient = ch;
                break;
            }
        }

        if (targetClient == null) {
            out.println("User " + name + " not found.");
            return;
        }

        // Start a new thread to send the message after randomDelay
        ClientHandler finalTargetClient = targetClient;
        new Thread(() -> {
            try {
                Thread.sleep(randomDelay);
                finalTargetClient.notify("Private message from " + name + ": " + privateMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
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