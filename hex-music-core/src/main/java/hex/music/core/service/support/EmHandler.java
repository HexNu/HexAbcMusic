package hex.music.core.service.support;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 * Created 2014-12-10
 *
 * @author hl
 */
public interface EmHandler {

    public static enum Type {

        HEX, MUSIC, OPERATIVE;

        String getPersistenceUnitPrototypeName() {
            String puPrototypeName = "hex" + (this != OPERATIVE ? "-" + name().toLowerCase() : "") + "PU";
            Logger.getLogger(EmHandler.class.getName()).log(Level.INFO, "PersistenceUnit Prototype Name \"{0}\" created.", puPrototypeName);
            return puPrototypeName;
        }
    }

    EntityManager getManager();

    void closeManager();

    void close();
}
