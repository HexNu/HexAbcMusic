package hex.music.api.dto.in;

import hex.music.core.domain.Voice;
import hex.music.core.domain.impl.AbcVoice;

/**
 *
 * @author hln
 */
public class SaveVoiceDTO {

    private Long voiceId;
    private String voiceCode;
    private String name;
    private String subname;
    private SaveClefDTO clef;
    private Integer voiceIndex;
    private String body;

    public SaveVoiceDTO(String voiceId, String voiceCode, String name, String subname, String clefId, String clef,
            String transpose, String middle, String voiceIndex, String body) {
        this.voiceId = voiceId == null || voiceId.equals("") ? null : Long.valueOf(voiceId);
        this.voiceCode = voiceCode;
        this.name = name;
        this.subname = subname;
        this.clef = new SaveClefDTO(clefId, clef, transpose, middle);
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

    public SaveClefDTO getClef() {
        return clef;
    }

    public void setClef(SaveClefDTO clef) {
        this.clef = clef;
    }

    public Integer getVoiceIndex() {
        return voiceIndex;
    }

    public void setVoiceIndex(Integer voiceIndex) {
        this.voiceIndex = voiceIndex;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setVoiceId(Long voiceId) {
        this.voiceId = voiceId;
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
        result.setClef(clef.getDomainObject());
        return result;
    }
}
