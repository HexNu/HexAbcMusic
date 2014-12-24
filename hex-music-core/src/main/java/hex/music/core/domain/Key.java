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

        A_FLAT_MAJ("Ab dur", "Ab"),
        A_FLAT_MIN("Ab moll", "Abm"),
        A_FLAT_DOR("Ab dorisk", "Abdor"),
        A_FLAT_LYD("Ab lydisk", "Ablyd"),
        A_FLAT_MIX("Ab mixolydisk", "Abmix"),
        A_MAJ("A dur", "A"),
        A_MIN("A moll", "Am"),
        A_DOR("A dorisk", "Ador"),
        A_PHR("A frygisk", "Aphr"),
        A_LYD("A lydisk", "Alyd"),
        A_MIX("A mixolydisk", "Amix"),
        A_LOC("A lokrisk", "Aloc"),
        A_SHARP_MIN("A moll", "A#m"),
        A_SHARP_PHR("A# frygisk", "A#phr"),
        A_SHARP_LOC("A# lokrisk", "A#loc"),
        B_FLAT_MAJ("Bb dur", "Bb"),
        B_FLAT_MIN("Bb moll", "Bbm"),
        B_FLAT_DOR("Bb dorisk", "Bbdor"),
        B_FLAT_PHR("Bb frygisk", "Bbphr"),
        B_FLAT_LYD("Bb lydisk", "Bblyd"),
        B_FLAT_MIX("Bb mixolydisk", "Bbmix"),
        B_FLAT_LOC("Bb lokrisk", "Bbloc"),
        B_MAJ("B dur", "B"),
        B_MIN("B moll", "Bm"),
        B_DOR("B dorisk", "Bdor"),
        B_PHR("B frygisk", "Bphr"),
        B_LYD("B lydisk", "Blyd"),
        B_MIX("B mixolydisk", "Bmix"),
        B_LOC("B lokrisk", "Bloc"),
        B_SHARP_LOC("B# lokrisk", "B#loc"),
        C_FLAT_MAJ("Cb dur", "Cb"),
        C_FLAT_LYD("Cb lydisk", "Cblyd"),
        C_MAJ("C dur", "C"),
        C_MIN("C moll", "Cm"),
        C_DOR("C dorisk", "Cdor"),
        C_PHR("C frygisk", "Cphr"),
        C_LYD("C lydisk", "Clyd"),
        C_MIX("C mixolydisk", "Cmix"),
        C_LOC("C lokrisk", "Cloc"),
        C_SHARP_MAJ("C# dur", "C#"),
        C_SHARP_MIN("C# moll", "C#m"),
        C_SHARP_DOR("C# dorisk", "C#dor"),
        C_SHARP_PHR("C# frygisk", "C#phr"),
        C_SHARP_MIX("C# mixolydisk", "C#mix"),
        C_SHARP_LOC("C# lokrisk", "C#loc"),
        D_FLAT_MAJ("Db dur", "Db"),
        D_FLAT_DOR("Db dorisk", "Dbmix"),
        D_FLAT_LYD("Db lydisk", "Dblyd"),
        D_FLAT_MIX("Db mixolydisk", "Dbmix"),
        D_MAJ("D dur", "D"),
        D_MIN("D moll", "Dm"),
        D_DOR("D dorisk", "Ddor"),
        D_PHR("D frygisk", "Dphr"),
        D_LYD("D lydisk", "Dlyd"),
        D_MIX("D mixolydisk", "Dmix"),
        D_LOC("D lokrisk", "Dloc"),
        D_SHARP_MIN("D# moll", "D#m"),
        D_SHARP_DOR("D# dorisk", "D#dor"),
        D_SHARP_PHR("D# frygisk", "D#phr"),
        D_SHARP_LOC("D# lokrisk", "D#loc"),
        E_FLAT_MAJ("Eb dur", "Eb"),
        E_FLAT_MIN("Eb moll", "Ebm"),
        E_FLAT_DOR("Eb dorisk", "Ebdor"),
        E_FLAT_PHR("Eb frygisk", "Ebphr"),
        E_FLAT_LYD("Eb lydisk", "Eblyd"),
        E_FLAT_MIX("Eb mixolydisk", "Ebmix"),
        E_MAJ("E dur", "E"),
        E_MIN("E moll", "Em"),
        E_DOR("E dorisk", "Edor"),
        E_PHR("E frygisk", "Ephr"),
        E_LYD("E lydisk", "Elyd"),
        E_MIX("E mixolydisk", "Emix"),
        E_LOC("E lokrisk", "Eloc"),
        E_SHARP_PHR("E# frygisk", "E#phr"),
        E_SHARP_LOC("E# lokrisk", "E#loc"),
        F_FLAT_LYD("Fb lydisk", "Fblyd"),
        F_MAJ("F dur", "F"),
        F_MIN("F moll", "Fm"),
        F_DOR("F dorisk", "Fdor"),
        F_PHR("F frygisk", "Fphr"),
        F_LYD("F lydisk", "Flyd"),
        F_MIX("F mixolydisk", "Fmix"),
        F_LOC("F lokrisk", "Floc"),
        F_SHARP_MAJ("F# dur", "F#"),
        F_SHARP_MIN("F# moll", "F#m"),
        F_SHARP_DOR("F# dorisk", "F#dor"),
        F_SHARP_PHR("F# frygisk", "F#phr"),
        F_SHARP_LYD("F# lydisk", "F#lyd"),
        F_SHARP_MIX("F# mixolydisk", "F#mix"),
        F_SHARP_LOC("F# lokrisk", "F#loc"),
        G_FLAT_MAJ("Gb dur", "Gb"),
        G_FLAT_LYD("Gb lydisk", "Gblyd"),
        G_FLAT_MIX("Gb mixolydisk", "Gbmix"),
        G_MAJ("G dur", "G"),
        G_MIN("G moll", "Gm"),
        G_DOR("G dorisk", "Gdor"),
        G_PHR("G frygisk", "Gphr"),
        G_LYD("G lydisk", "Glyd"),
        G_MIX("G mixolydisk", "Gmix"),
        G_LOC("G lokrisk", "Gloc"),
        G_SHARP_MIN("G# moll", "G#m"),
        G_SHARP_DOR("G# dorisk", "G#dor"),
        G_SHARP_PHR("G# frygisk", "G#phr"),
        G_SHARP_MIX("G# mixolydisk", "G#mix"),
        G_SHARP_LOC("G# lokrisk", "G#loc"),
        HP_ACCIDENTALS("Säckpipa (höjt f och c)", "Hp"),
        HP_NO_ACCIDENTALS("Säckpipa (utan förtecken)", "HP");
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

        public static Signature getByString(String signatureString) {
            for (Signature t : values()) {
                if (t.getLabel().equalsIgnoreCase(signatureString)) {
                    return t;
                }
            }
            return getByCode(signatureString);
        }
    }
}
