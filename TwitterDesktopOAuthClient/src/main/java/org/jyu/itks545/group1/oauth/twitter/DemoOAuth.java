package org.jyu.itks545.group1.oauth.twitter;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import org.jyu.itks545.group1.oauth.OAuthGetRequest;

/**
 * Base for Twitter OAuth http-messages
 * 
 * @author Bela Borbely <bela.z.borbely at gmail.com>
 * @version 14.2.2013
 */
public class DemoOAuth {

    private static final String TESTAPP_OAUTH_CONSUMERKEY = "8c556453e8161858e577e3489635c7c2051263054";
    private static final String TESTAPP_OAUTH_CONSUMERSECRET = "8d00bf6b91b0683b3d626642e11c5784";
    private static final String API_URL = "http://itks545/index.php";
    private static final String TEST_REGISTER_PATH = API_URL + "/register";
    private static final String TEST_REQUEST_TOKEN_PATH = API_URL + "/request_token";
    private static final String TEST_AUTHORIZE_PATH = API_URL + "/authorize";
    private static final String TEST_ACCESS_TOKEN_PATH = API_URL + "/access_token";
    public static final String TEST_AUTHORIZED_REQUEST_PATH = API_URL + "/messages/add/25.852331/62.22241/1/";

    public static class RequestToken extends OAuthGetRequest {

        public RequestToken() {
            super(TESTAPP_OAUTH_CONSUMERKEY, TESTAPP_OAUTH_CONSUMERSECRET, TEST_REQUEST_TOKEN_PATH);
        }
    }

    public static class Authorize extends OAuthGetRequest {

        private final RequestToken requestToken;

        public Authorize(RequestToken requestToken) {
            super(TESTAPP_OAUTH_CONSUMERKEY, TESTAPP_OAUTH_CONSUMERSECRET, TEST_AUTHORIZE_PATH);
            this.requestToken = requestToken;
        }

        public URI getURI() throws URISyntaxException {
            return new URI(TEST_AUTHORIZE_PATH + "?oauth_token=" + requestToken.response_oauth_token());
        }
    }

    public static class AccessToken extends OAuthGetRequest {

        public AccessToken(String pin, RequestToken requestToken) {
            super(TESTAPP_OAUTH_CONSUMERKEY, TESTAPP_OAUTH_CONSUMERSECRET, TEST_ACCESS_TOKEN_PATH);
            super.putValue(ParameterKey.oauth_verifier, pin);
            super.putValue(ParameterKey.oauth_token, requestToken.response_oauth_token());
            super.putValue(ParameterKey.oauth_token_secret, requestToken.response_oauth_token_secret());
        }
    }
    
    public static class AuthorizedRequest extends OAuthGetRequest {

        public AuthorizedRequest(String message, AccessToken accessToken) throws Exception {
            super(TESTAPP_OAUTH_CONSUMERKEY, TESTAPP_OAUTH_CONSUMERSECRET, TEST_AUTHORIZED_REQUEST_PATH + URLEncoder.encode(message, "UTF-8"));
            super.putValue(OAuthGetRequest.ParameterKey.oauth_token, accessToken.response_oauth_token());
            super.putValue(OAuthGetRequest.ParameterKey.oauth_token_secret, accessToken.response_oauth_token_secret());
        }
    }
}
