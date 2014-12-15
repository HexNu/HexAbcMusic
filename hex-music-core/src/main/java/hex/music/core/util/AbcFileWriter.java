package hex.music.core.util;

import hex.music.core.AbcConstants;
import java.io.File;

/**
 *
 * @author hln
 */
public class AbcFileWriter extends SimpleFileWriter {

    public AbcFileWriter(String content, File file) {
        super(content, file, AbcConstants.ABC_ENCODING);
    }

}
