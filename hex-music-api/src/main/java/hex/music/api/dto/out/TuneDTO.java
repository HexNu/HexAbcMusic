package hex.music.api.dto.out;

import hex.music.api.dto.AbstractDTO;
import hex.music.core.domain.Tune;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class TuneDTO extends AbstractDTO {

    private final String id;
    private final String title;
    private final String subheader;
    private final String composer;
    private final String originator;
    private final String rythm;
    private final String region;
    private final String history;
    private final String notes;
    private final String transcriber;
    private final String source;
    private final String meter;
    private final String unitNoteLength;
    private final KeyDTO key;
    private final List<VoiceDTO> voices = new ArrayList<>();

    public TuneDTO(Tune tune) {
        this.id = String.valueOf(tune.getId());
        this.title = tune.getTitle();
        this.subheader = tune.getSubheader();
        this.composer = tune.getComposer();
        this.originator = tune.getOriginator();
        this.rythm = tune.getRythm();
        this.region = tune.getRegion();
        this.history = tune.getHistory();
        this.notes = tune.getNotes();
        this.transcriber = tune.getTranscriber();
        this.source = tune.getSource();
        this.meter = tune.getMeter();
        this.unitNoteLength = tune.getUnitNoteLength();
        this.key = new KeyDTO(tune.getKey());
        tune.getVoices().stream().forEach((voice) -> {
            voices.add(new VoiceDTO(voice));
        });
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubheader() {
        return subheader;
    }

    public String getComposer() {
        return composer;
    }

    public String getOriginator() {
        return originator;
    }

    public String getRythm() {
        return rythm;
    }

    public String getRegion() {
        return region;
    }

    public String getHistory() {
        return history;
    }

    public String getNotes() {
        return notes;
    }

    public String getTranscriber() {
        return transcriber;
    }

    public String getSource() {
        return source;
    }

    public String getMeter() {
        return meter;
    }

    public String getUnitNoteLength() {
        return unitNoteLength;
    }

    public KeyDTO getKey() {
        return key;
    }

    public List<VoiceDTO> getVoices() {
        return voices;
    }
}
