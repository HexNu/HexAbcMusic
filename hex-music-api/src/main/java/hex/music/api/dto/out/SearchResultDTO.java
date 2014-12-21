package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import hex.music.api.dto.LinkDTOBuilder;
import hex.music.fw.domain.SearchResult;

/**
 *
 * @author hln
 */
public class SearchResultDTO extends AbstractDTO {

    private final String title;

    public SearchResultDTO(SearchResult searchResult, LinkDTOBuilder linkBuilder) {
        addLink(linkBuilder.createFwTunePageLink(searchResult));
        addLink(linkBuilder.createFwTuneDownloadLink(searchResult));
        this.title = searchResult.getText();
    }

    public String getTitle() {
        return title;
    }

}
