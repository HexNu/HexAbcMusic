package hex.music.api.dto.in;

import hex.music.core.domain.Key;
import hex.music.core.domain.impl.AbcKey;

/**
 *
 * @author hln
 */
public class SaveKeyDTO {

    private Long keyId;
    private String key;
    private SaveClefDTO clef;

    public SaveKeyDTO() {
    }

    SaveKeyDTO(String keyId, String key, String clefId, String clef, String transpose, String middle) {
        this.keyId = keyId == null || keyId.equals("") ? null : Long.valueOf(keyId);
        this.key = key;
        this.clef = new SaveClefDTO(clefId, clef, transpose, middle);

    }

    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SaveClefDTO getClef() {
        return clef;
    }

    public void setClef(SaveClefDTO clef) {
        this.clef = clef;
    }

    public Key getDomainObject() {
        Key result = new AbcKey();
        result.setId(keyId);
        result.setSignature(Key.Signature.getByString(key));
        result.setClef(clef.getDomainObject());
        return result;
    }
}
