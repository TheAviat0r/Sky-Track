package track.oauthhomework;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class SplashScreenActivity extends AppCompatActivity implements TokenCheckerListener {
    private final static String TAG = "SplashScreenActivity>";
    private final static int STATUS_OK = 200;
    private final static String KEY_AUTH_TOKEN = "auth_token";

    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.d(TAG, "onCreate");

        Intent intent = getIntent();
        authToken = intent.getStringExtra(KEY_AUTH_TOKEN);

        new AccessTokenChecker(this).execute(authToken);
    }

    @Override
    public void onSuccessCheck()
    {
        // Nothing happens here, user stays on activity with splash screen
        Log.d(TAG, "onSuccessCheck");
    }

    @Override
    public void onFailCheck(String token)
    {
        Log.d(TAG, "onFailCheck");

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);

        finish();
    }

    private class AccessTokenChecker extends AsyncTask<String, Void, Boolean> {
        private TokenCheckerListener mListener;
        private String authToken;

        private Boolean validationResult;

        public AccessTokenChecker(TokenCheckerListener listener) {
            this.mListener = listener;
            this.validationResult = false;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            Log.d(TAG, "doInBackground");

            if (params.length != 1) {
                return false;
            }

            authToken = params[0];

            try {
                URL url = new URL(getString(R.string.check_url));

                Log.d(TAG, "authToken = " + authToken);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Authorization", "Bearer " + authToken);

                urlConnection.connect();
                int connectionStatus = urlConnection.getResponseCode();

                Log.d(TAG, "connectionStatus = " + connectionStatus);
                Log.d(TAG, "urlConnection method = " + urlConnection.getRequestMethod());
                Log.d(TAG, "urlConnection header = " + urlConnection.getRequestProperty("Authorization"));


                if (connectionStatus == STATUS_OK) {
                    validationResult = true;
                } else {
                    validationResult = false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return validationResult;
        }

        @Override
        protected void onPostExecute(Boolean validationResult) {
            if (!validationResult) {
                mListener.onFailCheck(authToken);
            } else {
                mListener.onSuccessCheck();
            }
        }
    }
}
