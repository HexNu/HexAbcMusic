package hex.music.service.command.tune;

import hex.music.fw.domain.SearchResultListWrapper;
import hex.music.fw.search.TitleSearch;
import hex.music.service.command.AbstractServiceCommand;

/**
 *
 * @author hln
 */
public class SearchTunesOnFolkWikiCommand extends AbstractServiceCommand<SearchResultListWrapper> {

    private final int limit;
    private final int offset;
    private final String searchString;

    public SearchTunesOnFolkWikiCommand(int limit, int offset, String searchString) {
        this.limit = limit;
        this.offset = offset;
        this.searchString = searchString;
    }

    @Override
    public SearchResultListWrapper execute() {
        return new TitleSearch(searchString).getResult(limit, offset);
    }
}
