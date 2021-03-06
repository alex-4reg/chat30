package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class Server {
    static ArrayList<User> users = new ArrayList<>();
    static String db_url = "jdbc:mysql://localhost/android_30";
    static String db_login = "root";
    static String db_pass = "QjhLi36tA";

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9174);
            System.out.println("Server started!");
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            while (true) {
                // Create socket for connected client
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                User user = new User(socket);
                /**/
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            boolean isAuth = false;
                            while (!isAuth) {
                                String authData = user.getIs().readUTF();
                                String phone = authData.split("//")[1];
                                String pass = authData.split("//")[2];
//                                user.getOut().writeUTF("Phone: ");
//                                String phone = user.getIs().readUTF();
//                                user.getOut().writeUTF("Password: ");
//                                String pass = user.getIs().readUTF();
                                Connection connection = DriverManager.getConnection(db_url, db_login, db_pass);
                                Statement statement = connection.createStatement();
                                ResultSet resultSet = statement.executeQuery("SELECT * FROM `users` " +
                                        "WHERE `phone`='" + phone + "' AND `password`='" + pass + "'");
                                System.out.println(resultSet.next());
                                System.out.println(resultSet.getString("name"));
                                if (resultSet.next()) isAuth = true;
                                else user.getOut().writeUTF("error");
                            }
                            user.getOut().writeUTF("success");
                            users.add(user);
                            //System.out.println(resultSet.getString("name"));
                            user.getOut().writeUTF("Input name: ");
                            user.setName(user.getIs().readUTF());
                            while (true) {
                                String request = user.getIs().readUTF();
                                System.out.println(request);
                                broadCastMessage(request, user.getUuid());
                            }
                        } catch (Exception e) {
                            users.remove(user);
                            broadCastMessage(user.getName() + " disconnected");
                        }
                    }
                });
                thread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void broadCastMessage(String message, UUID uuid) {

        for (User user : users) {
            try {
                if (!user.getUuid().toString().equals(uuid.toString())) {
                    user.getOut().writeUTF(user.getName() + ": " + message);
                } else {
                    System.out.println(user.getName() + ": " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadCastMessage(String message) {
        System.out.println(message);
        for (User user : users) {
            try {
                user.getOut().writeUTF("Server: " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}