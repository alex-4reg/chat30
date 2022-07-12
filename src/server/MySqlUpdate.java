package server;

import java.sql.*;

public class MySqlUpdate {

    String db_url;
    String db_login;
    String db_pass;

    public MySqlUpdate() {
    }

    public MySqlUpdate(String db_url, String db_login, String db_pass) {
        this.db_url = db_url;
        this.db_login = db_login;
        this.db_pass = db_pass;
    }

    protected void insertRow(String tableName, String[] values) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(db_url, db_login, db_pass);
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `android_30`.`users` (`name`, `lastname`, `phone`" +
                    ", `password`) VALUES ('" + values[1] + "', '" + values[2] + "', '" + values[3] + "', '" + values[4] + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
