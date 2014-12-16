package hex.music.service.command.tune;

import hex.music.core.domain.Tune;
import hex.music.service.command.AbstractServiceCommand;
import java.util.List;

/**
 *
 * @author hln
 */
public class GetAllTunesCommand extends AbstractServiceCommand<List<Tune>> {

    @Override
    public List<Tune> execute() {
        return getDaoFactory().getTuneDao().findAll();
    }

}
