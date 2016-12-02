package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

        private static final String TAG = "CheatActivity";

        private static final String EXTRA_ANSWER_IS_TRUE
                ="com.bignerdranch.android.GeoQuiz.answer_is_true";

        private static final String EXTRA_ANSWER_SHOWN
                ="com.bignerdranch.android.GeoQuiz.answer_shown";

        private boolean mAnswerIsTrue;
        private Button mShowAnswerButton;
        private TextView mAnswerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        //This is where we get the current question's answer. Out of the intent, which is defined
        //in the parent class.
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);


        /*TODO: ASK HOW THE LAYOUTS CAN BE SUBCLASSED BY ACTIVITY AS OTHERWIESE YOU ARE GOING TO NEED TO REMEMBER THE ID'S ACROSS LAYOUTS WITHIN THE PROJECT
        */
        mShowAnswerButton = (Button)findViewById(R.id.showAnswerButton);
        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mAnswerTextView.setText("The Answer was [" + String.valueOf(mAnswerIsTrue) +" ]");
                setAnswerShownResult(true);
            }
        });

    }


    /* This is how you should allow parent activities to create child activies... Give them a template*/
    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }


    /* This flow is kind of crazy, you send the intent data back, but then pass it back and forth
     to ensure that you don't need to parse it on the parent Activity. Seems odd, but I understand why
     */

    private void setAnswerShownResult(Boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }
}
