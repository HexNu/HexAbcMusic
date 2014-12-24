package hex.music.core.domain;

/**
 *
 * @author hln
 */
public interface Voice extends DomainEntity {

    public static final String DEFAULT_VOICE_CODE = "V1";

    String getVoiceCode();

    void setVoiceCode(String code);

    void setName(String name);

    String getSubname();

    void setSubname(String subname);

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
