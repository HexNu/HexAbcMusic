package hex.music.core.domain;

/**
 *
 * @author hln
 */
public interface Key extends DomainEntity {

    Signature getSignature();

    void setSignature(Signature signature);

    Clef getClef();

    void setClef(Clef clef);

    public enum Signature {

        A_MAJ("A dur", "A"),
        A_MIN("A moll", "Am"),
        A_DOR("A dorisk", "Ador"),
        A_MIX("A mixolydisk", "Amix"),
        B_FLAT_MAJ("Bb dur", "Bb"),
        B_FLAT_MIX("Bb mixolydisk", "Bbmix"),
        B_MAJ("B dur", "B"),
        B_Min("B moll", "Bm"),
        B_DOR("B dorisk", "Bdor"),
        B_MIX("B mixolydisk", "Bmix"),
        C_MAJ("C dur", "C"),
        C_MIN("C moll", "Cm"),
        C_DOR("C dorisk", "Cdor"),
        C_MIX("C mixolydisk", "Cmix"),
        C_SHARP_MIN("Ciss moll", "C#m"),
        D_MAJ("D dur", "D"),
        D_MIN("D moll", "Dm"),
        D_DOR("D dorisk", "Ddor"),
        D_MIX("D mixolydisk", "Dmix"),
        E_FLAT_MAJ("Eb dur", "Eb"),
        E_FLAT_MIX("Eb mixolydisk", "Ebmix"),
        E_MAJ("E dur", "E"),
        E_MIN("E moll", "Em"),
        E_DOR("E dorisk", "Edor"),
        E_MIX("E mixolydisk", "Emix"),
        F_MAJ("F dur", "F"),
        F_MIN("F moll", "Fm"),
        F_DOR("F dorisk", "Fdor"),
        F_MIX("F mixolydisk", "Fmix"),
        F_SHARP_MIN("Fiss moll", "F#m"),
        G_MAJ("G dur", "G"),
        G_MIN("G moll", "Gm"),
        G_DOR("G dorisk", "Gdor"),
        G_MIX("G mixolydisk", "Gmix");
        private final String label;
        private final String code;
        public static final Signature DEFAULT_TYPE = D_MAJ;

        private Signature(String label, String code) {
            this.label = label;
            this.code = code;
        }

        public String getLabel() {
            return label;
        }

        public String getCode() {
            return code;
        }

        public static Signature getByCode(String code) {
            for (Signature t : values()) {
                if (t.getCode().equalsIgnoreCase(code)) {
                    return t;
                }
            }
            return DEFAULT_TYPE;
        }
    }
}
