package hex.music.mysql.database;

/**
 * Created 2014-12-10
 *
 * @author hl
 */
public class ConnectorFactory {

    private static final DatabaseConnector connector = new MySqlConnector();

    public static DatabaseConnector getConnector() {
        return connector;
    }
}
