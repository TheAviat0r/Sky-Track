package track.oauthhomework;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.net.URI;

import track.oauthhomework.auth.AuthorizationActivity;


public class LoginFragment extends Fragment {
    private final static String TAG = "LoginFragment";

    private final static int REQUEST_CODE = 100;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new LoginOnClickListener());

        return view;
    }

    private class LoginOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AuthorizationActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onAcitivtyResult");

        if (requestCode == REQUEST_CODE) {
            Log.d(TAG, "requestCode == REQUEST_CODE");

            if (resultCode == Activity.RESULT_OK) {
                String token = data.getStringExtra(AuthorizationActivity.ACCESS_TOKEN);
                Toast.makeText(getContext(), token, Toast.LENGTH_SHORT).show();

                Log.d(TAG, "resultCode == Activity.RESULT_OK");
                Log.d(TAG, "token = + + token");
            }
        }
    }

}
