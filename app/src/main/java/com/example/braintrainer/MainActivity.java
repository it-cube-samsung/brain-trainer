package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    CountDownTimer timer;
    TextView timeTextView;
    TextView questionTextView;
    TextView scoreTextView;
    TextView resultTextView;

    TableLayout answersLayout;

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    Random random = new Random();
    ArrayList<Integer> answers = new ArrayList<>();
    int locationCorrectAnswer;
    int counterQuestions;
    int counterCorrect;


    public void start(View v) {
        resetScore();
        goButton.setVisibility(View.INVISIBLE);
        generateQuestion();
        questionTextView.setVisibility(View.VISIBLE);
        answersLayout.setVisibility(View.VISIBLE);
        timer = new CountDownTimer(30 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setTime((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                mediaPlayer.start();
                finishGame();
            }
        };
        timer.start();
    }

    private void finishGame() {
        answersLayout.setVisibility(View.INVISIBLE);
        questionTextView.setVisibility(View.INVISIBLE);
        resultTextView.setText(String.format("Your result: %d/%d", counterCorrect, counterQuestions));
        goButton.setVisibility(View.VISIBLE);
    }

    public void checkAnswer(View v) {
        counterQuestions += 1;
        if (Integer.parseInt(v.getTag().toString()) == locationCorrectAnswer) {
            counterCorrect += 1;
            resultTextView.setText("Correct!");
        } else {
            resultTextView.setText("Wrong :(");
        }
        setScore(counterCorrect, counterQuestions);
        generateQuestion();
    }

    private void setScore(int a, int b) {
        scoreTextView.setText(a + "/" + b);
    }

    private void resetScore() {
        counterCorrect = 0;
        counterQuestions = 0;
        setScore(0, 0);
    }

    private void setTime(int seconds) {
        timeTextView.setText(seconds + "s");
    }

    private void generateQuestion() {
        int a = random.nextInt(31);
        int b = random.nextInt(31);
        questionTextView.setText(a + " + " + b);

        answers.clear();
        locationCorrectAnswer = random.nextInt(4);
        for (int i = 0; i < 4; i++) {
            if (i == locationCorrectAnswer) {
                answers.add(a + b);
            } else {
                int wrongAnswer = random.nextInt(61);
                while (wrongAnswer == a + b) {
                    wrongAnswer = random.nextInt(61);
                }
                answers.add(wrongAnswer);
            }
        }
        button0.setText(answers.get(0));
        button1.setText(answers.get(1));
        button2.setText(answers.get(2));
        button3.setText(answers.get(3));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);
        timeTextView = findViewById(R.id.timeTextView);
        questionTextView = findViewById(R.id.questionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        resultTextView = findViewById(R.id.resultTextView);

        answersLayout = findViewById(R.id.answersLayout);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
    }
}