package hex.music.api.resource;

import hex.music.service.provider.ServiceProviderDelegate;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created 2014-nov-27
 *
 * @author jep
 */
public abstract class AbstractResource {

    @HeaderParam("key")
    private String key;

    @HeaderParam("user-key")
    private String userKey;

    @HeaderParam("external-base-uri")
    private String externalBaseUri;

    @Context
    private UriInfo uriInfo;

    public String getKey() {
        if (key == null || key.length() == 0) {
            return "FOLK";
        }
        return key;
    }

    public String getUserKey() {
        if (userKey == null || userKey.length() == 0) {
            return "ABC_MUSIC";
        }
        return userKey;
    }

    public String getBaseUri() {
        if (externalBaseUri == null || externalBaseUri.length() < 1) {
            externalBaseUri = uriInfo.getBaseUri().toString();
        }
        return externalBaseUri;
    }

    public UriBuilder getClassContextUriBuilder() {
        return UriBuilder.fromUri(URI.create(getBaseUri())).path(this.getClass());
    }

    protected static String convertISO88591ToUTF8(String string) throws UnsupportedEncodingException {
        return new String(string.getBytes("ISO-8859-1"), "UTF-8");
    }
}
