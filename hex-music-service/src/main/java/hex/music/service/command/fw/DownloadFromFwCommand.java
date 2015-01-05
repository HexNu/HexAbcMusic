package hex.music.service.command.fw;

import hex.music.core.domain.Tune;
import hex.music.fw.search.DownloadLinks;
import hex.music.io.parse.AbcDocumentParser;
import hex.music.service.command.AbstractServiceCommand;
import hex.music.service.command.tune.SaveTuneCommand;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hln
 */
public class DownloadFromFwCommand extends AbstractServiceCommand<List<Tune>> {

    private final String tuneId;

    public DownloadFromFwCommand(String tuneId) {
        this.tuneId = tuneId;
    }

    @Override
    public List<Tune> execute() {
        try {
            Map<String, String> linkMap = new DownloadLinks(tuneId).get();
            String url = linkMap.get("abc");
            URLConnection connection = new URL(url).openConnection();
            InputStream stream = connection.getInputStream();
            List<Tune> result = new AbcDocumentParser(stream, "UTF-8").parse();
            result.stream().forEach((t) -> {
                t.setUri("http://folkwiki.se/Musik/" + tuneId);
                executeSubcommand(new SaveTuneCommand(t));
            });
            return result;
        } catch (MalformedURLException ex) {
            Logger.getLogger(DownloadFromFwCommand.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DownloadFromFwCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
