package track.questiontest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aviator on 2/13/17.
 */

public class Question implements Parcelable {
    private int textResId;
    private boolean isTrue;
    private boolean isAnswered = false;
    private int scoreMultiplier = 1;

    public Question(int resId, boolean answer) {
        this.textResId = resId;
        this.isTrue = answer;
    }

    private Question(Parcel in) {
        this.textResId = in.readInt();
        this.isTrue = in.readByte() != 0;
        this.isAnswered = in.readByte() != 0;
        this.scoreMultiplier = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(textResId);
        out.writeByte((byte) (isTrue ? 1 : 0));
        out.writeByte((byte) (isAnswered ? 1 : 0));
        out.writeInt(scoreMultiplier);
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }
        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getTextResId() {
        return this.textResId;
    }

    public boolean getAnswer() {
        return this.isTrue;
    }

    public int getScoreMultiplier() { return this.scoreMultiplier; }

    public void setTextResId(int resId) {
        this.textResId = resId;
    }

    public void setAnswer(boolean answer) {
        this.isTrue = answer;
    }

    public void setScoreMultiplier(int value) { this.scoreMultiplier = value; }

    public void markAnswered() {
        this.isAnswered = true;
    }

    public boolean markedAnswered() {
        return this.isAnswered;
    }
}
