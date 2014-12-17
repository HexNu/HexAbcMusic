package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import hex.music.core.domain.Key;

/**
 *
 * @author hln
 */
public class KeyDTO extends AbstractDTO {

    private final String id;
    private final String signature;
    private final ClefDTO clef;

    public KeyDTO(Key key) {
        this.id = String.valueOf(key.getId());
        this.clef = new ClefDTO(key.getClef());
        this.signature = key.getSignature().getCode();
    }

    public String getId() {
        return id;
    }

    public String getSignature() {
        return signature;
    }

    public ClefDTO getClef() {
        return clef;
    }
}
