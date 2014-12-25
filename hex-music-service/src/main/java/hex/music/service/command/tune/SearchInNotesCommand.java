package hex.music.service.command.tune;

import hex.music.core.domain.impl.ResultListWrapper;
import hex.music.service.command.AbstractServiceCommand;

/**
 *
 * @author hln
 */
public class SearchInNotesCommand extends AbstractServiceCommand<ResultListWrapper> {

    private final Integer limit;
    private final Integer offset;
    private final String query;

    public SearchInNotesCommand(Integer limit, Integer offset, String query) {
        this.limit = limit;
        this.offset = offset;
        this.query = query;
    }

    @Override
    public ResultListWrapper execute() {
        return getDaoFactory().getTuneDao().getNoteSearchResult(limit, offset, query);
    }
}
