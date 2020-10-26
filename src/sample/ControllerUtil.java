package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ControllerUtil {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/users?&serverTimezone=UTC";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "1234";
    Connection conn=null;
    public static Connection conDB() throws ClassNotFoundException {
        try {
            Class.forName(DB_DRIVER);
            return DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
        }
    }

