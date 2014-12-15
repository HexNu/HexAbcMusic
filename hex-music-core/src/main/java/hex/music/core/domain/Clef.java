package hex.music.core.domain;

import hex.music.core.domain.impl.AbcClef;

/**
 *
 * @author hln
 */
public interface Clef extends DomainEntity {

    public static final int DEFAULT_TRANSPOSE = 0;
    public static final Clef DEFAULT_CLEF = new AbcClef();

//    Voice getVoice();
//
//    void setVoice(Voice voice);
    Type getType();

    void setType(Type type);

    int getTranspose();

    void setTranspose(int transpose);

    /**
     * Returns the note representation that is used on the middle (third) line.
     *
     * Defaults are: treble: B; alto: C; tenor: A,; bass: D,; none: B
     *
     * @return
     */
    String getMiddle();

    /**
     * Sets the note representation that is used on the middle (third) line.
     *
     * Defaults are: treble: B; alto: C; tenor: A,; bass: D,; none: B
     *
     * @param middle
     */
    void setMiddle(String middle);

    public enum Type {

        TREBLE("G-klav", "treble"),
        ALTO("C- eller Altklav)", "alto"),
        BASS("F-klav, linje 4", "bass"),
        TREBLE_8VA("G-klav 8va", "treble+8"),
        TREBLE_8VA_BASSO("G-klav 8va basso", "treble-8"),
        BASS_8VA("F-klav 8va", "bass+8"),
        BASS_8VA_BASSO("F-kla 8va basso", "bass-8"),
        TREBLE1("G-klav, linje 1", "treble1"),
        TREBLE2("G-klav, linje 2", "treble2"),
        TREBLE3("G-klav, linje 3", "treble3"),
        TREBLE4("G-klav, linje 4", "treble4"),
        TREBLE5("G-klav, linje 5", "treble5"),
        ALTO1("C-klav, linje 1 (Sopranklav)", "alto1"),
        ALTO2("C-klav, linje 2 (Mezzosopranklav)", "alto2"),
        ALTO3("C-klav, linje 3 (Altklav)", "alto3"),
        ALTO4("C-klav, linje 4 (Tenorklav)", "alto4"),
        ALTO5("C-klav, linje 5 (Baritonklav)", "alto5"),
        BASS1("F-klav, linje 1", "bass1"),
        BASS2("F-klav, linje 2", "bass2"),
        BASS3("F-klav, linje 3", "bass3"),
        BASS4("F-klav, linje 4", "bass4"),
        BASS5("F-klav, linje 5", "bass5"),
        TENOR("C-klav, linje 4", "tenor"),
        SOPRANO("C-klav, linje 1", "alto1"),
        MEZZOSOPRANO("C-klav, linje 2", "alto2"),
        BARITON("F-klav, linje 3", "bass3");
        private final String label;
        private final String code;
        public static final Type DEFAULT_TYPE = TREBLE;

        private Type(String label, String code) {
            this.label = label;
            this.code = code;

        }

        public String getLabel() {
            return label;
        }

        public String getCode() {
            return code;
        }

        public static Type getByCode(String code) {
            for (Type t : values()) {
                if (t.getCode().equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return DEFAULT_TYPE;
        }
    }
}
