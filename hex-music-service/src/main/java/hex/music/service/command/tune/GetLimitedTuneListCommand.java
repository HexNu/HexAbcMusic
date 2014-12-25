package hex.music.service.command.tune;

import hex.music.core.domain.TuneListWrapper;
import hex.music.service.command.AbstractServiceCommand;

/**
 *
 * @author hln
 */
public class GetLimitedTuneListCommand extends AbstractServiceCommand<TuneListWrapper> {

    private final int limit;
    private final int offset;

    public GetLimitedTuneListCommand(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public TuneListWrapper execute() {
        TuneListWrapper tunes = getDaoFactory().getTuneDao().getLimitedTuneList(limit, offset);
        return tunes;
    }
}
