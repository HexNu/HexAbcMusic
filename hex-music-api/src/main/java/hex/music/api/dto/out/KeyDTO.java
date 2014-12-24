package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import hex.music.core.domain.Key;

/**
 *
 * @author hln
 */
public class KeyDTO extends AbstractDTO {

    private final String keyId;
    private final String signature;
    private final String clefType;
    private final String transpose;
    private final String middle;

    public KeyDTO(Key key) {
        this.keyId = String.valueOf(key.getId());
        this.signature = key.getSignature().getCode();
        this.clefType = key.getClef().getType().getLabel();
        this.transpose = String.valueOf(key.getClef().getTranspose());
        this.middle = key.getClef().getMiddle();
    }

    public String getId() {
        return keyId;
    }

    public String getSignature() {
        return signature;
    }

    public String getClefType() {
        return clefType;
    }

    public String getTranspose() {
        return transpose;
    }

    public String getMiddle() {
        return middle;
    }
}
