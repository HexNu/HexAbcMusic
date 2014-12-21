package hex.music.service.command.tune;

import hex.music.core.domain.Clef;
import hex.music.service.command.AbstractServiceCommand;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author hln
 */
public class GetAvailableClefsCommand extends AbstractServiceCommand<List<Clef.Type>> {

    @Override
    public List<Clef.Type> execute() {
        return Arrays.asList(Clef.Type.values());
    }
}
