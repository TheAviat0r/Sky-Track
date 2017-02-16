package track.questiontest;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

/**
 * Created by aviator on 2/16/17.
 */


public class QuestionBank {
    private ArrayList<Question> mQuestionList;
    private int mCurrentIndex = 0;


    QuestionBank(Question[] questionArray) {
        this.mQuestionList = new ArrayList<Question>();

        for (Question question: questionArray) {
            mQuestionList.add(question);
        }
    }

    QuestionBank(ArrayList<Question> questionArray, int currentIndex) {
        this.mQuestionList = new ArrayList<Question>();

        for (Question question: questionArray) {
            mQuestionList.add(question);
        }
    }

    public boolean toNext() {
        if (mCurrentIndex == mQuestionList.size()- 1) {
            return false;
        }

        mCurrentIndex = (mCurrentIndex + 1) % mQuestionList.size();
        return true;
    }

    public boolean toPrev() {
        if (mCurrentIndex == 0) {
            return false;
        }

        mCurrentIndex = (mCurrentIndex - 1) % mQuestionList.size();
        return true;
    }

    public boolean isTestFinished() {
        for (Question question: mQuestionList) {
            if (!question.markedAnswered()) {
                return false;
            }
        }

        return true;
    }

    public Question getQuestion() {
        return mQuestionList.get(mCurrentIndex);
    }

    public ArrayList<Question> getQuestions() {
        return this.mQuestionList;
    }

    public int getIndex() {
        return mCurrentIndex;
    }

}
