package hex.music.service.impl;

import hex.music.service.AbcMusicService;
import hex.music.service.ServiceProvider;

/**
 *
 * @author hln
 */
public class ServiceProviderImpl implements ServiceProvider {

    @Override
    public AbcMusicService getAbcMusicService(String fingerprint) {
        return new AbcMusicServiceImpl(fingerprint);
    }

}
