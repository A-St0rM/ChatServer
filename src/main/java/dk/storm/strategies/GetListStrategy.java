package dk.storm.strategies;

import dk.storm.interfaces.IMessageStrategy;
import dk.storm.server.handlers.ClientHandler;

import java.util.List;

public class GetListStrategy implements IMessageStrategy {

    @Override
    public void execute(String restMessage, ClientHandler client) {

        List<ClientHandler> clients = client.getClients();

        client.getServer().broadcast("Here is a list of all active clients");
        for(ClientHandler ch : clients){
            client.getServer().broadcast(ch.getName());
        }

    }
}
