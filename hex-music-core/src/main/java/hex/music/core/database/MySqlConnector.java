package hex.music.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created 2014-12-10
 *
 * @author hl
 */
public class MySqlConnector implements DatabaseConnector {

    @Override
    public void create(String databaseName) {
        try {
            Class.forName(getJdbcDriverClassName());
            try (Connection conn = createConnection(); Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(MySqlConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getJdbcDriverClassName() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String getDatabaseUrl(String databaseName) {
        return "jdbc:mysql://" + System.getProperty("hex.host", "localhost") + (databaseName.length() > 0 ? "/" + databaseName : "");
    }

    @Override
    public String getDisplayName() {
        return "MySQL";
    }

    @Override
    public String getHibernateDialectClassName() {
        return "org.hibernate.dialect.MySQLDialect";
    }

    @Override
    public void drop(String databaseName) {
        try {
            try (Connection conn = createConnection(); Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DROP DATABASE " + databaseName);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(MySqlConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName(getJdbcDriverClassName());
        Connection conn = DriverManager.getConnection(getDatabaseUrl(""),
                System.getProperty("hex.username", "hex"),
                System.getProperty("hex.password", "hex"));
        return conn;
    }

    @Override
    public Map getFlavoredProperties() {
        return new HashMap() {
            {
                put("hibernate.connection.username", System.getProperty("hex.username", "hex"));
                put("hibernate.connection.password", System.getProperty("hex.password", "hex"));
            }
        };
    }
}
