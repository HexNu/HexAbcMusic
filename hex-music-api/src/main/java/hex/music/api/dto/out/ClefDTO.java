package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import hex.music.core.domain.Clef;

/**
 *
 * @author hln
 */
public class ClefDTO extends AbstractDTO {

    private final String clefId;
    private final String middle;
    private final String transpose;
    private final String type;

    public ClefDTO(Clef clef) {
        this.clefId = String.valueOf(clef.getId());
        this.middle = clef.getMiddle();
        this.transpose = String.valueOf(clef.getTranspose());
        this.type = clef.getType().getLabel();
    }

    public String getId() {
        return clefId;
    }

    public String getMiddle() {
        return middle;
    }

    public String getTranspose() {
        return transpose;
    }

    public String getType() {
        return type;
    }
}
