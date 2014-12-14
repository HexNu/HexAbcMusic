package hex.music.core.domain;

/**
 *
 * @author hln
 */
public interface Voice extends DomainEntity {

    String getCode();

    void setCode(String code);

    void setName(String name);

    String getShortName();

    void setShortName(String shortName);

    MetaData getMetaData();

    void setMetaData(MetaData metaData);

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
}
