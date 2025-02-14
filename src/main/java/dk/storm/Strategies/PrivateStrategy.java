package dk.storm.Strategies;

import dk.storm.Server.ClientHandler;
import dk.storm.Interfaces.IMessageStrategy;

import java.util.List;
import java.util.Random;

public class PrivateStrategy implements IMessageStrategy {

    @Override
    public void execute(String restMessage, ClientHandler client) {
        String[] splitMessage = restMessage.split(" ", 2);

        if (splitMessage.length < 2) {
            client.notify("Invalid private message format. Use: #PRIVATE <recipient> <message>");
            return;
        }

        String targetClientName = splitMessage[0];
        String privateMessage = splitMessage[1];

        sendPrivateMessage(targetClientName, privateMessage, client);
    }

    private void sendPrivateMessage(String targetName, String privateMessage, ClientHandler sender) {
        Random random = new Random();
        int randomDelay = random.nextInt(5000); // Random delay mellem 0-5000ms

        List<ClientHandler> clients = sender.getServer().getClients(); // Henter alle klienter

        ClientHandler targetClient = null;
        for (ClientHandler ch : clients) {
            if (ch.getName().equals(targetName)) {
                targetClient = ch;
                break;
            }
        }

        if (targetClient == null) {
            sender.notify("User " + targetName + " not found.");
            return;
        }

        final ClientHandler finalTargetClient = targetClient; // Gør variablen final til brug i tråden

        // Brug en anonym klasse til Thread i stedet for lambda
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(randomDelay);
                    finalTargetClient.notify("Private message from " + sender.getName() + ": " + privateMessage);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
