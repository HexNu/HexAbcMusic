package hex.music.core.domain;

/**
 *
 * @author hln
 */
public interface Voice extends DomainEntity {

    public static final String DEFAULT_VOICE_ID = "V1";

    String getVoiceId();

    void setVoiceId(String code);

    void setName(String name);

    String getSubname();

    void setShortName(String shortName);

    Tune getTune();

    void setTune(Tune tune);

    /**
     * The index for the voice.
     *
     * i.e. 1 (for first voice/part)
     *
     * Field in abc: V
     *
     * @return
     */
    int getVoiceIndex();

    void setVoiceIndex(int index);

    /**
     * The clef to be used for the part.
     *
     * @return
     */
    Clef getClef();

    void setClef(Clef clef);

    String getBody();

    void setBody(String body);

    void addBodyLine(String line);
}
