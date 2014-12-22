package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import hex.music.api.dto.LinkDTO;
import hex.music.api.dto.LinkDTOBuilder;
import hex.music.core.domain.TuneListWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class LimitedTuneListDTO extends AbstractDTO {

    private final List<TuneListItemDTO> tunes;

    public LimitedTuneListDTO(TuneListWrapper listWrapper, LinkDTOBuilder linkBuilder) {
        if (listWrapper.getPrevious() != null) {
            addLink(linkBuilder.createPreviousLink(listWrapper.getPrevious(), listWrapper.getLimit(), listWrapper.getQuery()));
        }
        if (listWrapper.getNext() != null) {
            addLink(linkBuilder.createNextLink(listWrapper.getNext(), listWrapper.getLimit(), listWrapper.getQuery()));
        }
        tunes = new ArrayList<>();
        listWrapper.getTunes().stream().forEach((t) -> {
            tunes.add(new TuneListItemDTO(t, linkBuilder));
        });
    }

    public List<TuneListItemDTO> getTunes() {
        return tunes;
    }
}
