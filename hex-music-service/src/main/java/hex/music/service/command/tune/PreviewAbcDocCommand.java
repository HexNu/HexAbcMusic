package hex.music.service.command.tune;

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
        StringBuilder result = new StringBuilder();
        tunes.stream().forEach((tune) -> {
            result.append(new AbcDocumentProducer(tune).produce());
        });
        return result.toString();
    }

}
