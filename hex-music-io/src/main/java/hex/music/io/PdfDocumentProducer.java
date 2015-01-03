package hex.music.io;

import hex.music.core.domain.Tune;
import hex.music.core.util.AbcFileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hln
 */
public class PdfDocumentProducer {

    private final List<Tune> tunes;
    private static final String ABCM_2_PS_COMMAND = "abcm2ps",
            ABC_FILE_PATH = "/tmp/result.abc",
            INDENT = "-I",
            INDENT_VALUE = "1cm",
            PIPE = "|",
            PS_2_PDF_COMMAND = "ps2pdf",
            PS_OUTPUT_FILE_FLAG = "-O",
            PS_FILE_PATH = "/tmp/result.ps",
            PDF_FILE_PATH = "/tmp/result.pdf";

    public PdfDocumentProducer(List<Tune> tunes) {
        this.tunes = tunes;
    }

    public InputStream produce() throws IOException, InterruptedException {
        return processPs();
    }

    private InputStream processPs() throws IOException, InterruptedException {
        File psFile = processAbc();
        File pdfFile = new File(PDF_FILE_PATH);
        pdfFile.deleteOnExit();
        List<String> commands = new ArrayList<>();
        commands.add(PS_2_PDF_COMMAND);
        commands.add(psFile.getAbsolutePath());
        commands.add(PDF_FILE_PATH);
        ProcessBuilder builder = new ProcessBuilder(commands);
        Process process = builder.start();
        process.waitFor();
        return new FileInputStream(pdfFile);
    }

    private File processAbc() throws IOException, InterruptedException {
        File abcFile = produceAbcFile();
        File psFile = new File(PS_FILE_PATH);
        psFile.deleteOnExit();
        List<String> commands = new ArrayList<>();
        commands.add(ABCM_2_PS_COMMAND);
        commands.add(PS_OUTPUT_FILE_FLAG);
        commands.add(PS_FILE_PATH);
        commands.add(INDENT);
        commands.add(INDENT_VALUE);
        commands.add(abcFile.getAbsolutePath());
        ProcessBuilder builder = new ProcessBuilder(commands);
        Process process = builder.start();
        process.waitFor();
        return psFile;
    }

    private File produceAbcFile() throws IOException {
        File abcFile = new File(ABC_FILE_PATH);
        abcFile.deleteOnExit();
        StringBuilder abcResult = new StringBuilder();
        tunes.stream().forEach((tune) -> {
            abcResult.append(new AbcDocumentProducer(tune).produce());
        });
        new AbcFileWriter(abcResult.toString(), abcFile).write();
        return abcFile;
    }
}
