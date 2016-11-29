package track.oauthhomework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

//    private class AccessToken extends AsyncTask<Boolean, Void, String> {
//        private AuthorizationListener listener;
//
//        AccessTokenGetter(AuthorizationListener listener) {
//            this.listener = listener;
//        }
//
//        @Override
//        protected Boolean doInBackground(String... params) {
////            String url = String.format(tokenUrlTemplate, clientId, "&", clientSecret, "&", params[0]);
//            try {
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String token) {
//            if (token.contains("Error")) {
//                listener.onError(token);
//            } else {
//                listener.onComplete(token);
//            }
//        }
//    }
}
