package hex.music.service.support;

import hex.music.mysql.database.ConnectorFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created 2014-12-10
 *
 * @author hl
 */
class ThreadSafeEmHandler implements EmHandler {

    private final EntityManagerFactory entityManagerFactory;
    private final ThreadLocal<EntityManager> threadLocalManager = new ThreadLocal<EntityManager>() {

        @Override
        protected EntityManager initialValue() {
            return entityManagerFactory.createEntityManager();
        }
    };

    public ThreadSafeEmHandler(Type type, String databaseName) {
        ConnectorFactory.getConnector().create(databaseName);
        Map properties = setupProperties(databaseName);
        entityManagerFactory = Persistence.createEntityManagerFactory(type.getPersistenceUnitPrototypeName(), properties);
    }

    protected Map getSpecialisedProperties() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public void close() {
        entityManagerFactory.close();
    }

    @Override
    public EntityManager getManager() {
        return threadLocalManager.get();
    }

    @Override
    public void closeManager() {
        if (getManager().isOpen()) {
            getManager().close();
        }
        threadLocalManager.remove();
    }

    private Map setupProperties(String databaseName) {
        Map properties = new HashMap();
        properties.put("javax.persistence.jdbc.url", ConnectorFactory.getConnector().getDatabaseUrl(databaseName));
        properties.put("javax.persistence.jdbc.driver", ConnectorFactory.getConnector().getJdbcDriverClassName());
        properties.put("hibernate.dialect", ConnectorFactory.getConnector().getHibernateDialectClassName());
        properties.putAll(ConnectorFactory.getConnector().getFlavoredProperties());
        properties.putAll(getSpecialisedProperties());
        return properties;
    }
}
