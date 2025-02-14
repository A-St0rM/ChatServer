package dk.storm.interfaces;

import dk.storm.server.handlers.ClientHandler;

public interface IMessageStrategy {
    //Strategy pattern
    void execute(String restMessage, ClientHandler client);

}
