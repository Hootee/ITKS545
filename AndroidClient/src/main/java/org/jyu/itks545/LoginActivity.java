package org.jyu.itks545;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.jyu.itks545.MyOAuth.AccessToken;
import org.jyu.itks545.MyOAuth.Authorize;
import org.jyu.itks545.MyOAuth.RequestToken;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private RequestToken requestToken;
    /**
     * The default email to populate the email field with.
     */
    public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private String mEmail;
    private String mPassword;
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private View mLoginStatusView;
    private TextView mLoginStatusMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        setupActionBar();

        // Set up the login form.
        mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
        mEmailView = (EditText) findViewById(R.id.email);
        mEmailView.setText(mEmail);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id,
                    KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mLoginStatusView = findViewById(R.id.login_status);
        mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

        findViewById(R.id.sign_in_button).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                // TODO: If Settings has multiple levels, Up should navigate up
                // that hierarchy.
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    public void startRegistration() {
        String url = getString(R.string.server) + "register_user";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        mEmail = mEmailView.getText().toString();
        mPassword = mPasswordView.getText().toString();
        Log.e(TAG, mEmail + " - " + mPassword);
//        boolean cancel = false;
//        View focusView = null;

        // Check for a valid password.
//        if (TextUtils.isEmpty(mPassword)) {
//            mPasswordView.setError(getString(R.string.error_field_required));
//            focusView = mPasswordView;
//            cancel = true;
//        } else if (mPassword.length() < 4) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(mEmail)) {
//            mEmailView.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
//            cancel = true;
//        } else if (!mEmail.contains("@")) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        Log.e(TAG, "StartLoginTask");
        mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
        showProgress(true);
        mAuthTask = new UserLoginTask();
        mAuthTask.execute((Void) null);
//        }
    }

    protected void login() {
        Intent intent = new Intent(this, MyMapActivity.class);
        startActivity(intent);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mLoginStatusView.setVisibility(View.VISIBLE);
            mLoginStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginStatusView.setVisibility(show ? View.VISIBLE
                            : View.GONE);
                }
            });

            mLoginFormView.setVisibility(View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE
                            : View.VISIBLE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                String url = "http://192.168.1.3/index.php/login/" + mEmailView.getText().toString() + "/" + mEmailView.getText().toString();
                Log.e(TAG, "Login request: " + url);
                HttpGet get = new HttpGet(url);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpClient.execute(get, responseHandler);
                Log.e(TAG, "Login response: " + response);
                JSONObject jsonResponse = new JSONObject(response);
                int user_id = jsonResponse.getInt("user_id");
                String consumer_key = jsonResponse.getString("consumer_key");
                String consumer_secret = jsonResponse.getString("consumer_secret");
                Log.e(TAG, "Login user_id: " + user_id + " cosnumer_key: " + consumer_key + " conumer_secret: " + consumer_secret);
                requestToken = new RequestToken(consumer_key, consumer_secret);
                try {
                    requestToken.sendRequest();
                    Log.e(TAG, "Request token response: " + requestToken.getResponseString());

                    Authorize authorize = new Authorize(requestToken);
//                    String request = authorize.sendRequest(new String[]{"user_id", "" + user_id});
                    get = new HttpGet(authorize.getURI() + "&user_id=" + user_id);
                    response = httpClient.execute(get, responseHandler);

//                    Log.e(TAG, "Authorize request: " + request);
                    Log.e(TAG, "Authorize response: " + response);
                    AccessToken accessToken = new AccessToken(response, requestToken);
                    accessToken.sendRequest();
                    Log.e(TAG, "Access token response: " + accessToken.getResponseString());
                } catch (Exception e) {
                    // TODO Automaattisesti luotu catch-lohko
                    e.printStackTrace();
                }

//                authorizer = new Authorize(requestToken);

                //			try {
                //				String authorization_uri = authorizer.getURI().toString();
                //				Log.d("UserLoginTask", "auth_url:" + authorization_uri);
                //				Intent i = new Intent(Intent.ACTION_VIEW);
                //				i.setData(Uri.parse(authorization_uri));
                //				startActivity(i);
                //								
                //				
                //			} catch (URISyntaxException e) {
                //				// TODO Automaattisesti luotu catch-lohko
                //				e.printStackTrace();
                //			}

                //			authorizeUser(authorizer.getURI());

                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }

        private boolean authorizeUser(URI uri) {
            AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
            HttpGet get = new HttpGet(uri);

            try {
                HttpResponse result = client.execute(get);
                InputStream is = result.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                Log.i("UserLoginTask", "response: " + sb.toString());

                reader.close();
                is.close();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                client.close();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                login();
                Log.i("LoginActivity", "success");
                finish();
            } else {
                Log.e(TAG, "FUCK");
                Log.i("LoginActivity", "failure");
                mPasswordView
                        .setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
