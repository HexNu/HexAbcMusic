package hex.music.api.dto.out;

import hex.music.core.domain.Clef;

/**
 *
 * @author hln
 */
public class ClefTypeListItemDTO {

    private final String name;

    public ClefTypeListItemDTO(Clef.Type clef) {
        this.name = clef.getLabel();
    }

    public String getName() {
        return name;
    }
}
