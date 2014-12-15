package hex.music.core.producer;

import hex.music.core.AbcConstants;
import hex.music.core.domain.Tune;
import hex.music.core.domain.Voice;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hln
 */
public class AbcProducer {

    private final Tune data;

    public AbcProducer(Tune data) {
        this.data = data;
    }

    public String produce() {
        String result = createResult();
        try {
            changeEncoding(result);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AbcProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private String changeEncoding(String result) throws UnsupportedEncodingException {
        return new String(result.getBytes(AbcConstants.ABC_ENCODING), AbcConstants.ABC_ENCODING);
    }

    private String createResult() {
        StringBuilder result = new StringBuilder();
        result.append("X:").append(data.getId()).append("\n");
        createTuneHeader(result);
        if (data.getRythm() != null) {
            result.append("R:").append(data.getRythm()).append("\n");
        }
        if (data.getRegion() != null) {
            result.append("O:").append(data.getRegion()).append("\n");
        }
        if (data.getSource() != null) {
            result.append("S:").append(data.getSource()).append("\n");
        }
        if (data.getHistory() != null) {
            result.append("H:").append(data.getHistory()).append("\n");
        }
        if (data.getNotes() != null) {
            result.append("N:").append(data.getNotes()).append("\n");
        }
        if (data.getTranscriber() != null) {
            result.append("Z:").append(data.getTranscriber()).append("\n");
        }
        if (data.getMeter() != null) {
            result.append("M:").append(data.getMeter()).append("\n");
        }
        if (data.getUnitNoteLength() != null) {
            result.append("L:").append(data.getUnitNoteLength()).append("\n");
        }
        if (data.getKey() != null) {
            result.append("K:").append(data.getKey().getType().getCode()).append("\n");
        }
        data.getVoices().stream().map((voice) -> {
            result.append("V:").append(voice.getCode() != null ? voice.getCode() : Voice.DEFAULT_CODE);
            if (data.getVoices().size() > 1) {
                result.append(" name=\"").append(voice.getName()).append("\"")
                        .append(" subname=\"").append(voice.getSubname()).append("\"");
            }
            result.append(" clef=").append(voice.getClef().getType().getCode());
            return voice;
        }).map((voice) -> {
            if (voice.getClef().getTranspose() != 0) {
                result.append(" transpose=").append(voice.getClef().getTranspose());
            }
            return voice;
        }).forEach((voice) -> {
            result.append("\n").append(voice.getBody()).append("\n");
        });
        result.append("\n\n");
        return result.toString();
    }

    private void createTuneHeader(StringBuilder result) {
        result.append("T:").append(data.getTitle()).append("\n");
        if (data.getSubheader() != null) {
            result.append("T:").append(data.getSubheader()).append("\n");
        }
        if (data.getComposer() != null) {
            result.append("C:").append(data.getComposer()).append("\n");
        }
        if (data.getOriginator() != null) {
            result.append("C:efter ").append(data.getOriginator()).append("\n");
        }
        if (data.getTempo() != null) {
            result.append("Q:").append(data.getTempo()).append("\n");
        }
    }
}
