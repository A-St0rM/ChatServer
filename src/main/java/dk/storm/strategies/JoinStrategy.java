package dk.storm.strategies;

import dk.storm.server.handlers.ClientHandler;
import dk.storm.interfaces.IMessageStrategy;

public class JoinStrategy implements IMessageStrategy {

    @Override
    public void execute(String restMessage, ClientHandler client){
        client.setName(restMessage);
        client.getServer().broadcast("A new person joined the chat. Welcome to: " + restMessage);
    }
}
