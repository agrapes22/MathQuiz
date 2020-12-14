package com.example.mathquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button bt_start, bt_answer0, bt_answer1, bt_answer2, bt_answer3;
    TextView tv_score, tv_questions, tv_timer, tv_bottomMessage;
    ProgressBar pb_timer;

    Game g = new Game();
    int secondsRemaining = 30;

    CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished)
        {
            secondsRemaining--;
            tv_timer.setText(Integer.toString(secondsRemaining) + " sec");
            pb_timer.setProgress(30 - secondsRemaining);
        }

        @Override
        public void onFinish()
        {
            bt_answer0.setEnabled(false);
            bt_answer1.setEnabled(false);
            bt_answer2.setEnabled(false);
            bt_answer3.setEnabled(false);

            tv_bottomMessage.setText("Time is up! Score: " + g.getNumCorrect() + "/" + (g.getTotalQuestions() - 1));

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    bt_start.setVisibility(View.VISIBLE);
                }
            }, 4000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_start = findViewById(R.id.bt_start);
        bt_answer0 = findViewById(R.id.bt_answer0);
        bt_answer1 = findViewById(R.id.bt_answer1);
        bt_answer2 = findViewById(R.id.bt_answer2);
        bt_answer3 = findViewById(R.id.bt_answer3);

        tv_score = findViewById(R.id.tv_score);
        tv_questions = findViewById(R.id.tv_question);
        tv_timer = findViewById(R.id.tv_timer);
        tv_bottomMessage = findViewById(R.id.tv_bottomMessage);

        pb_timer = findViewById(R.id.pb_timer);

        tv_timer.setText("0 Sec");
        tv_questions.setText("");
        tv_bottomMessage.setText("Press Go!");
        tv_score.setText("0 pts");

        View.OnClickListener startButtonClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Button startButton = (Button) v;
                secondsRemaining = 30;
                g = new Game();
                startButton.setVisibility(View.INVISIBLE);
                tv_score.setText(Integer.toString(g.getScore()));
                nextTurn();
                timer.start();
            }
        };

        bt_start.setOnClickListener(startButtonClickListener);

        View.OnClickListener answerButtonClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Button buttonClicked = (Button) v;

                int answerSelected = Integer.parseInt(buttonClicked.getText().toString());

                g.checkAnswer(answerSelected);
                tv_score.setText(Integer.toString(g.getScore()));
                nextTurn();

            }
        };

        bt_answer0.setOnClickListener(answerButtonClickListener);
        bt_answer1.setOnClickListener(answerButtonClickListener);
        bt_answer2.setOnClickListener(answerButtonClickListener);
        bt_answer3.setOnClickListener(answerButtonClickListener);

    }

    private void nextTurn()
    {
        g.makeNewQuestion();
        int [] answers = g.getCurrentQuestion().getAnswerArray();
        bt_answer0.setText(Integer.toString(answers[0]));
        bt_answer1.setText(Integer.toString(answers[1]));
        bt_answer2.setText(Integer.toString(answers[2]));
        bt_answer3.setText(Integer.toString(answers[3]));

        bt_answer0.setEnabled(true);
        bt_answer1.setEnabled(true);
        bt_answer2.setEnabled(true);
        bt_answer3.setEnabled(true);

        tv_questions.setText(g.getCurrentQuestion().getQuestionPhrase());

        tv_bottomMessage.setText(g.getNumCorrect() + "/" + (g.getTotalQuestions() - 1));

    }

}