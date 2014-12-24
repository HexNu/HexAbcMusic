package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import hex.music.core.domain.Voice;

/**
 *
 * @author hln
 */
public class VoiceDTO extends AbstractDTO {

    private final String voiceId;
    private final String voiceCode;
    private final String name;
    private final String subname;
    private final String voiceIndex;
    private final String clefId;
    private final String clef;
    private final String transpose;
    private final String middle;
    private final String body;

    public VoiceDTO(Voice voice) {
        this.voiceId = String.valueOf(voice.getId());
        this.voiceCode = voice.getVoiceCode();
        this.name = voice.getName();
        this.subname = voice.getSubname();
        this.voiceIndex = String.valueOf(voice.getVoiceIndex());
        this.clefId = String.valueOf(voice.getClef().getId());
        this.clef = voice.getClef().getType().getLabel();
        this.transpose = String.valueOf(voice.getClef().getTranspose());
        this.middle = voice.getClef().getMiddle();
        this.body = voice.getBody();
    }

    public String getVoiceId() {
        return voiceId;
    }

    public String getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public String getSubname() {
        return subname;
    }

    public String getVoiceCode() {
        return voiceCode;
    }

    public String getVoiceIndex() {
        return voiceIndex;
    }

    public String getClefId() {
        return clefId;
    }

    public String getClef() {
        return clef;
    }

    public String getTranspose() {
        return transpose;
    }

    public String getMiddle() {
        return middle;
    }
}
