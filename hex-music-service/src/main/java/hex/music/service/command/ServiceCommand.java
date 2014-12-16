package hex.music.service.command;

/**
 * Created 2014-12-10
 *
 * @author hl
 * @param <T>
 */
public interface ServiceCommand<T> {

    T execute();

    void setFingerprint(String fingerprint);

    Object getSynchronizationObject();
}
