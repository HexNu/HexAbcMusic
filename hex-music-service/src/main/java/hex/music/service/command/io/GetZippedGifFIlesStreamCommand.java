package hex.music.service.command.io;

import hex.music.core.domain.Tune;
import hex.music.io.out.ZippedGifFilesStreamProducer;
import hex.music.service.command.AbstractServiceCommand;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author hln
 */
public class GetZippedGifFIlesStreamCommand extends AbstractServiceCommand<InputStream> {

    private final List<Tune> tunes;

    public GetZippedGifFIlesStreamCommand(List<Tune> tunes) {
        this.tunes = tunes;
    }

    @Override
    public InputStream execute() {
        return new ZippedGifFilesStreamProducer(tunes).produce();
    }
}
