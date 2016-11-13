package track.oauthhomework.auth;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import java.net.URI;

import track.oauthhomework.R;

public class AuthorizationActivity extends AppCompatActivity {
    public final String ACCESS_TOKEN = "access_token";
    public final String AUTH_ERROR = "auth_error";

    public final static String EXTRA_CLIENT_ID = "client_id";
    public final static String EXTRA_CLIENT_SECRET = "client_secret";

    private String clientId;
    private String clientSecret;

    private String authToken;
    private String authUrl;
    private String callbackUrl;

    private WebView webView;

    public static Intent createAuthActivityIntent(Context context, String clientId, String clientSecret) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CLIENT_ID, clientId);
        intent.putExtra(EXTRA_CLIENT_SECRET, clientSecret);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        webView = (WebView) findViewById(R.id.auth_view);

        authUrl = getString(R.string.auth_url);
        callbackUrl = getString(R.string.callback_url);
    }

    @Override
    public void onResume() {
        super.onResume();
        URI uri = URI.create(authUrl);

        webView.loadUrl(uri.toString());
    }
}
