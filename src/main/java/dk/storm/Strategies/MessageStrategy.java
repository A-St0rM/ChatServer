package dk.storm.Strategies;

import dk.storm.Server.ClientHandler;
import dk.storm.Interfaces.IMessageStrategy;

public class MessageStrategy implements IMessageStrategy {

    @Override
    public void execute(String restMessage, ClientHandler client) {
        client.getServer().broadcast(client.getName() +": " + restMessage);
    }

}
