package track.oauthhomework;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

import track.oauthhomework.auth.AuthorizationActivity;


public class LoginFragment extends Fragment implements OnBackPressedListener {
    private final static String TAG = "LoginFragment";

    private final static int REQUEST_CODE = 100;
    private final static int TOKEN_CHECK_CODE = 42;
    private final static int STATUS_OK = 200;

    private final static String KEY_AUTH_TOKEN = "auth_token";
    private final static String AUTH_KEY = "auth";
    private final static String SUCCESS_AUTH = "success";
    private final static String FAIL_AUTH = "fail";

    private String authToken = null;

    private SharedPreferences settings;

    private TextView authorizationResultView;
    private Button tryAgainButton;

    private Boolean quitOnBackPressed = false;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        authorizationResultView = (TextView) view.findViewById(R.id.resultView);
        tryAgainButton = (Button) view.findViewById(R.id.button);
        tryAgainButton.setVisibility(View.INVISIBLE);

        tryAgainButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AuthorizationActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        if (!isNetworkAvailable()) {
            Log.d(TAG, "Network not available");

            authorizationResultView.setText(R.string.network_fail_text);

            quitOnBackPressed = true;

            return view;
        }

        settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        authToken = settings.getString(KEY_AUTH_TOKEN, null);

        if (authToken == null) {
            Log.d(TAG, "authToken == null, confirmed");

            Intent intent = new Intent(getActivity(), AuthorizationActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Log.d(TAG, "Token checking started");

            Intent intent = new Intent(getActivity(), SplashScreenActivity.class);
            intent.putExtra(KEY_AUTH_TOKEN, authToken);
            startActivityForResult(intent, TOKEN_CHECK_CODE);
        }

        return view;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult");

        if (requestCode == REQUEST_CODE) {
            Log.d(TAG, "requestCode == REQUEST_CODE");

            if (resultCode == Activity.RESULT_OK) {
                String token = data.getStringExtra(AuthorizationActivity.ACCESS_TOKEN);

                Log.d(TAG, "resultCode == Activity.RESULT_OK");
                Log.d(TAG, "token = " + token);

                SharedPreferences.Editor editor = settings.edit();
                editor.putString(KEY_AUTH_TOKEN, token);
                editor.commit();

                authorizationResultView.setText(R.string.auth_success_text);
            }
        }

        if (requestCode == TOKEN_CHECK_CODE) {
            Log.d(TAG, "requestCode = TOKEN_CHECK_CODE");

            if (resultCode == Activity.RESULT_CANCELED) {
                authorizationResultView.setText(R.string.failed_auth);

                settings.edit().remove(KEY_AUTH_TOKEN).commit();

                tryAgainButton.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public Boolean onBackPressed() {
        return quitOnBackPressed;
    }

}
