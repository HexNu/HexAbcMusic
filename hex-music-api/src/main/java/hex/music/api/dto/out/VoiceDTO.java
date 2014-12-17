package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import hex.music.core.domain.Voice;

/**
 *
 * @author hln
 */
public class VoiceDTO extends AbstractDTO {

    private final String id;
    private final ClefDTO clef;
    private final String body;
    private final String name;
    private final String subname;
    private final String voiceId;
    private final String voiceIndex;

    public VoiceDTO(Voice voice) {
        this.id = String.valueOf(voice.getId());
        this.name = voice.getName();
        this.subname = voice.getSubname();
        this.voiceId = voice.getVoiceId();
        this.voiceIndex = String.valueOf(voice.getVoiceIndex());
        this.body = voice.getBody();
        this.clef = new ClefDTO(voice.getClef());
    }

    public String getId() {
        return id;
    }

    public ClefDTO getClef() {
        return clef;
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
}
