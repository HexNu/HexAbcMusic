package hex.music.io.out;

import hex.music.core.AbcConstants;
import hex.music.core.domain.Tune;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hln
 */
public class AbcStreamProducer extends AbstractProducer<InputStream> {

    public AbcStreamProducer(List<Tune> tunes) {
        super(tunes);
    }

    @Override
    public InputStream produce() {
        try {
            return new ByteArrayInputStream(getAbcDocumentAsString().getBytes(AbcConstants.ABC_ENCODING));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AbcStreamProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
