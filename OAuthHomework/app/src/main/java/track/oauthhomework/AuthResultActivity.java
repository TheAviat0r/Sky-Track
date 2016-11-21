package track.oauthhomework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AuthResultActivity extends AppCompatActivity {
    private final static String SUCCESS_AUTH = "success";
    private final static String FAIL_AUTH = "fail";

    private final static String AUTH_KEY = "auth";

    private TextView authResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_result1);

        Bundle extras = getIntent().getExtras();
        String value;

        if (extras != null) {
            value = extras.getString(AUTH_KEY);

            if (value == SUCCESS_AUTH) {
                setContentView(R.layout.activity_auth_result1);
                authResultText = (TextView)findViewById(R.id.authResult);
                authResultText.setText(getString(R.string.auth_result_text));
            } else if (value == FAIL_AUTH) {
                setContentView(R.layout.activity_auth_result2);
                authResultText = (TextView)findViewById(R.id.failText);
                authResultText.setText(getString(R.string.fail_result_text));

                Button tryAgainButton = (Button) findViewById(R.id.tryAgain);

                tryAgainButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

            }
        } else {
            finish();
        }


    }
}
