package hex.music.mysql.database;

import java.util.Map;

/**
 * Created 2014-12-10
 *
 * @author hl
 */
public interface DatabaseConnector {

    public void create(String databaseName);

    public void drop(String databaseName);

    public String getJdbcDriverClassName();

    public String getDatabaseUrl(String databaseName);

    public String getDisplayName();

    public String getHibernateDialectClassName();

    public Map getFlavoredProperties();
}
