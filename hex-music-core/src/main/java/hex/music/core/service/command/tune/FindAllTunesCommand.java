package hex.music.core.service.command.tune;

import hex.music.core.domain.Tune;
import hex.music.core.service.command.AbstractServiceCommand;
import java.util.List;

/**
 *
 * @author hln
 */
public class FindAllTunesCommand extends AbstractServiceCommand<List<Tune>> {

    @Override
    public List<Tune> execute() {
        return getDaoFactory().getTuneDao().findAll();
    }

}
