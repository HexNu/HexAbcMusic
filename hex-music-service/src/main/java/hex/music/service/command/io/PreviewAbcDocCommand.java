package hex.music.service.command.io;

import hex.music.core.domain.Tune;
import hex.music.io.AbcDocumentProducer;
import hex.music.service.command.AbstractServiceCommand;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class PreviewAbcDocCommand extends AbstractServiceCommand<String> {

    private final List<Tune> tunes = new ArrayList<>();

    public PreviewAbcDocCommand(Tune tune) {
        tunes.add(tune);
    }

    public PreviewAbcDocCommand(List<Tune> tunes) {
        tunes.addAll(tunes);
    }

    @Override
    public String execute() {
        return new AbcDocumentProducer(tunes).produce();
    }
}
