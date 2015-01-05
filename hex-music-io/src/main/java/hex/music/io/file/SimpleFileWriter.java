package hex.music.io.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hln
 */
public class SimpleFileWriter implements FileWriter {

    private final String content;
    private final File file;
    private final String encoding;

    public SimpleFileWriter(String content, File file) {
        this(content, file, DEFAULT_ENCODING);
    }

    public SimpleFileWriter(String content, File file, String encoding) {
        this.content = content;
        this.file = file;
        this.encoding = encoding;
    }

    @Override
    public File write() {
        File result = null;
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file), encoding);
            writer.append(content);
            result = file;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SimpleFileWriter.class.getName()).log(Level.SEVERE, ex.getMessage());
            throw new RuntimeException(ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimpleFileWriter.class.getName()).log(Level.SEVERE, ex.getMessage());
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleFileWriter.class.getName()).log(Level.SEVERE, ex.getMessage());
            throw new RuntimeException(ex);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(SimpleFileWriter.class.getName()).log(Level.SEVERE, ex.getMessage());
                throw new RuntimeException(ex);
            }
        }
        return result;
    }
}
