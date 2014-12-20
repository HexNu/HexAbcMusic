package hex.music.service.command.tune;

import hex.music.service.command.AbstractServiceCommand;
import java.util.List;

/**
 *
 * @author hln
 */
public class GetExistingRegionsCommand extends AbstractServiceCommand<List<String>> {

    @Override
    public List<String> execute() {
        return getDaoFactory().getTuneDao().getRegions();
    }
}
