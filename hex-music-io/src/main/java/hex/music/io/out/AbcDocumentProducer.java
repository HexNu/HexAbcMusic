package hex.music.io.out;

import hex.music.core.domain.Tune;
import java.util.List;

/**
 *
 * @author hln
 */
public class AbcDocumentProducer extends AbstractProducer<String> {

    public AbcDocumentProducer(Tune tune) {
        super(tune);
    }

    public AbcDocumentProducer(List<Tune> tunes) {
        super(tunes);
    }

    @Override
    public String produce() {
        return getAbcDocumentAsString();
    }
}
