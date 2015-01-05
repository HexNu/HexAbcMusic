package hex.music.service.command.io;

import hex.music.core.domain.Tune;
import hex.music.io.ZippedMidiFilesStreamProducer;
import hex.music.service.command.AbstractServiceCommand;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class GetZippedMidiFilesStreamCommand extends AbstractServiceCommand<InputStream> {

    private final List<Tune> tunes = new ArrayList<>();

    public GetZippedMidiFilesStreamCommand(List<Tune> tunes) {
        this.tunes.addAll(tunes);
    }

    @Override
    public InputStream execute() {
        return new ZippedMidiFilesStreamProducer(tunes).produce();
    }
}