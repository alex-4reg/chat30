package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    public static void main(String[] args) {
        ArrayList<Socket> sockets = new ArrayList<>();
        HashMap<Integer, String> clientData = new HashMap<>();

        try {
            ServerSocket serverSocket = new ServerSocket(9178);
            System.out.println("������ �������!");
            while (true) {
                // ������ ����� ��� ��������������� �������
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                System.out.println("������ �����������");
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DataInputStream is = new DataInputStream(socket.getInputStream());
                            DataInputStream in = new DataInputStream(socket.getInputStream());
                            // ����� ������ ��� socket
                            // ��������� ������ "��� ���� �����?"
                            // �������� ����� � ���������
                            while (true) {
                                String request = is.readUTF();
                                System.out.println(request);
                                for (Socket socket1 : sockets) {
                                    DataOutputStream out = new DataOutputStream(socket1.getOutputStream());
                                    out.writeUTF("Server: " + request);
                                    System.out.println(socket1.getPort());
                                    //clientData.putIfAbsent(socket1.getPort()) socket1.getPort()
                                    /**/
                                    int clientPort = socket1.getPort();
                                    if (!clientData.containsKey(clientPort)) {
                                        out.writeUTF("��� ���� �����?");
                                        String name = in.readUTF();
                                        clientData.put(clientPort, name);
                                        out.writeUTF("������, " + name + ". ��� ���� = " + clientPort);
                                    }
                                    //System.out.println(socket1.getPort());
                                    //if(userNames. != null) out.writeUTF("��� ���� �����?");
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