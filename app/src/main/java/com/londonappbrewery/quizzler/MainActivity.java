package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // TODO: Declare constants here


    // TODO: Declare member variables here:
        Button mTrueButton;
        Button mFalseButton;
        TextView mQuestionTextView;
        TextView mScoreTextView;
        int mIndex;
        int mQuestion;
        int mScore;
        ProgressBar mProgressBar;



    // TODO: Uncomment to create question bank
    private com.londonappbrewery.quizzler.TrueFalse[] mQuestionBank = new com.londonappbrewery.quizzler.TrueFalse[] {
            new com.londonappbrewery.quizzler.TrueFalse(R.string.question_1, true),
            new com.londonappbrewery.quizzler.TrueFalse(R.string.question_2, true),
            new com.londonappbrewery.quizzler.TrueFalse(R.string.question_3, true),
            new com.londonappbrewery.quizzler.TrueFalse(R.string.question_4, true),
            new com.londonappbrewery.quizzler.TrueFalse(R.string.question_5, true),
            new com.londonappbrewery.quizzler.TrueFalse(R.string.question_6, false),
            new com.londonappbrewery.quizzler.TrueFalse(R.string.question_7, true),
            new com.londonappbrewery.quizzler.TrueFalse(R.string.question_8, false),
            new com.londonappbrewery.quizzler.TrueFalse(R.string.question_9, true),
            new com.londonappbrewery.quizzler.TrueFalse(R.string.question_10, true),
            new com.londonappbrewery.quizzler.TrueFalse(R.string.question_11, false),
            new com.londonappbrewery.quizzler.TrueFalse(R.string.question_12, false),
            new com.londonappbrewery.quizzler.TrueFalse(R.string.question_13,true)
    };
    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0/ mQuestionBank.length);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( savedInstanceState != null){
            mScore =savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");

        }
        else{
            mScore = 0;
            mIndex = 0;

        }


        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mQuestionTextView =(TextView) findViewById(R.id.question_text_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mScoreTextView = (TextView)  findViewById(R.id.score);

        mQuestion = mQuestionBank[mIndex].getQuestionId();
        mQuestionTextView.setText(mQuestion);
        mScoreTextView.setText("Score"+ mScore + "/" + mQuestionBank.length);




        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateQuestion();
            }
        });


        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            updateQuestion();
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mScoreTextView = (TextView)  findViewById(R.id.score);


    }

    private void updateQuestion(){
        mIndex = (mIndex+1)% mQuestionBank.length;

        if(mIndex == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You Scored "+mScore+"points!");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();

        }
        mQuestion = mQuestionBank[mIndex].getQuestionId();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score"+mScore+"/"+mQuestionBank.length);
    }

    private void checkAnswer(boolean userSelection){
        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();

        if(userSelection == correctAnswer){
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mScore = mScore+1;
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }


    }
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);
    }
}
