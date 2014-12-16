package hex.music.service.provider;

import hex.music.service.AbcMusicService;
import hex.music.service.ServiceProvider;
import hex.music.service.impl.ServiceProviderImpl;

/**
 * Created 2014-mar-31
 *
 * @author jep
 */
public class ServiceProviderDelegate {

    private final ServiceProvider serviceProvider;
    private final String fingerprint;

    public ServiceProviderDelegate(String fingerprint) {
        serviceProvider = new ServiceProviderImpl();
        this.fingerprint = fingerprint;
    }

    public AbcMusicService getAbcMusicService() {
        return serviceProvider.getAbcMusicService(fingerprint);
    }
}
