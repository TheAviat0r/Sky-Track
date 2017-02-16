package track.questiontest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private final static String KEY_SCORE = "PlayerScore";

    private TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreView = (TextView) findViewById(R.id.total_score);

        Intent intent = getIntent();
        Integer playerScore = intent.getIntExtra(KEY_SCORE, 0);

        Log.d("RESULT", "playerScore = " + playerScore);

        scoreView.setText(playerScore.toString());
    }
}
