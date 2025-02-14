package dk.storm.Strategies;

import dk.storm.Server.ClientHandler;
import dk.storm.Interfaces.IMessageStrategy;

public class JoinStrategy implements IMessageStrategy {

    @Override
    public void execute(String restMessage, ClientHandler client){
        client.setName(restMessage);
        client.getServer().broadcast("A new person joined the chat. Welcome to: " + restMessage);
    }
}
