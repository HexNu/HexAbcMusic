package hex.music.core.domain.impl;

import hex.music.core.domain.Key;
import hex.music.core.domain.Tune;
import hex.music.core.domain.Voice;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author hln
 */
@Entity
@Table(name = "Tune")
public class AbcTune implements Tune {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String title;
    @Column
    private String subheader;
    @Column
    private String originator;
    @Column
    private String composer;
    @Column
    private String source;
    @Column
    private String region;
    @Column(length = 2 * KB)
    private String history;
    @Column(length = 2 * KB)
    private String notes;
    @Column
    private String transcriber;
    @Column
    private String rythm;
    @Column
    private String meter;
    @Column
    private String unitNoteLength;
    @OneToOne(cascade = CascadeType.ALL, targetEntity = AbcKey.class)
    private Key musicalKey;
    @Column
    private String tempo;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "tune", targetEntity = AbcVoice.class)
    private final List<Voice> voices = new ArrayList<>();

    public AbcTune() {
    }

    public AbcTune(String title) {
        this.title = title;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return getTitle();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSubheader() {
        return subheader;
    }

    @Override
    public void setSubheader(String subheader) {
        this.subheader = subheader;
    }

    @Override
    public String getComposer() {
        return composer;
    }

    @Override
    public void setComposer(String composer) {
        this.composer = composer;
    }

    @Override
    public String getOriginator() {
        return originator;
    }

    @Override
    public void setOriginator(String originator) {
        this.originator = originator;
    }

    @Override
    public String getRythm() {
        return rythm;
    }

    @Override
    public void setRythm(String rythm) {
        this.rythm = rythm;
    }

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getHistory() {
        return history;
    }

    @Override
    public void setHistory(String history) {
        this.history = history;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public String getTranscriber() {
        return transcriber;
    }

    @Override
    public void setTranscriber(String transcriber) {
        this.transcriber = transcriber;
    }

    @Override
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String getMeter() {
        return meter;
    }

    @Override
    public void setMeter(String meter) {
        this.meter = meter;
    }

    @Override
    public String getUnitNoteLength() {
        return unitNoteLength;
    }

    @Override
    public void setUnitNoteLength(String length) {
        this.unitNoteLength = length;
    }

    @Override
    public Key getKey() {
        return musicalKey;
    }

    @Override
    public void setKey(Key key) {
        this.musicalKey = key;
    }

    @Override
    public String getTempo() {
        return tempo;
    }

    @Override
    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    @Override
    public List<Voice> getVoices() {
        return voices;
    }

    @Override
    public void addVoice(Voice voice) {
        voices.add(voice);
    }
}
