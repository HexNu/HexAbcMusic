package hex.music.mysql.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Created 2014-12-10
 *
 * @author hl
 * @param <T>
 * @param <ID>
 */
public class GenericDao<T, ID extends Serializable> {

    private final EntityManager entityManager;
    private final Class<T> persistentClass;

    public GenericDao(Class c, EntityManager entityManager) {
        persistentClass = c;
        this.entityManager = entityManager;
    }

    protected EntityManager getManager() {
        return entityManager;
    }

    public T findByPrimaryKey(ID id) {
        return (T) getManager().find(persistentClass, id);
    }

    protected T save(T entity) {
        getManager().persist(entity);
        getManager().flush();
        return entity;
    }

    protected T update(T entity) {
        return getManager().merge(entity);
    }

    public void delete(T entity) {
        getManager().remove(entity);
    }

    public List<T> findByExample(T exampleInstance) {
        return null;
    }

    public List<T> findAll() {
        return getManager().createQuery("FROM " + persistentClass.getName()).getResultList();
    }
}
