package hex.music.service.command;

import hex.music.mysql.dao.DaoFactory;
import hex.music.service.exception.ServiceException;
import hex.music.service.support.PuHandlerFactory;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created 2014-12-10
 *
 * @author hl
 * @param <T>
 */
public abstract class AbstractServiceCommand<T> implements ServiceCommand<T> {

    private static final PuHandlerFactory puHandlerFactory = new PuHandlerFactory();
    private String fingerprint;

    protected String getFingerprint() {
        return fingerprint;
    }

    private EntityManager getManager() {
        return puHandlerFactory.get(fingerprint).getManager();
    }

    protected CriteriaBuilder getCriteriaBuilder() {
        return getManager().getCriteriaBuilder();
    }

    protected DaoFactory getDaoFactory() {
        return new DaoFactory(getManager());
    }

    protected PuHandlerFactory getPuHandlerFactory() {
        return puHandlerFactory;
    }

    protected <S> S executeSubcommand(ServiceCommand<S> subcommand) {
        subcommand.setFingerprint(fingerprint);
        return subcommand.execute();
    }

    protected <S> TypedQuery<S> executeQuery(String query, Class<S> s) {
        TypedQuery<S> createQuery = getManager().createQuery(query, s);
        return createQuery;
    }

    @Override
    public final void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    @Override
    public Object getSynchronizationObject() {
        throw new ServiceException("Synkronisering ej tillåten för detta kommando", null);
    }
}
