package DesignPattern;

import java.util.HashMap;
import java.util.Map;

public class MessageStrategyFactory {

    //Factory design pattern
    private static Map<String, IMessageStrategy> stratagies = new HashMap<>();

    static {
        stratagies.put("#JOIN", new JoinStrategy());
        stratagies.put("#MESSAGE", new MessageStrategy());
        stratagies.put("#PRIVATE", new MessageStrategy());
        stratagies.put("#LEAVE ", new MessageStrategy());
    }

    public static IMessageStrategy getStrategy(String strategy){
        return stratagies.getOrDefault(strategy, new IMessageStrategy() {
            @Override
            public void execute(String message, ClientHandler clientHandler) {
                clientHandler.notify("Your command is invalid. No command with that name: " + message);
            }
        });
    }
}
