package DesignPattern;

public class JoinStrategy implements IMessageStrategy{

    @Override
    public void execute(String message, ClientHandler client){
        client.setName(message);
        client.getServer().broadcast("A new person joined the chat. Welcome to: " + message);
    }
}
