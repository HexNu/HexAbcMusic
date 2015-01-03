package hex.music.service.command.tune;

import hex.music.core.domain.Tune;
import hex.music.io.PdfDocumentProducer;
import hex.music.service.command.AbstractServiceCommand;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hln
 */
public class GetPdfDocCommand extends AbstractServiceCommand<InputStream> {

    private final List<Tune> tunes = new ArrayList<>();

    public GetPdfDocCommand(Tune tune) {
        tunes.add(tune);
    }

    public GetPdfDocCommand(List<Tune> tunes) {
        this.tunes.addAll(tunes);
    }

    @Override
    public InputStream execute() {
        try {
            return new PdfDocumentProducer(tunes).produce();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(GetPdfDocCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
