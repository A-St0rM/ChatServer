package dk.storm.Interfaces;

import dk.storm.Server.ClientHandler;

public interface IMessageStrategy {
    //Strategy pattern
    void execute(String restMessage, ClientHandler client);

}
