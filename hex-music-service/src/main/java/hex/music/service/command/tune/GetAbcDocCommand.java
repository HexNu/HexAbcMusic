package hex.music.service.command.tune;

import hex.music.core.domain.Tune;
import hex.music.service.io.produce.AbcProducer;
import hex.music.service.command.AbstractServiceCommand;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class GetAbcDocCommand extends AbstractServiceCommand<String> {

    private final List<Tune> tunes = new ArrayList<>();

    public GetAbcDocCommand(Tune tune) {
        tunes.add(tune);
    }

    public GetAbcDocCommand(List<Tune> tunes) {
        this.tunes.addAll(tunes);
    }

    @Override
    public String execute() {
        StringBuilder result = new StringBuilder();
        for (Tune tune : tunes) {
            result.append(new AbcProducer(tune).produce());
        }
        return result.toString();
    }

}
