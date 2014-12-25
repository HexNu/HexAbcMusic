package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class FwTuneListDTO extends AbstractDTO {

    private final List<FwTuneListItemDTO> tunes = new ArrayList<>();

    public List<FwTuneListItemDTO> getTunes() {
        return tunes;
    }
    
    public void addFwSearchListItem(FwTuneListItemDTO item) {
        tunes.add(item);
    }
}
