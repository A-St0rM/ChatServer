package dk.storm.strategies;

import dk.storm.interfaces.IMessageStrategy;
import dk.storm.server.handlers.ClientHandler;

public class LeaveStrategy implements IMessageStrategy {
    @Override
    public void execute(String restMessage, ClientHandler client) {
        System.out.println("[DEBUG] Executing LeaveStrategy for: " + client.getName());

        client.getServer().removeClient(client);
        client.getServer().broadcast(client.getName() + " just left the server. Bye bye.....");
        client.closeClient();
    }


}
