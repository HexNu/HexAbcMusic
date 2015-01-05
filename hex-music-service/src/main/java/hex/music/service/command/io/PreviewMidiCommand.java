package hex.music.service.command.io;

import hex.music.core.domain.Tune;
import hex.music.io.out.MidiStreamProducer;
import hex.music.service.command.AbstractServiceCommand;
import java.io.InputStream;

/**
 *
 * @author hln
 */
public class PreviewMidiCommand extends AbstractServiceCommand<InputStream> {

    private final Tune tune;

    public PreviewMidiCommand(Tune tune) {
        this.tune = tune;
    }

    @Override
    public InputStream execute() {
        return new MidiStreamProducer(tune).produce();
    }
}
