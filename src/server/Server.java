package server;

import client.ChatMember;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    public static void main(String[] args) {
        ArrayList<Socket> sockets = new ArrayList<>();
        HashMap<Integer, ChatMember> clientData = new HashMap<>();

        try {
            ServerSocket serverSocket = new ServerSocket(9178);
            System.out.println("Сервер запущен!");
            while (true) {
                // Create socket for connected client
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                System.out.println("Клиент подключился");
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DataInputStream is = new DataInputStream(socket.getInputStream());
                            DataInputStream in = new DataInputStream(socket.getInputStream());
                            // output stream for socket
                            // send request "What's your name?"
                            // receive answer and retain it
                            while (true) {
                                String request = is.readUTF();
                                System.out.println(request);
                                for (Socket socket1 : sockets) {
                                    DataOutputStream out = new DataOutputStream(socket1.getOutputStream());
                                    out.writeUTF("Server: " + request);
                                    int clientPort = socket1.getPort();
                                    if (!clientData.containsKey(clientPort)) {
                                        out.writeUTF("Как тебя зовут?");
                                        String name = in.readUTF();
                                        System.out.println("Добавлен пользователь чата " + name +
                                                ", port: " + clientPort);
                                        clientData.put(clientPort, new ChatMember(name, clientPort));
                                        out.writeUTF("Привет, " + name);
                                    }
                                    /*
                                    for (ChatMember user: clientData.values()){
                                        System.out.print(user.getNAME() + ", ");
                                    }
                                    */
                                }

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}