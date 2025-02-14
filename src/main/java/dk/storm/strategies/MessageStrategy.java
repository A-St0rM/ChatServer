package dk.storm.strategies;

import dk.storm.server.handlers.ClientHandler;
import dk.storm.interfaces.IMessageStrategy;

public class MessageStrategy implements IMessageStrategy {

    @Override
    public void execute(String restMessage, ClientHandler client) {
        client.getServer().broadcast(client.getName() +": " + restMessage);
    }

}
