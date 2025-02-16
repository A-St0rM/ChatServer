package dk.storm.strategies;

import dk.storm.interfaces.IMessageStrategy;
import dk.storm.server.handlers.ClientHandler;

import java.util.HashMap;
import java.util.Map;

public class HelpStrategy implements IMessageStrategy {
    @Override
    public void execute(String restMessage, ClientHandler client) {
        Map<String, IMessageStrategy> strategies = MessageStrategyFactory.getStrategies();

        int counter = 1;
        for(Map.Entry<String, IMessageStrategy> entry : strategies.entrySet()){
             client.getServer().broadcast(counter + ": " + entry.getKey());
             counter++;
        }
    }
}
