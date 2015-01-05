package hex.music.service.command.io;

import hex.music.core.domain.Tune;
import hex.music.io.FirstLineAsGifStreamProducer;
import hex.music.service.command.AbstractServiceCommand;
import java.io.InputStream;

/**
 *
 * @author hln
 */
public class GetFirstLineGifStreamCommand extends AbstractServiceCommand<InputStream> {
    private final Tune tune;

    public GetFirstLineGifStreamCommand(Tune tune) {
        this.tune = tune;
    }

    
    @Override
    public InputStream execute() {
        return new FirstLineAsGifStreamProducer(tune).produce();
    }
}
