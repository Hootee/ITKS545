package org.jyu.itks545.group1.oauth;

import java.awt.Desktop;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Some tests about OAuth against Twitter
 * @author Bela Borbely <bela.z.borbely at gmail.com>
 * @version 14.2.2013
 */
public class TwitterOAuthTest{
    
    @Test
    public void testRequestToken() throws Exception {
        TwitterOAuth.TwitterRequestToken requestTokenRequest = new TwitterOAuth.TwitterRequestToken();
        requestTokenRequest.sendRequest();
        boolean isset_oauth_token = requestTokenRequest.getResponseString().indexOf("oauth_token")>=0;
        boolean isset_oauth_token_secret = requestTokenRequest.getResponseString().indexOf("oauth_token_secret")>=0;
        assertTrue(isset_oauth_token && isset_oauth_token_secret);
        
        TwitterOAuth.TwitterAuthorize authorize = new TwitterOAuth.TwitterAuthorize(requestTokenRequest);
        authorize.openBrowser();
    }
}
