package org.jyu.itks545;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Base for Twitter OAuth http-messages
 *
 * @author Bela Borbely <bela.z.borbely at gmail.com>
 * @version 14.2.2013
 */
public class MyOAuth {

//    private static final String API_URL = "http://itks545.it.jyu.fi/toarjusa/itks545/index.php";
    private static final String API_URL = "http://192.168.1.3/index.php";
    private static final String REQUEST_TOKEN_PATH = API_URL + "/request_token";
    private static final String AUTHORIZE_PATH = API_URL + "/authorize";
    private static final String ACCESS_TOKEN_PATH = API_URL + "/access_token";

    public static class RequestToken extends OAuthGetRequest {

        public RequestToken(String client_identifier,String client_sharedsecret) {
            super(client_identifier, client_sharedsecret, REQUEST_TOKEN_PATH);
        }
    }

    public static class Authorize extends OAuthGetRequest {

        private final RequestToken requestToken;

        public Authorize(RequestToken requestToken) {
            super(requestToken.getConsumerKey(), requestToken.getConsumerSecret(), AUTHORIZE_PATH);
            this.requestToken = requestToken;
        }

        public URI getURI() throws URISyntaxException {
            return new URI(AUTHORIZE_PATH + "?oauth_token=" + requestToken.response_oauth_token());
        }
    }

    public static class AccessToken extends OAuthGetRequest {

        public AccessToken(String pin, RequestToken requestToken) {
            super(requestToken.getConsumerKey(), requestToken.getConsumerSecret(), ACCESS_TOKEN_PATH);
            super.putValue(ParameterKey.oauth_verifier, pin);
            super.putValue(ParameterKey.oauth_token, requestToken.response_oauth_token());
            super.putValue(ParameterKey.oauth_token_secret, requestToken.getResponseString(ParameterKey.oauth_token_secret.toString()));
        }
    }
}
