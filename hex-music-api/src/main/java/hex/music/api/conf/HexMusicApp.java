package hex.music.api.conf;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created 2014-nov-24
 *
 * @author jep
 */
@ApplicationPath("resources")
public class HexMusicApp extends ResourceConfig {

    public HexMusicApp() {
        packages("hex.music.api");
        register(JacksonFeature.class);
        register(MultiPartFeature.class);
    }
}
