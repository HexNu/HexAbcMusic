package hex.music.core.domain;

/**
 *
 * @author hln
 */
public enum Key {

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
    G_MAJ("G dur", "G"),
    G_MIN("G moll", "Gm"),
    G_DOR("G dorisk", "Gdor"),
    G_MIX("G mixolydisk", "Gmix");
    private final String label;
    private final String code;

    private Key(String label, String code) {
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
