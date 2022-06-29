package client;

public class ChatMember {
    private final String NAME;
    private int port;

    public ChatMember(String name, int port) {
        NAME = name;
        this.port = port;
    }

    public String getNAME() {
        return NAME;
    }

    public int getPort() {
        return port;
    }


}
