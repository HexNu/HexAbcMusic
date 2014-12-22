package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import hex.music.core.domain.Voice;

/**
 *
 * @author hln
 */
public class VoiceDTO extends AbstractDTO {

    private final String id;
    private final String body;
    private final String name;
    private final String subname;
    private final String voiceId;
    private final String voiceIndex;
    private final String clef;
    private final String transpose;
    private final String middle;

    public VoiceDTO(Voice voice) {
        this.id = String.valueOf(voice.getId());
        this.name = voice.getName();
        this.subname = voice.getSubname();
        this.voiceId = voice.getVoiceId();
        this.voiceIndex = String.valueOf(voice.getVoiceIndex());
        this.body = voice.getBody();
        this.clef = voice.getClef().getType().getLabel();
        this.transpose = String.valueOf(voice.getClef().getTranspose());
        this.middle = voice.getClef().getMiddle();
    }

    public String getId() {
        return id;
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

    public String getVoiceId() {
        return voiceId;
    }

    public String getVoiceIndex() {
        return voiceIndex;
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
