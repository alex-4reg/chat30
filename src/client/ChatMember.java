package client;

public class ChatMember {
    private String name;
    private int port;

    public ChatMember(String name, int port) {
        this.name = name;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }
}
