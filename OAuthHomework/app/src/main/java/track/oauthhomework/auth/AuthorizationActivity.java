package track.oauthhomework.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URI;

import track.oauthhomework.R;
import track.oauthhomework.AuthorizationListener;

public class AuthorizationActivity extends AppCompatActivity implements AuthorizationListener {
    private final static String TAG = "WEB VIEW";

    public final static String ACCESS_TOKEN = "access_token";
    public final static String AUTH_ERROR = "auth_error";

    public final static String EXTRA_CLIENT_ID = "client_id";
    public final static String EXTRA_CLIENT_SECRET = "client_secret";

    private String authToken;
    private String authUrl;
    private String callbackUrl;

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        webView = (WebView) findViewById(R.id.auth_view);
        webView.clearCache(false);
        webView.clearHistory();

        authUrl = getString(R.string.auth_url);
        callbackUrl = getString(R.string.callback_url);
    }

    @Override
    public void onResume() {
        super.onResume();
        URI uri = URI.create(authUrl);

        webView.setWebViewClient(new OAuthWebClient(this));
        webView.loadUrl(uri.toString());
    }

    @Override
    public void onAuthStarted() {
    }

    @Override
    public void onComplete(String token) {
        Intent intent = new Intent();
        intent.putExtra(ACCESS_TOKEN, token);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onError(String error) {
    }

    private class OAuthWebClient extends WebViewClient {
        private static final String TAG = "WebViewClient";

        private AuthorizationListener mListener;

        public OAuthWebClient(AuthorizationListener listener) {
            this.mListener = listener;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            Log.d(TAG, "[onPageReceived] URL:<" + url + ">");

            if (url.startsWith(callbackUrl)) {
                String tokenUrl = url.split("=")[1];
                authToken = tokenUrl.split("&")[0];

                Log.d(TAG, "[TOKEN ACQUIRED]: " + authToken);

                if (url.contains("error")) {
                    onError(url);
                } else {
                    onComplete(authToken);
                }
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            mListener.onError(error.toString());
        }
    }

    private class AccessTokenGetter extends AsyncTask<String, Void, String> {
        private AuthorizationListener listener;

        public AccessTokenGetter(AuthorizationListener listener) {
            this.listener = listener;
        }

        @Override
        public String doInBackground(String ... params) {

            return params[0];
        }
    }
}
