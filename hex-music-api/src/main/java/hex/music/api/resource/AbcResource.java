package hex.music.api.resource;

import hex.music.core.domain.Tune;
import hex.music.service.command.tune.CreateTunesFromAbcDocCommand;
import hex.music.service.command.tune.GetAllTunesCommand;
import java.io.InputStream;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author hln
 */
@Path("tunes/abc")
public class AbcResource extends AbstractResource {

    @GET
    public Response getTuneList() {
        List<Tune> tuneList = commandExecutor.execute(new GetAllTunesCommand(), getKey());
        StringBuilder result = new StringBuilder();
        tuneList.stream().forEach((c) -> {
            System.out.println(c.getTitle());
            System.out.println(c.getComposer());
            System.out.println(c.getOriginator());
            System.out.println(c.getMeter());
            System.out.println(c.getKey().getSignature().getLabel());
            result.append("title=").append(c.getTitle())
                    .append("\nkey=").append(c.getKey().getSignature().getCode()).append("\n\n");
        });
        return Response.ok(result.toString()).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upploadAbcDoc(@FormDataParam("file") InputStream inputStream) {
        List<Tune> newTunes = commandExecutor.executeInTransaction(new CreateTunesFromAbcDocCommand(inputStream), getKey());
        return Response.ok("Number of tunes created: " + newTunes.size()).build();
    }
}
