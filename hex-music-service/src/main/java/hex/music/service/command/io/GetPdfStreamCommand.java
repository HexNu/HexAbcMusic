package hex.music.service.command.io;

import hex.music.core.domain.Tune;
import hex.music.io.out.PdfStreamProducer;
import hex.music.service.command.AbstractServiceCommand;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class GetPdfStreamCommand extends AbstractServiceCommand<InputStream> {

    private final List<Tune> tunes = new ArrayList<>();

    public GetPdfStreamCommand(List<Tune> tunes) {
        this.tunes.addAll(tunes);
    }

    @Override
    public InputStream execute() {
        return new PdfStreamProducer(tunes).produce();
    }
}
