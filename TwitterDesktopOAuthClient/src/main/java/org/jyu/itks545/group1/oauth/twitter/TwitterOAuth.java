package org.jyu.itks545.group1.oauth.twitter;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import org.jyu.itks545.group1.oauth.OAuthGetRequest;

/**
 * Base for Twitter OAuth http-messages
 * 
 * @author Bela Borbely <bela.z.borbely at gmail.com>
 * @version 14.2.2013
 */
public class TwitterOAuth {

    private static final String TESTAPP_OAUTH_CONSUMERKEY = "FNDrxvtA6S4yVqZDA9Bz0w";
    private static final String TESTAPP_OAUTH_CONSUMERSECRET = "RwOSIGwmazvyWibR44FUYE5oq3eG1s3dZADpDBv4";
    private static final String TWITTER_REQUEST_TOKEN_PATH = "https://api.twitter.com/oauth/request_token";
    private static final String TWITTER_AUTHORIZE_PATH = "https://api.twitter.com/oauth/authorize";
    private static final String TWITTER_ACCESS_TOKEN_PATH = "https://api.twitter.com/oauth/access_token";

    public static class RequestToken extends OAuthGetRequest {

        public RequestToken() {
            super(TESTAPP_OAUTH_CONSUMERKEY, TESTAPP_OAUTH_CONSUMERSECRET, TWITTER_REQUEST_TOKEN_PATH);
        }
    }

    public static class Authorize extends OAuthGetRequest {

        private final RequestToken requestToken;

        public Authorize(RequestToken requestToken) {
            super(TWITTER_AUTHORIZE_PATH, TESTAPP_OAUTH_CONSUMERKEY, TESTAPP_OAUTH_CONSUMERSECRET);
            this.requestToken = requestToken;
        }

        public URI getURI() throws URISyntaxException {
            return new URI(TWITTER_AUTHORIZE_PATH + "?oauth_token=" + requestToken.response_oauth_token());
        }
    }

    public static class AccessToken extends OAuthGetRequest {

        public AccessToken(String pin, RequestToken requestToken) {
            super(TESTAPP_OAUTH_CONSUMERKEY, TESTAPP_OAUTH_CONSUMERSECRET, TWITTER_ACCESS_TOKEN_PATH);
            super.putValue(ParameterKey.oauth_verifier, pin);
            super.putValue(ParameterKey.oauth_token, requestToken.getResponseString(ParameterKey.oauth_token.toString()));
        }
    }
}
