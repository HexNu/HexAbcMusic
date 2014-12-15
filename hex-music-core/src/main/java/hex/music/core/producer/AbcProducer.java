package hex.music.core.producer;

import hex.music.core.AbcConstants;
import hex.music.core.AbcConstants.Field;
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
        createTuneMetaData(result);
        createTuneBody(result);
        result.append("\n\n");
        return result.toString();
    }

    private void createTuneHeader(StringBuilder result) {
        createKeyValueRow(result, Field.T, data.getTitle());
        createKeyValueRow(result, Field.T, data.getSubheader());
        createKeyValueRow(result, Field.C, data.getComposer());
        if (data.getOriginator() != null) {
            createKeyValueRow(result, Field.C, "efter " + data.getOriginator());
        }
        createKeyValueRow(result, Field.Q, data.getTempo());
    }

    private void createKeyValueRow(StringBuilder result, Field key, String value) {
        if (value != null) {
            result.append(key.name()).append(":").append(value).append("\n");
        }
    }

    private void createTuneMetaData(StringBuilder result) {
        createKeyValueRow(result, Field.R, data.getRythm());
        createKeyValueRow(result, Field.O, data.getRegion());
        createKeyValueRow(result, Field.S, data.getSource());
        createKeyValueRow(result, Field.H, data.getHistory());
        createKeyValueRow(result, Field.N, data.getNotes());
        createKeyValueRow(result, Field.Z, data.getTranscriber());
        createKeyValueRow(result, Field.M, data.getMeter());
        createKeyValueRow(result, Field.L, data.getUnitNoteLength());
        createKeyValueRow(result, Field.K, data.getKey().getSignature().getCode());
    }

    private void createTuneBody(StringBuilder result) {
        data.getVoices().stream().map((voice) -> {
            result.append("V:").append(voice.getVoiceId() != null ? voice.getVoiceId() : Voice.DEFAULT_VOICE_ID);
            if (data.getVoices().size() > 1) {
                if (voice.getName() != null) {
                    result.append(" name=\"").append(voice.getName()).append("\"");
                }
                if (voice.getSubname() != null) {
                    result.append(" subname=\"").append(voice.getSubname()).append("\"");
                }
            }
            result.append(" clef=").append(voice.getClef().getType().getCode());
            return voice;
        }).map((voice) -> {
            if (voice.getClef().getTranspose() != 0) {
                result.append(" transpose=").append(voice.getClef().getTranspose());
            }
            return voice;
        }).forEach((voice) -> {
            result.append("\n").append(voice.getBody());
        });
    }
}
