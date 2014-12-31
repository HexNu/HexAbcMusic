package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import hex.music.api.dto.LinkDTOBuilder;
import hex.music.fw.domain.SearchResultListWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class FwTuneListDTO extends AbstractDTO {

    private final List<FwTuneListItemDTO> tunes;

    public FwTuneListDTO(SearchResultListWrapper listWrapper, LinkDTOBuilder linkBuilder) {
        if (listWrapper.getPrevious() != null) {
            addLink(linkBuilder.createPreviousFwLink(listWrapper.getPrevious(), listWrapper.getLimit(), listWrapper.getQuery()));
        }
        if (listWrapper.getNext() != null) {
            addLink(linkBuilder.createNextFwLink(listWrapper.getNext(), listWrapper.getLimit(), listWrapper.getQuery()));
        }
        tunes = new ArrayList<>();
        listWrapper.getResults().stream().forEach((t) -> {
            tunes.add(new FwTuneListItemDTO(t, linkBuilder));
        });

    }

    public List<FwTuneListItemDTO> getTunes() {
        return tunes;
    }
}
