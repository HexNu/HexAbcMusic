package hex.music.service.command.io;

import hex.music.core.domain.Tune;
import hex.music.io.GifStreamProducer;
import hex.music.service.command.AbstractServiceCommand;
import java.io.InputStream;

/**
 *
 * @author hln
 */
public class PreviewGifCommand extends AbstractServiceCommand<InputStream> {
    private final Tune tune;

    public PreviewGifCommand(Tune tune) {
        this.tune = tune;
    }

    @Override
    public InputStream execute() {
        return new GifStreamProducer(tune).produce();
    }

}
