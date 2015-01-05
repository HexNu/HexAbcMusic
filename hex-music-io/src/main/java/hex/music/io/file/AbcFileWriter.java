package hex.music.io.file;

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
