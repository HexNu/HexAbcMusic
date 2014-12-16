package hex.music.api.resource;

import hex.music.core.domain.Tune;
import hex.music.service.ServiceProvider;
import hex.music.service.provider.ServiceProviderDelegate;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author hln
 */
@Path("tunes/abc")
public class AbcResource extends AbstractResource {

    private final ServiceProviderDelegate delegate = new ServiceProviderDelegate(getKey());

    @GET
    public Response getTuneList() {
        List<Tune> tuneList = delegate.getAbcMusicService().getAllTunes();
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
}
