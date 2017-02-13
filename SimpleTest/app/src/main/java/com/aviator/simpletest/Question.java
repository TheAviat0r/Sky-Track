package com.aviator.simpletest;

/**
 * Created by aviator on 2/11/17.
 */

public class Question {
    private int mText;
    private boolean mAnswer;

    Question(int textId, boolean answer) {
        mText = textId;
        mAnswer = answer;
    }

    public int getText() {
        return mText;
    }

    public void setText(int mText) {
        this.mText = mText;
    }

    public boolean getAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        this.mAnswer = answer;
    }
}
