package track.questiontest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {
    private final static String TAG = "QuestionActivity";
    private final static String KEY_INDEX = "CurrentIndex";
    private final static String KEY_SCORE = "PlayerScore";
    private final static String KEY_ARRAY = "QuestionsData";

    private int mStartIndex = 0;
    private int mPlayerScore = 0;
    private int mScorePerAnswer = 100;
    private int mScoreLoose = 50;

    private boolean rightAnswer;
    private int questionTextId;

    private Question[] mQuestionData = new Question[] {
            new Question(R.string.q1, false),
            new Question(R.string.q2, true),
            new Question(R.string.q3, true)
    };

    private QuestionBank mQuestionBank;

    private TextView mQuestionText;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private Button mNextButton;
    private Button mPrevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        if (savedInstanceState != null) {
            mStartIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mPlayerScore = savedInstanceState.getInt(KEY_SCORE, 0);
            ArrayList<Question> data = savedInstanceState.getParcelableArrayList(KEY_ARRAY);
            mQuestionBank = new QuestionBank(data, mStartIndex);
        } else {
            mQuestionBank = new QuestionBank(mQuestionData);
        }

        initializeWidgets();
    }

    private void initializeWidgets() {
        Log.d(TAG, " initializeWidgets");

        mQuestionText = (TextView) findViewById(R.id.question);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, " trueButton - onClick");
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, " falseButton - onCLick");
                checkAnswer(false);
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, " cheatButton - onClick");
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, " nextButton - onClick");
                nextQuestion();
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, " prevButton - onClick");
                prevQuestion();
            }
        });

        updateQuestion();
    }

    private void checkAnswer(boolean answer) {
        Question question = mQuestionBank.getQuestion();

        if (rightAnswer == answer && !question.markedAnswered()) {
            Log.d(TAG, "Right answer: score = " + mScorePerAnswer + " , multi: = " + question.getScoreMultiplier());
            mPlayerScore += mScorePerAnswer * question.getScoreMultiplier();
            Toast.makeText(this, "Right answer", Toast.LENGTH_SHORT).show();
        } else if (rightAnswer != answer && !question.markedAnswered()) {
            Toast.makeText(this, "Wrong answer", Toast.LENGTH_SHORT).show();
        }

        if (question.markedAnswered()) {
            Toast.makeText(this, "You already answered to that question", Toast.LENGTH_SHORT).show();
        }

        question.markAnswered();

        if (mQuestionBank.isTestFinished()) {
            Log.d(TAG, "Total score: " + mPlayerScore);
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra(KEY_SCORE, mPlayerScore);
            startActivity(intent);
        }
    }

    private void updateQuestion() {
        questionTextId = mQuestionBank.getQuestion().getTextResId();
        rightAnswer = mQuestionBank.getQuestion().getAnswer();
        Log.d(TAG, " updateQuestion: question[" + questionTextId + "] rightAnswer[" + new Boolean(rightAnswer).toString() + "]");

        mQuestionText.setText(questionTextId);
    }

    private void nextQuestion() {
        Log.d(TAG, " nextQuestion");

        if (mQuestionBank.toNext()) {
            updateQuestion();
        } else {
            Toast.makeText(this, "There are no next questions", Toast.LENGTH_SHORT).show();
        }
    }

    private void prevQuestion() {
        Log.d(TAG, " prevQuestion");

        if (mQuestionBank.toPrev()) {
            updateQuestion();
        } else {
            Toast.makeText(this, "There are no previous questions", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, " onSaveInstanceState - saving data: [PlayerScore]=" + mPlayerScore);

        savedInstanceState.putInt(KEY_INDEX, mQuestionBank.getIndex());
        savedInstanceState.putInt(KEY_SCORE, mPlayerScore);
        savedInstanceState.putParcelableArrayList(KEY_ARRAY, mQuestionBank.getQuestions());

        super.onSaveInstanceState(savedInstanceState);
    }
}
