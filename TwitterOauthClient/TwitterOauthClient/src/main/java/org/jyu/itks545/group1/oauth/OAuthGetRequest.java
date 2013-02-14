package org.jyu.itks545.group1.oauth;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import twitter4j.internal.http.BASE64Encoder;

/**
 * Baseclass for HTTP-requests for OAuth-process
 * @author Bela Borbely <bela.z.borbely at gmail.com>
 * @version 14.2.2013
 */
public class OAuthGetRequest {

    public enum ParameterKey {

        oauth_version,
        oauth_nonce,
        oauth_timestamp,
        oauth_consumer_key,
        oauth_signature_method,
        oauth_signature
    };
    private static final String DEFAULT_OAUTH_VERSION = "1.0";
    private static final String DEFAULT_OAUTH_SIGNATURE_METHOD = "HMAC-SHA1";
    private final String consumerKey;
    private final String consumerSecret;
    private final String httpUrl;
    private HttpGet httpGet;
    private String response;
    private Map<String, String> parameters = new LinkedHashMap<String, String>();
    private HttpClient httpClient = new DefaultHttpClient();
    private ResponseHandler<String> responseHandler = new BasicResponseHandler();
    private Random random = new Random();

    /**
     * Constructor -  consumer key / secret and url
     * @param consumerKey
     * @param consumerSecret
     * @param httpUrl 
     */
    public OAuthGetRequest(String consumerKey, String consumerSecret, String httpUrl) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.httpUrl = httpUrl;
    }

    /**
     * Fill the parameter map. Generate signature, nonce, etc 
     * @throws Exception 
     */
    private void collectParameters() throws Exception {
        parameters.put(ParameterKey.oauth_consumer_key.toString(), consumerKey);
        parameters.put(ParameterKey.oauth_signature_method.toString(), DEFAULT_OAUTH_SIGNATURE_METHOD);
        parameters.put(ParameterKey.oauth_version.toString(), DEFAULT_OAUTH_VERSION);
        parameters.put(ParameterKey.oauth_nonce.toString(), nonce());
        parameters.put(ParameterKey.oauth_timestamp.toString(), timestamp());
        parameters.put(ParameterKey.oauth_signature.toString(), signature());
    }

    /**
     * URL-encode inputtext
     * @param input
     * @return
     * @throws Exception 
     */
    private String encode(String input) throws Exception {
        return URLEncoder.encode(input, "UTF-8");
    }

    /**
     * Sign basestring with customersecret
     * @return
     * @throws Exception 
     */
    private String signature() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        // Some secret about secret: consumerSecret + "&"
        SecretKeySpec secret = new SecretKeySpec((consumerSecret + "&").getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(secret);
        byte[] digest = mac.doFinal(baseString().getBytes("UTF-8"));
        String signature = BASE64Encoder.encode(digest);
        return signature;
    }

    /**
     * Input for signing
     * @return
     * @throws Exception 
     */
    private String baseString() throws Exception {
        List<String> params = new LinkedList<String>(parameters.keySet());
        Collections.sort(params);
        StringBuilder bs = new StringBuilder("GET&");
        bs.append(encode(httpUrl)).append("&");
        int lastIndex = params.size() - 1;
        for (int i = 0; i <= lastIndex; i++) {
            boolean last = i == lastIndex;
            String param = params.get(i);
            String value = parameters.get(param);
            bs.append(encode(param + "=" + value));
            if (last) {
                break;
            }
            bs.append(encode("&"));

        }
        String baseString = bs.toString();
        debug("Basestring: " + baseString);
        return baseString;
    }

    /**
     * Some random nonsence
     * @return 
     */
    private String nonce() {
        return "" + System.currentTimeMillis() + random.nextInt();
    }

    /**
     * Timestamp in seconds
     * @return 
     */
    private String timestamp() {
        return "" + System.currentTimeMillis() / 1000;
    }

    /**
     * Create the requested HTTP GET request url
     * @throws Exception 
     */
    private void buildRequestUrl() throws Exception {
        StringBuilder queryString = new StringBuilder(httpUrl);
        queryString.append("?");
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            queryString.append(encode(key)).append("=").append(encode(value)).append("&");
        }
        queryString.deleteCharAt(queryString.length() - 1);
        httpGet = new HttpGet(queryString.toString());
    }

    /**
     * Send request and store the response string
     * @throws Exception 
     */
    public void sendRequest() throws Exception {
        collectParameters();
        buildRequestUrl();
        debug("Request URL: " + httpGet.getRequestLine().getUri());
        response = httpClient.execute(httpGet, responseHandler);
    }

    /**
     * Response
     * @return 
     */
    public String getResponse() {
        return response;
    }

    public static void debug(String message) {
        Logger.getLogger(OAuthGetRequest.class.getName()).debug(message);
    }

    public static void error(Exception ex) {
        Logger.getLogger(OAuthGetRequest.class).error(ex.getMessage(), ex);
    }
}
