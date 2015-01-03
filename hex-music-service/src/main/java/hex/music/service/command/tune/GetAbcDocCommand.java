package hex.music.service.command.tune;

import hex.music.core.AbcConstants;
import hex.music.core.domain.Tune;
import hex.music.io.AbcDocumentProducer;
import hex.music.service.command.AbstractServiceCommand;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hln
 */
public class GetAbcDocCommand extends AbstractServiceCommand<InputStream> {

    private final List<Tune> tunes = new ArrayList<>();

    public GetAbcDocCommand(Tune tune) {
        tunes.add(tune);
    }

    public GetAbcDocCommand(List<Tune> tunes) {
        this.tunes.addAll(tunes);
    }

    @Override
    public InputStream execute() {
        StringBuilder result = new StringBuilder();
        tunes.stream().forEach((tune) -> {
            result.append(new AbcDocumentProducer(tune).produce());
        });
        try {
            return new ByteArrayInputStream(result.toString().getBytes(AbcConstants.ABC_ENCODING));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GetAbcDocCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
