package org.jyu.itks545;

import android.content.SharedPreferences;

/**
 *
 * @author Bela Borbely <bela.borbely at student.jyu.fi>
 * @version 9.4.2013
 */
public class UserId {

    private int user_id;
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;

    public static UserId create(SharedPreferences pref) {
        UserId userId = new UserId();
        userId.user_id = pref.getInt("user_id", 0);
        userId.consumerKey = pref.getString("consumerKey", null);
        userId.consumerSecret = pref.getString("consumerSecret", null);
        userId.accessToken = pref.getString("accessToken", null);
        return userId;
    }
    
    public boolean isRegistered(){
        return user_id>0 && consumerKey!=null && consumerSecret!=null && accessToken!=null;
    }
}
