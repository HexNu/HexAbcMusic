package hex.music.core.service.command;

import hex.music.core.service.support.PuHandlerFactory;


/**
 * Created 2014-12-10
 *
 * @author hl
 * @param <T>
 */
public interface ServiceCommand<T> {

    T execute();

    void setFingerprint(String fingerprint);

    void setPuHandlerFactory(PuHandlerFactory puHandlerFactory);

    Object getSynchronizationObject();
}
