package com.aviator.simpletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


public class QuestionActivity extends AppCompatActivity {
    private final static String TAG = "QuestionActivity";
    private final static String KEY_INDEX = "index";

    private TextView mQuestionView;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private Button mNextButton;
    private Button mPrevButton;

    private Question[] mQuestionsBank = new Question[] {
            new Question(R.string.q1, true),
            new Question(R.string.q2, true),
            new Question(R.string.q3, true)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            Log.d(TAG, " current index restored = " + mCurrentIndex);
        }

        mQuestionView = (TextView) findViewById(R.id.question);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        int question = mQuestionsBank[mCurrentIndex].getText();
        boolean correctAnswer = mQuestionsBank[mCurrentIndex].getAnswer();

        mQuestionView.setText(question);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, " onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
}
