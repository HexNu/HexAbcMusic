package hex.music.service.command.io;

import hex.music.core.domain.Tune;
import hex.music.io.out.AbcStreamProducer;
import hex.music.service.command.AbstractServiceCommand;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class GetAbcStreamCommand extends AbstractServiceCommand<InputStream> {

    private final List<Tune> tunes = new ArrayList<>();

    public GetAbcStreamCommand(List<Tune> tunes) {
        this.tunes.addAll(tunes);
    }

    @Override
    public InputStream execute() {
        return new AbcStreamProducer(tunes).produce();
    }
}
