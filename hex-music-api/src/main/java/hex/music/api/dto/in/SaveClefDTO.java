package hex.music.api.dto.in;

import hex.music.core.domain.Clef;
import hex.music.core.domain.impl.AbcClef;

/**
 *
 * @author hln
 */
public class SaveClefDTO {

    private Long clefId;
    private String clef;
    private Integer transpose;
    private String middle;

    public SaveClefDTO() {
    }

    SaveClefDTO(String clefId, String clef, String transpose, String middle) {
        this.clefId = clefId == null || clefId.equals("") ? null : Long.valueOf(clefId);
        this.clef = clef;
        this.transpose = transpose == null || transpose.equals("") ? null : Integer.valueOf(transpose);
        this.middle = middle;
    }

    public Long getClefId() {
        return clefId;
    }

    public void setClefId(Long clefId) {
        this.clefId = clefId;
    }

    public String getClef() {
        return clef;
    }

    public void setClef(String clef) {
        this.clef = clef;
    }

    public Integer getTranspose() {
        return transpose;
    }

    public void setTranspose(Integer transpose) {
        this.transpose = transpose;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public Clef getDomainObject() {
        Clef result = new AbcClef();
        result.setId(clefId);
        result.setType(Clef.Type.getByString(clef));
        result.setTranspose(transpose);
        result.setMiddle(middle);
        return result;
    }
}
