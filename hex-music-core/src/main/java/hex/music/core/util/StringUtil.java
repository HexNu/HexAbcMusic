package hex.music.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hln
 */
public class StringUtil {

    private final String string;

    public StringUtil(String string) {
        this.string = string;
    }

    public List<String> split() {
        List<String> result = new ArrayList<>();
//        Matcher m = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'").matcher(string);
        Matcher m = Pattern.compile("[^\\s\"=']+|\"[^\"]*\"|'[^']*'").matcher(string);
        while (m.find()) {
            result.add(m.group());
        }
        return result;
    }

    public static void main(String[] args) {
        String testar = "V:V1 name=\"1:a Fiol\" subname=\"F 1\" clef=treble";
        for (String s : new StringUtil(testar).split()) {
            System.out.println(s);
        }
//        String shortTest = "V:V1";
//        for (String s : new StringUtil(shortTest).split()) {
//            System.out.println(s);
//        }

    }
}
