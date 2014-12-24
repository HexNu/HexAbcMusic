package hex.music.api.dto.in;

import hex.music.core.domain.Tune;
import hex.music.core.domain.Voice;
import hex.music.core.domain.impl.AbcTune;

/**
 *
 * @author hln
 */
public class SaveTuneDTO {

    private Long id;
    private String title;
    private String subheader;
    private String composer;
    private String source;
    private String rythm;
    private String region;
    private String history;
    private String notes;
    private String transcriber;
    private String bibliography;
    private String discography;
    private String uri;
    private String meter;
    private String unitNoteLength;
    private String tempo;
    private SaveKeyDTO key;
    private SaveVoiceDTO[] voices;

    public SaveTuneDTO() {
    }

    public SaveTuneDTO(String tuneId, String title, String subheader, String composer, String source, String rythm, String region, String history,
            String notes, String transcriber, String bibliography, String discography, String uri, String meter, String unitNoteLength,
            String tempo, String keyId, String key, String clefId, String clef, String transpose, String middle, SaveVoiceDTO[] voices) {
        this.id = tuneId == null || tuneId.equals("") ? null : Long.valueOf(tuneId);
        this.title = title;
        this.subheader = subheader;
        this.composer = composer;
        this.source = source;
        this.rythm = rythm;
        this.region = region;
        this.history = history;
        this.notes = notes;
        this.transcriber = transcriber;
        this.bibliography = bibliography;
        this.discography = discography;
        this.uri = uri;
        this.meter = meter;
        this.unitNoteLength = unitNoteLength;
        this.tempo = tempo;
        this.key = new SaveKeyDTO(keyId, key, clefId, clef, transpose, middle);
        this.voices = voices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubheader() {
        return subheader;
    }

    public void setSubheader(String subheader) {
        this.subheader = subheader;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRythm() {
        return rythm;
    }

    public void setRythm(String rythm) {
        this.rythm = rythm;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTranscriber() {
        return transcriber;
    }

    public void setTranscriber(String transcriber) {
        this.transcriber = transcriber;
    }

    public String getBibliography() {
        return bibliography;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
    }

    public String getDiscography() {
        return discography;
    }

    public void setDiscography(String discography) {
        this.discography = discography;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }

    public String getUnitNoteLength() {
        return unitNoteLength;
    }

    public void setUnitNoteLength(String unitNoteLength) {
        this.unitNoteLength = unitNoteLength;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public SaveKeyDTO getKey() {
        return key;
    }

    public void setKey(SaveKeyDTO key) {
        this.key = key;
    }

    public SaveVoiceDTO[] getVoices() {
        return voices;
    }

    public void setVoices(SaveVoiceDTO[] voices) {
        this.voices = voices;
    }

    public Tune getDomainObject() {
        Tune result = new AbcTune();
        result.setId(id);
        result.setTitle(title);
        result.setSubheader(subheader);
        result.setComposer(composer);
        result.setSource(source);
        result.setRythm(rythm);
        result.setRegion(region);
        result.setHistory(history);
        result.setNotes(notes);
        result.setTranscriber(transcriber);
        result.setBibliography(bibliography);
        result.setDiscography(discography);
        result.setUri(uri);
        result.setMeter(meter);
        result.setUnitNoteLength(unitNoteLength);
        result.setTempo(tempo);
        result.setKey(key.getDomainObject());
        for (SaveVoiceDTO voiceDto : voices) {
            Voice voice = voiceDto.getDomainObject();
            result.addVoice(voice);
            voice.setTune(result);
        }
        return result;
    }
}
