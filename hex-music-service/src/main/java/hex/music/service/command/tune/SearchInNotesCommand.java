package hex.music.service.command.tune;

import hex.music.core.domain.TuneListWrapper;
import hex.music.service.command.AbstractServiceCommand;

/**
 *
 * @author hln
 */
public class SearchInNotesCommand extends AbstractServiceCommand<TuneListWrapper> {

    private final Integer limit;
    private final Integer offset;
    private final String query;

    public SearchInNotesCommand(Integer limit, Integer offset, String query) {
        this.limit = limit;
        this.offset = offset;
        this.query = query;
    }

    @Override
    public TuneListWrapper execute() {
        return getDaoFactory().getTuneDao().getNoteSearchResult(limit, offset, query);
    }
}
