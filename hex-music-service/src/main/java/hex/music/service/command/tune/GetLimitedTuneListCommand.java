package hex.music.service.command.tune;

import hex.music.core.domain.impl.ResultListWrapper;
import hex.music.service.command.AbstractServiceCommand;

/**
 *
 * @author hln
 */
public class GetLimitedTuneListCommand extends AbstractServiceCommand<ResultListWrapper> {

    private final int limit;
    private final int offset;

    public GetLimitedTuneListCommand(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public ResultListWrapper execute() {
        ResultListWrapper tunes = getDaoFactory().getTuneDao().getLimitedTuneList(limit, offset);
        return tunes;
    }
}
