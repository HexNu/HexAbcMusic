package hex.music.core.domain;

import java.util.List;

/**
 *
 * @author hln
 */
public interface MetaData extends DomainEntity {

    /**
     * The reference number for the tune.
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
     * The tune title.
     *
     * Field in abc: T
     *
     * @return
     */
    String getTitle();

    void setTitle(String title);

    /**
     * The composer of the tune.
     *
     * If it is a known composer: Pelle Schenell. If it is a tune where the use
     * originator.
     *
     * Field in abc: C
     *
     * @return
     */
    String getComposer();

    void setComposer(String composer);

    /**
     * The musician that the tune originates from.
     *
     * i. e. Katrina Lundstet. If the composer is known, use that field.
     * 
     * Field in abc: C
     *
     * @return
     */
    String getOriginator();

    void setOriginator(String originator);

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

    void setRegion(String region);

    /**
     * The source for the tune.
     *
     * i.e. Svenska låtar Hälsingland nr 633
     *
     * Field in abc: S
     *
     * @return
     */
    String getSource();

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

    void setHistory(String history);

    /**
     * Notes.
     *
     * i.e Se även SL Jämtland/Härjedalen 310
     *
     * Field in abc: N
     *
     * @return
     */
    String getNotes();

    void setNotes(String notes);

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

    void setKey(Key key);

    /**
     * The tempo for the tune in beats per minute.
     *
     * i.e. 1/4=112
     *
     * Also used when converted to midi file. Default tempo is 1/4=120
     *
     * Field in abc: Q
     *
     * @return
     */
    String getTempo();

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

    void addVoice(Voice voice);
}
