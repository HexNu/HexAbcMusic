package hex.music.api.dto.out;

import hex.music.core.domain.Key;

/**
 *
 * @author hln
 */
public class KeySignatureListItemDTO {

    private final String description;
    private final String name;

    public KeySignatureListItemDTO(Key.Signature signature) {
        this.description = signature.getLabel();
        this.name = signature.getCode();
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
