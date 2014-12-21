package hex.music.service.command.tune;

import hex.music.fw.domain.SearchResult;
import hex.music.fw.search.TitleSearch;
import hex.music.service.command.AbstractServiceCommand;
import java.util.List;

/**
 *
 * @author hln
 */
public class SearchTunesOnFolkWikiCommand extends AbstractServiceCommand<List<SearchResult>> {

    private final String searchString;

    public SearchTunesOnFolkWikiCommand(String searchString) {
        this.searchString = searchString;
    }

    @Override
    public List<SearchResult> execute() {
        return new TitleSearch(searchString).execute();
    }
}
