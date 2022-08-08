package zad1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    String url;
    TravelData data;
    Connection conn;
    public Database(String url, TravelData travelData) {
        this.url=url;
        this.data=travelData;
    }

    public void create() {
        try {
            Class.forName("com.mysql.jdb.Driver");
            conn = DriverManager.getConnection(url);
            Statement s=conn.createStatement();
            int Result=s.executeUpdate("CREATE DATABASE pro1");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void showGui() {
    }
}
