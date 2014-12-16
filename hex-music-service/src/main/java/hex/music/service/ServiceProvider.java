package hex.music.service;

/**
 *
 * @author hln
 */
public interface ServiceProvider {

    AbcMusicService getAbcMusicService(String fingerprint);
}
