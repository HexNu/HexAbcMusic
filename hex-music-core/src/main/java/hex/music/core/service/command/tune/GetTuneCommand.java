package hex.music.core.service.command.tune;

import hex.music.core.domain.Tune;
import hex.music.core.service.command.AbstractServiceCommand;

/**
 *
 * @author hln
 */
public class GetTuneCommand extends AbstractServiceCommand<Tune>  {
    private final Long id;

    public GetTuneCommand(Long id) {
        this.id = id;
    }

    @Override
    public Tune execute() {
        return getDaoFactory().getTuneDao().findByPrimaryKey(id);
    }

}
