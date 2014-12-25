package hex.music.api.dto.in;

import hex.music.core.domain.Clef;
import hex.music.core.domain.Voice;
import hex.music.core.domain.impl.AbcClef;
import hex.music.core.domain.impl.AbcVoice;

/**
 *
 * @author hln
 */
public class UpdateVoiceDTO {

    private Long voiceId;
    private String voiceCode;
    private String name;
    private String subname;
    private Long clefId;
    private String clef;
    private Integer transpose;
    private String middle;
    private Integer voiceIndex;
    private String body;

    public UpdateVoiceDTO(String voiceId, String voiceCode, String name, String subname, String clefId, String clef,
            String transpose, String middle, String voiceIndex, String body) {
        this.voiceId = voiceId == null || voiceId.equals("") ? null : Long.valueOf(voiceId);
        this.voiceCode = voiceCode;
        this.name = name;
        this.subname = subname;
        this.clefId = clef == null || voiceId.equals("") ? null : Long.valueOf(voiceId);
        this.clef = clef;
        this.middle = middle;
        this.voiceIndex = voiceIndex == null || voiceIndex.equals("") ? null : Integer.valueOf(voiceIndex);
        this.voiceIndex = voiceIndex == null || voiceIndex.equals("") ? null : Integer.valueOf(voiceIndex);
        this.body = body;
    }

    public Long getVoiceId() {
        return voiceId;
    }

    public String getVoiceCode() {
        return voiceCode;
    }

    public String getName() {
        return name;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getClef() {
        return clef;
    }

    public void setClef(String clef) {
        this.clef = clef;
    }

    public Long getClefId() {
        return clefId;
    }

    public void setClefId(String clefId) {
        this.clefId = clefId == null || clefId.equals("") ? null : Long.valueOf(clefId);
    }

    public Integer getTranspose() {
        return transpose;
    }

    public void setTranspose(String transpose) {
        this.transpose = transpose == null || transpose.equals("") ? null : Integer.valueOf(transpose);
    }

    public Integer getVoiceIndex() {
        return voiceIndex;
    }

    public void setVoiceIndex(String voiceIndex) {
        this.voiceIndex = voiceIndex == null || voiceIndex.equals("") ? null : Integer.valueOf(voiceIndex);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setVoiceId(String voiceId) {
        this.voiceId = voiceId == null || voiceId.equals("") ? null : Long.valueOf(voiceId);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVoiceCode(String voiceCode) {
        this.voiceCode = voiceCode;
    }

    public Voice getDomainObject() {
        Voice result = new AbcVoice();
        result.setId(voiceId);
        result.setVoiceCode(voiceCode);
        result.setName(name);
        result.setSubname(subname);
        result.setVoiceIndex(voiceIndex);
        result.setBody(body);
        Clef voiceClef = new AbcClef();
        voiceClef.setId(clefId);
        voiceClef.setType(Clef.Type.getByString(clef));
        voiceClef.setTranspose(transpose);
        voiceClef.setMiddle(middle);
        result.setClef(voiceClef);
        return result;
    }
}
