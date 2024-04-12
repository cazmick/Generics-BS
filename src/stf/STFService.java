package stf;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * This class creates a STF Service object.
 */
public class STFService {
   
    private String stfUrlDomain="http://stf.infoedge.com";  // Change this URL
    private String auth="3c9a7c2789dd4b2fbb1682b273a1e09a1027ff62c23544f5bd70225901453613";  //stf api access token //user:automation,email:automation@yopmail.com

    private String stfUrl;
    private String authToken;

    public STFService() throws MalformedURLException, URISyntaxException {
        this.stfUrl = new URL(stfUrlDomain).toURI().resolve("/api/v1/").toString();
        this.authToken = auth;
    }

    public String getStfUrl() {
        return stfUrl;
    }

    public void setStfUrl(String stfUrl) throws MalformedURLException, URISyntaxException {
        this.stfUrl = new URL(stfUrl).toURI().resolve("/api/v1/").toString();;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}