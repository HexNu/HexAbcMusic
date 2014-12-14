package hex.music.core.domain;

/**
 *
 * @author hln
 */
public interface Clef extends DomainEntity {

    Voice getVoice();

    void setVoice(Voice voice);

    Type getType();

    int getTranspose();

    public enum Type {

        TREBLE("G klav", "treble"),
        TREBLE_8VA("G klav 8va", "treble+8"),
        TREBLE_8VA_BASSO("G klav 8va basso", "treble+8"),
        SOPRANO("C klav, linje 1", "alto1"),
        MEZZOSOPRANO("Alt klav, linje 2", "alto2"),
        ALTO("C klac, linje 3 (Alt klav)", "alto"),
        TENOR("C klav, linje 4", "tenor"),
        BARITON("F klav, linje 3", "bass3"),
        BASS("F klav, linje 4", "bass");
        private final String label;
        private final String code;

        private Type(String label, String code) {
            this.label = label;
            this.code = code;

        }

        protected String getLabel() {
            return label;
        }

        protected String getCode() {
            return code;
        }
    }
}
