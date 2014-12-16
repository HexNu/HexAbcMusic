package hex.music.service.command;

import hex.music.service.exception.ServiceException;
import hex.music.service.support.LockHandler;
import hex.music.service.support.PuHandlerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

/**
 * Created 2014-12-10
 *
 * @author hl
 */
public class CommandExecutor {
    
    private PuHandlerFactory puHandlerFactory;
    private final static LockHandler LOCK_HANDLER = new LockHandler();

    public CommandExecutor() {
    }

    public CommandExecutor(PuHandlerFactory factory) {
        puHandlerFactory = factory;
    }

    private <T> T executeInternally(ServiceCommand<T> command, String fingerprint) {
        try {
            command.setFingerprint(fingerprint);
            command.setPuHandlerFactory(puHandlerFactory);
            return command.execute();
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServiceException(null, ex);
        }
    }

    public <T> T execute(ServiceCommand<T> command, String fingerprint) {
        try {
            return executeInternally(command, fingerprint);
        } finally {
            puHandlerFactory.get(fingerprint).closeManager();
        }
    }

    public <T> T executeInTransaction(ServiceCommand<T> command, String fingerprint) {
        EntityTransaction tx = puHandlerFactory.get(fingerprint).getManager().getTransaction();
        try {
            tx.begin();
            T result = executeInternally(command, fingerprint);
            tx.commit();
            return result;
        } catch (RollbackException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            tx.rollback();
            throw ex;
        } catch (Exception ex) {
            tx.rollback();
            throw new ServiceException("Transaktion misslyckades pga: " + ex.getLocalizedMessage(), ex);
        } finally {
            puHandlerFactory.get(fingerprint).closeManager();
        }
    }

    public <T> T executeInSynchronizedTransaction(ServiceCommand<T> command, String fingerprint) {
        Object synchronizationObject = command.getSynchronizationObject() != null ? command.getSynchronizationObject() : fingerprint;
        synchronized (LOCK_HANDLER.getLockObject(synchronizationObject)) {
            try {
                return executeInTransaction(command, fingerprint);
            } finally {
                puHandlerFactory.get(fingerprint).closeManager();
            }
        }
    }

//
//    private static final PuHandlerFactory puHandlerFactory = new PuHandlerFactory();
//    private final static LockHandler LOCK_HANDLER = new LockHandler();
//
//    private <T> T executeInternally(ServiceCommand<T> command, String fingerprint) {
//        try {
//            command.setFingerprint(fingerprint);
//            return command.execute();
//        } catch (RuntimeException ex) {
//            throw ex;
//        } catch (Exception ex) {
//            throw new ServiceException(null, ex);
//        }
//    }
//
//    public <T> T execute(ServiceCommand<T> command, String fingerprint) {
//        try {
//            return executeInternally(command, fingerprint);
//        } finally {
//            puHandlerFactory.get(fingerprint).closeManager();
//        }
//    }
//
//    public <T> T executeInTransaction(ServiceCommand<T> command, String fingerprint) {
//        EntityTransaction tx = puHandlerFactory.get(fingerprint).getManager().getTransaction();
//        try {
//            tx.begin();
//            T result = executeInternally(command, fingerprint);
//            tx.commit();
//            return result;
//        } catch (RollbackException ex) {
//            throw ex;
//        } catch (RuntimeException ex) {
//            tx.rollback();
//            throw ex;
//        } catch (Exception ex) {
//            tx.rollback();
//            throw new ServiceException("Transaktion misslyckades pga: " + ex.getLocalizedMessage(), ex);
//        } finally {
//            puHandlerFactory.get(fingerprint).closeManager();
//        }
//    }
//
//    public <T> T executeInSynchronizedTransaction(ServiceCommand<T> command, String fingerprint) {
//        Object synchronizationObject = command.getSynchronizationObject() != null ? command.getSynchronizationObject() : fingerprint;
//        synchronized (LOCK_HANDLER.getLockObject(synchronizationObject)) {
//            try {
//                return executeInTransaction(command, fingerprint);
//            } finally {
//                puHandlerFactory.get(fingerprint).closeManager();
//            }
//        }
//    }
}
