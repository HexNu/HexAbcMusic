package hex.music.io.file;

import hex.music.core.AbcConstants;
import java.io.File;

/**
 *
 * @author hln
 */
public interface FileWriter {

    String DEFAULT_ENCODING = AbcConstants.UTF_8;

    File write();
}
