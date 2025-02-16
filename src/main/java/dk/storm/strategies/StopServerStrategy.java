package dk.storm.strategies;

import dk.storm.interfaces.IMessageStrategy;
import dk.storm.server.handlers.ClientHandler;

import java.io.IOException;
import java.util.List;

public class StopServerStrategy implements IMessageStrategy {
    @Override
    public void execute(String restMessage, ClientHandler client) {
        client.getServer().broadcast("A client requested to shut down the server..");

        List<ClientHandler> clients = client.getClients();

        for(ClientHandler ch : clients){
            ch.closeClient();
        }
        client.getServer().stopServer();
    }
}
