package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GeoQuiz extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button  mTrueButton;
    private Button  mFalseButton;
    private Button  mNextButton;         //TOODO: Ask what a button's weight does...
    private Button  mCheatButton;
    private int     mCurrentIndex = 0;
    private boolean mIsCheater;

    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
        new Question(R.string.question_oceans, true),
        new Question(R.string.question_mideast, false),
        new Question(R.string.question_africa, false),
        new Question(R.string.question_americas, true),
        new Question(R.string.question_asia, true)
    };


    private void updateQuestion(){
       int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }


    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if(mIsCheater){
            messageResId = R.string.judgment_toast;
        }else {

            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_geo_quiz);

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();

        //The trick here is that they didn't tell you that the setText Method allows for an ID (int)
        //and doesn't *require* a String.
        mQuestionTextView.setText(question);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(true);
            }
        });


        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });


        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //Start Cheat Activity - We use the static method on the target Activity as it allows
                // the child activity to drive how the extras as passed in.
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(GeoQuiz.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }

        updateQuestion();

    }

    /**
     * This method was added to ensure that we saved the information before the activity was destroyed
     * and restarted when we rotate the device. This also happens when the Activity goes into paused,
     * or stopped state.
     * @param saveInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    /**
     * These following methods were added in chapter 3.
     * Ensure that your rememebr the log.d live template which is logd.
     */
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart: called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause: called");
    }
    
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume: called");
    }
    
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop: called");
    }
    
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_geo_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
