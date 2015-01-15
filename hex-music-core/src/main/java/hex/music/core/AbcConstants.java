package hex.music.core;

/**
 *
 * @author hln
 */
public interface AbcConstants {

    public static final String ABC_ENCODING = "ISO-8859-1",
            UTF_8 = "UTF-8";

    public enum Field {

        B,
        C,
        D,
        F,
        H,
        J,
        K,
        L,
        M,
        N,
        O,
        P,
        Q,
        R,
        S,
        T,
        V,
        W,
        X,
        Z;

        public static String getStartRegexp() {
            StringBuilder result = new StringBuilder("^[");
            for (Field f : values()) {
                result.append(f.name());
            }
            result.append("]:");
            return result.toString();
        }
    }
}
