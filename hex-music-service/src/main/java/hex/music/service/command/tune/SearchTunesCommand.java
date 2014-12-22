package hex.music.service.command.tune;

import hex.music.core.domain.impl.ResultListWrapper;
import hex.music.service.command.AbstractServiceCommand;

/**
 *
 * @author hln
 */
public class SearchTunesCommand extends AbstractServiceCommand<ResultListWrapper> {

    private final Integer limit;
    private final Integer offset;
    private final String query;

    public SearchTunesCommand(Integer limit, Integer offset, String query) {
        this.limit = limit;
        this.offset = offset;
        this.query = query;
    }

    @Override
    public ResultListWrapper execute() {
        if (query == null) {
            return getDaoFactory().getTuneDao().getLimitedTuneList(limit, offset);
        } else {
            return getDaoFactory().getTuneDao().getSearchResult(limit, offset, query);
        }
    }

}
