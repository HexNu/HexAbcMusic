package hex.music.service.support;

/**
 * Created 2014-12-10
 *
 * @author hl
 */
public class PuHandlerFactory {

    public static final String MUSIC_DATABASE = "music_";
    private final EmHandlerCache cache = new EmHandlerCache();

    private EmHandler getMusicHandler(String key) {
        return cache.getHandler(EmHandler.Type.MUSIC, MUSIC_DATABASE + key.toUpperCase());
    }

    public EmHandler get(String key) {
        return getMusicHandler(key);
    }

    public void kill(String key) {
        if (key != null) {
            cache.removeHandler(MUSIC_DATABASE + key);
        }
    }
}
