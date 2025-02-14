package dk.storm.strategies;

import dk.storm.interfaces.IMessageStrategy;
import dk.storm.server.handlers.ClientHandler;

import java.util.HashMap;
import java.util.Map;

public class MessageStrategyFactory {

    //Factory design pattern
    private static Map<String, IMessageStrategy> stratagies = new HashMap<>();

    static {
        stratagies.put("#JOIN", new JoinStrategy());
        stratagies.put("#MESSAGE", new MessageStrategy());
        stratagies.put("#PRIVATE", new PrivateStrategy());
        stratagies.put("#LEAVE", new LeaveStrategy());
    }


    public static IMessageStrategy getStrategy(String strategy){
        return stratagies.getOrDefault(strategy, new IMessageStrategy() {
            @Override
            public void execute(String restMessage, ClientHandler client) {
                client.notify("Your command is invalid. No command with that name: " + restMessage);
            }
        });
    }
}
