package main;

import java.sql.*;

public class Model {
    protected Connection connection;

    public Model() {
        connection = createConnection();
    }
    public Model(Connection connection) {
        this.connection = connection;
    }

    public static ResultSet executeQuery(PreparedStatement stmt) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return rs;
    }

    public static Connection createConnection() {
        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            c =
                    DriverManager.getConnection("jdbc:mysql://localhost/pigkprojekt?" +
                            "user=pigk_user&password=123");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return c;
    }

    protected static PreparedStatement prepareStatement(String sql, Connection connection) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return statement;
    }

    protected static void executeUpdate(PreparedStatement stmt) {
        try {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    protected static void cleanup(PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) {
            } // ignore

            rs = null;
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) {
            } // ignore

            stmt = null;
        }
    }

    protected PreparedStatement prepareStatement(String sql) {
        return prepareStatement(sql, connection);
    }

    protected void cleanup(PreparedStatement stmt) {
        ResultSet rs = null;
        cleanup(stmt, rs);
    }
}
