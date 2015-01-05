package hex.music.io.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author hln
 */
public class ZipFileWriter implements FileWriter {

    private final FilenameFilter filenameFilter;
    private final File originDir;
    private final File resultFile;
    private final String resultFilename = "result.zip";

    public ZipFileWriter(File originDir, String... extensionsToZip) {
        this.originDir = originDir;
        String zipFilename = this.originDir + "/" + resultFilename;
        File zipFile = new File(zipFilename);
        if (zipFile.exists()) {
            zipFile.delete();
        }
        resultFile = new File(zipFilename);
        filenameFilter = (File dir, String name) -> {
            for (String extension : extensionsToZip) {
                if (name.endsWith("." + extension)) {
                    return true;
                }
            }
            return false;
        };
    }

    @Override
    public File write() {
        try {
            FileOutputStream fos = new FileOutputStream(resultFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File[] files = originDir.listFiles(filenameFilter);
            for (File fileToZip : files) {
                addToZipFile(fileToZip.getAbsolutePath(), zos);
            }
            zos.flush();
            zos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ZipFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ZipFileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultFile;
    }

    private static void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(fileName.substring(fileName.lastIndexOf("/")));
        zos.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        fis.close();
        zos.closeEntry();
        Logger.getLogger(ZipFileWriter.class.getName()).log(Level.INFO, "Writing \"[0}\" to zip file.", fileName);
    }
}
