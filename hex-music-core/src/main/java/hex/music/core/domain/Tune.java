package hex.music.core.domain;

import java.io.InputStream;
import java.util.List;

/**
 *
 * @author hln
 */
public interface Tune extends DomainEntity {

    /**
     * Getter for the reference number for the Tune.
     *
     * This number is automatially generated.
     *
     * Field in abc: X
     *
     * @return
     */
    @Override
    Long getId();

    /**
     * Getter for the Tune title.
     *
     * Field in abc: T
     *
     * @return
     */
    String getTitle();

    /**
     * Field in abc: T
     *
     * @param title
     */
    void setTitle(String title);

    /**
     * Getter for the Tune title.
     *
     * Field in abc: T
     *
     * @return
     */
    String getSubheader();

    /**
     * Field in abc: T
     *
     * @param subheader
     */
    void setSubheader(String subheader);

    /**
     * Getter for he composer of the Tune.
     *
     * If it is a known composer: Pelle Schenell. If it is a tune where the use
     * originator.
     *
     * Field in abc: C
     *
     * @return
     */
    String getComposer();

    /**
     * Field in abc: C
     *
     * @param composer
     */
    void setComposer(String composer);

    /**
     * The type of rythm.
     *
     * i.e. Polska, Vals et al.
     *
     * Field in abc: R
     *
     * @return
     */
    String getRythm();

    /**
     * Field in abc: R
     *
     * @param rythm
     */
    void setRythm(String rythm);

    /**
     * The geographical origin for the tune.
     *
     * i.e. Hassela, Hälsingland
     *
     * Field in abc: O
     *
     * @return
     */
    String getRegion();

    /**
     * Field in abc: O
     *
     * @param region
     */
    void setRegion(String region);

    /**
     * The source for the tune.
     *
     * i.e. efter Pekkos-Helmer
     *
     * Field in abc: S
     *
     * @return
     */
    String getSource();

    /**
     * Field in abc: S
     *
     * @param source
     */
    void setSource(String source);

    /**
     * Historical information about the tune.
     *
     * Den här låten hade Katrina efter sin far, Korp-Erik. Han...
     *
     * Field in abc: H
     *
     * @return
     */
    String getHistory();

    /**
     * Field in abc: H
     *
     * @param history
     */
    void setHistory(String history);

    /**
     * Notes.
     *
     * i.e. Se även SL Jämtland/Härjedalen 310
     *
     * Field in abc: N
     *
     * @return
     */
    String getNotes();

    /**
     * Field in abc: N
     *
     * @param notes
     */
    void setNotes(String notes);

    /**
     * Name of the person who transcribed the tune to abc.
     *
     * i.e. Aron Betasson
     *
     * Field in abc: Z
     *
     * @return
     */
    String getTranscriber();

    /**
     * Field in abc: Z
     *
     * @param transcriber
     */
    void setTranscriber(String transcriber);

    /**
     * The printed source for the tune.
     *
     * i.e B:Svenska låtar, Jämtland och Härjedalen, nr 211
     *
     * Field in abc: B
     *
     * @return
     */
    String getBibliography();

    /**
     * Field in abc: B
     *
     * @param bibliography
     */
    void setBibliography(String bibliography);

    /**
     * Recording information for the tune.
     *
     * i.e På tre man hand, Jonas Olsson, Fredrik Lind...
     *
     * Field in abc: D
     *
     * @return
     */
    String getDiscography();

    /**
     * Field in abc: D
     *
     * @param discography
     */
    void setDiscography(String discography);

    /**
     * Where the tune or file can be located.
     *
     * i.e http://folkwiki.se/Musik/991
     *
     * Field in abc: F
     *
     * @return
     */
    String getUri();

    /**
     * Field in abc: F
     *
     * @param uri
     */
    void setUri(String uri);

    /**
     * The meter for the tune.
     *
     * i.e. 2/4, 9/8
     *
     * Field in abc: M
     *
     * @return
     */
    String getMeter();

    /**
     * Field in abc: M
     *
     * @param meter
     */
    void setMeter(String meter);

    /**
     * The default note length for the tune body.
     *
     * i.e. 1/8, 1/4
     *
     * Field in abc: L
     *
     * @return
     */
    String getUnitNoteLength();

    /**
     * Field in abc: L
     *
     * @param length
     */
    void setUnitNoteLength(String length);

    /**
     * The key for the tune.
     *
     * i.e. A, Fmix Dm, Edor
     *
     * Field in abc: K
     *
     * @return
     */
    Key getKey();

    /**
     * Field in abc: K
     *
     * @param key
     */
    void setKey(Key key);

    /**
     * Field in abc: Q
     *
     * @return
     */
    String getTempo();

    /**
     * The tempo for the tune in beats per minute.
     *
     * i.e. 1/4=112
     *
     * Also used when converted to midi file. Default tempo is 1/4=120
     *
     * Field in abc: Q
     *
     * @param tempo
     */
    void setTempo(String tempo);

    /**
     * The musical notes part of the tune.
     *
     * This can be one to many voices.
     *
     * Field in abc: V
     *
     * @return
     */
    List<Voice> getVoices();

    /**
     * Field in abc: V
     *
     * @param voice
     */
    void addVoice(Voice voice);
    
    /**
     * First line of the first voice in the tune.
     * 
     * Stripped of accents, bows and chords
     * 
     * @return 
     */
    InputStream getFirstLine();
    
    void setFirstLine(byte[] start);
}
