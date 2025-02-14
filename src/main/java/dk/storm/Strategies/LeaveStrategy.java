package dk.storm.Strategies;

import dk.storm.Interfaces.IMessageStrategy;
import dk.storm.Server.ClientHandler;

public class LeaveStrategy implements IMessageStrategy {
    @Override
    public void execute(String restMessage, ClientHandler client) {
        System.out.println("[DEBUG] Executing LeaveStrategy for: " + client.getName());

        client.getServer().removeClient(client);
        client.getServer().broadcast(client.getName() + " just left the server. Bye bye.....");
        client.closeClient();
    }


}
