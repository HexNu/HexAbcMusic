package hex.music.io;

import hex.music.core.domain.Tune;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hln
 */
public class PsStreamProducer extends AbstractProducer<InputStream> {

    public PsStreamProducer(List<Tune> tunes) {
        super(tunes);
    }

    @Override
    public InputStream produce() {
        try {
            return new FileInputStream(getPsAsFile());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PsStreamProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
