package util;

import java.sql.*;

public class JDBCUtils {
    public final static String[] table = {"users", "books"};
    private final static String driver = "com.mysql.cj.jdbc.Driver";//驱动
    private final static String database = "library";//驱动
    private final static String url = "jdbc:mysql://localhost:3306/" + database + "?serverTimezone=Hongkong";
    private final static String USER = "root";
    private final static String PASSWORD = "241429";
    private static Connection conn = null;
    private static PreparedStatement stmt = null;
    private static ResultSet rs = null;

    /*注册驱动，只执行一次*/
    static {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*PreparedStatement*封装了sql语句*/
    public static void setStmt(String sql) {
        try {
            conn = DriverManager.getConnection(url, USER, PASSWORD);
            stmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setObject(int parameterIndex, Object x, int targetSqlType) {
        try {
            stmt.setObject(parameterIndex, x, targetSqlType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void execute() {
        try {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int executeUpdate() {
        try {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void executeQuery() {
        try {
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean next() {
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getString(String columnLabel) {
        try {
            return rs.getString(columnLabel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getString(int columnIndex) {
        try {
            return rs.getString(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*关闭*/
    public static void close() {
        try {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
