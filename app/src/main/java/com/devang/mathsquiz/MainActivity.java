package com.devang.mathsquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView que, ansA, ansB, ansC, ansD, correctAns, timer, yourScore, highScore;
    int score, noOfQuestion;
    CountDownTimer countDownTimer;
    SharedPreferences mSharedPreferences;

    int HIGH_SCORE;
    long secsLeft;

    int rightans;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences("HighScoree", Context.MODE_PRIVATE);
        final SharedPreferences preferences = getSharedPreferences("HighScoree", MODE_PRIVATE);

        int highscore = preferences.getInt("highscore", 0);
        highScore = findViewById(R.id.txtHighScore);
        if (highscore == 0) {
            highScore.setText("0");
        } else {
            highScore.setText("HighScore:"+highscore);
        }


        score = 0;
        noOfQuestion = 0;

        que = findViewById(R.id.txtQuestion);
        yourScore = findViewById(R.id.txtYourScore);


        ansA = findViewById(R.id.txtA);
        ansB = findViewById(R.id.txtB);
        ansC = findViewById(R.id.txtC);
        ansD = findViewById(R.id.txtD);
        timer = findViewById(R.id.txtTimer);

        correctAns = findViewById(R.id.txtCorrectAnswer);

        questionGenerate();

        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                secsLeft = l / 1000;

                timer.setText("00:" + l / 1000);

            }


            private void checkHighScore()
            {
               int prefscore= preferences.getInt("highscore",0);
                if(score >= prefscore)
                {
                    SharedPreferences.Editor editor = mSharedPreferences.edit();

                    editor.putInt("highscore", score);


                    editor.apply();
                }
            }
            @Override
            public void onFinish() {

                checkHighScore();


                setUpAlertDialogueBuilder();


            }
        }.start();


    }

    private void setUpAlertDialogueBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.popup, null);
        builder.setView(view);
        builder.setCancelable(false);
        TextView scoreTextView = view.findViewById(R.id.txtScorePop);
        Button playAgain = view.findViewById(R.id.btnPlayAgain);
        Button noThanks = view.findViewById(R.id.btnNoThanks);


        scoreTextView.setText("Your score is: " + score + " out of " + noOfQuestion);


        AlertDialog dialog = builder.create();

        dialog.show();


        View.OnClickListener alertButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btnPlayAgain) {
                   resetGame();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                } else if (view.getId() == R.id.btnNoThanks) {
                   finish();
                }
            }
        };
        playAgain.setOnClickListener(alertButtonClickListener);
        noThanks.setOnClickListener(alertButtonClickListener);


    }

    private void resetGame() {
        score = 0;
        noOfQuestion = 0;
    }

    private void checkAnswers(Integer ans) {
        if (ans == rightans) {
            score = score + 1;

            correctAns.setText("Correct");

            correctAns.setTextColor(this.getResources().getColor(android.R.color.holo_green_light));


        } else {

            correctAns.setTextColor(this.getResources().getColor(android.R.color.holo_red_light));
            correctAns.setText("Wrong");

        }

    }

    public void questionGenerate() {

        noOfQuestion = noOfQuestion + 1;
        Random random = new Random();

        int a = random.nextInt(100);
        int b = random.nextInt(100);

        if (a == 0 && b == 0 || a == 0 || b == 0) {
            a = random.nextInt(100);
            b = random.nextInt(100);
        }
        que.setText("Q."+noOfQuestion+" What is " + a + " + " + b + " ? ");

        final ArrayList<Integer> anslist = new ArrayList<>();
        anslist.clear();

        anslist.add(random.nextInt(200));
        anslist.add(random.nextInt(100));
        anslist.add(random.nextInt(100));
        rightans = a + b;
        anslist.add(rightans);

        Collections.shuffle(anslist);


        ansA.setText(anslist.get(0).toString());
        ansB.setText(anslist.get(1).toString());
        ansC.setText(anslist.get(2).toString());
        ansD.setText(anslist.get(3).toString());


        ansA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAnswers(anslist.get(0));
                questionGenerate();


            }
        });
        ansB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswers(anslist.get(1));
                questionGenerate();

            }
        });
        ansC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswers(anslist.get(2));
                questionGenerate();

            }
        });
        ansD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswers(anslist.get(3));
                questionGenerate();

            }
        });

        yourScore.setText("Your Score is " + String.valueOf(score) + " out of " + String.valueOf(noOfQuestion));


    }


}