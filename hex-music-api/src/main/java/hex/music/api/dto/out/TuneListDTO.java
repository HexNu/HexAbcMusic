package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class TuneListDTO extends AbstractDTO {

    private final List<TuneListItemDTO> tunes = new ArrayList<>();
    
    public List<TuneListItemDTO> getTunes() {
        return tunes;
    }
    
    public void addTuneListItem(TuneListItemDTO item) {
        tunes.add(item);
    }
}
