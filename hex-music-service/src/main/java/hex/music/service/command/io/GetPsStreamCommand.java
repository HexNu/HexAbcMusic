package hex.music.service.command.io;

import hex.music.core.domain.Tune;
import hex.music.io.out.PsStreamProducer;
import hex.music.service.command.AbstractServiceCommand;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class GetPsStreamCommand extends AbstractServiceCommand<InputStream> {
    private final List<Tune> tunes = new ArrayList<>();

    public GetPsStreamCommand(List<Tune> tunes) {
        this.tunes.addAll(tunes);
    }

    
    @Override
    public InputStream execute() {
        return new PsStreamProducer(tunes).produce();
    }
}
