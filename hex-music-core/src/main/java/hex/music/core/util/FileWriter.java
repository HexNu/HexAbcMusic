package hex.music.core.util;

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
