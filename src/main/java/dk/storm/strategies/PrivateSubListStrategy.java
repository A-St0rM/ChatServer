package dk.storm.strategies;

import dk.storm.interfaces.IMessageStrategy;
import dk.storm.server.handlers.ClientHandler;

import java.util.ArrayList;
import java.util.List;

public class PrivateSubListStrategy implements IMessageStrategy {

    @Override
    public void execute(String restMessage, ClientHandler client) {
        String[] rest = restMessage.split(" ",2);
        String[] targetClients = rest[0].split(",");

        if (rest.length < 2) {
            client.notify("Invalid private message format. Use: #PRIVATE <recipient> <message>");
            return;
        }

        String privateMessage = rest[1];

        sendPrivateMessage(targetClients, privateMessage, client);
    }

    private void sendPrivateMessage(String[] targetClients, String privateMessage, ClientHandler sender) {

        List<ClientHandler> clients = sender.getServer().getClients(); // Henter alle klienter
        List<ClientHandler> validClients = new ArrayList<>();


        for(int i = 0; i < targetClients.length; i++) {
            for (ClientHandler ch : clients) {
                if (ch.getName().equals(targetClients[i])) {
                    validClients.add(ch);
                }
            }
        }


        // Brug en anonym klasse til Thread i stedet for lambda
        new Thread(new Runnable() {
            @Override
            public void run() {

                for(ClientHandler tagetClient : validClients){
                    tagetClient.notify("Private message from " + sender.getName() + ": " + privateMessage);
                }

            }
        }).start();
    }
}
