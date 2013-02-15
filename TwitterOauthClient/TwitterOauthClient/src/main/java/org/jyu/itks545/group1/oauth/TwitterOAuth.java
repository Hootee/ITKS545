package org.jyu.itks545.group1.oauth;

import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URL;

/**
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
    
    public static class TwitterRequestToken extends OAuthGetRequest{

        public TwitterRequestToken() {
            super(TESTAPP_OAUTH_CONSUMERKEY, TESTAPP_OAUTH_CONSUMERSECRET, TWITTER_REQUEST_TOKEN_PATH);
        }
        
    }
    
    public static class TwitterAuthorize{

        private final TwitterRequestToken requestToken;

        public TwitterAuthorize(TwitterRequestToken requestToken) {
            this.requestToken = requestToken;
        }
        
        public void openBrowser() throws Exception{
            Desktop.getDesktop().browse(new URL(TWITTER_AUTHORIZE_PATH + "?oauth_token=" + requestToken.getResponseString("oauth_token")).toURI());
        }
        
    }
}
