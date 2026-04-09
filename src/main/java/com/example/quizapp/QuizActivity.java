package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    TextView tvQuestion;
    RadioGroup radioGroup;
    RadioButton option1, option2, option3, option4;
    Button btnSubmit;
    ProgressBar progressBar;

    TextView tvProgress;

    int currentQuestion = 0;
    int score = 0;

    String[] questions = {
            "Capital of Australia?",
            "2 + 2 = ?",
            "Color of sky?",
            "Android is?",
            "Java is?"
    };

    String[][] options = {
            {"Sydney", "Melbourne", "Canberra", "Perth"},
            {"3", "4", "5", "6"},
            {"Blue", "Green", "Red", "Yellow"},
            {"OS", "Game", "Language", "Browser"},
            {"Animal", "Language", "Car", "City"}
    };

    int[] correctAnswers = {2, 1, 0, 0, 1};

    boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuestion = findViewById(R.id.tvQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        btnSubmit = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressBar);
        tvProgress = findViewById(R.id.tvProgress);

        loadQuestion();

        btnSubmit.setOnClickListener(v -> {
            if (!answered) {
                checkAnswer();
            } else {
                nextQuestion();
            }
        });
    }

    // Load Question
    void loadQuestion() {
        answered = false;
        radioGroup.clearCheck();

        tvQuestion.setText(questions[currentQuestion]);

        option1.setText(options[currentQuestion][0]);
        option2.setText(options[currentQuestion][1]);
        option3.setText(options[currentQuestion][2]);
        option4.setText(options[currentQuestion][3]);

        // Update ProgressBar
        progressBar.setProgress(currentQuestion + 1);

        // SHOW NUMBER
        tvProgress.setText("Question " + (currentQuestion + 1) + " of " + questions.length);
    }

    // Check Answer
    void checkAnswer() {
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == -1) return;

        answered = true;

        RadioButton selected = findViewById(selectedId);
        int selectedIndex = radioGroup.indexOfChild(selected);

        int correctIndex = correctAnswers[currentQuestion];

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton rb = (RadioButton) radioGroup.getChildAt(i);

            if (i == correctIndex) {
                rb.setBackgroundColor(Color.GREEN);
            } else if (i == selectedIndex) {
                rb.setBackgroundColor(Color.RED);
            }
        }

        if (selectedIndex == correctIndex) {
            score++;
        }

        btnSubmit.setText("Next");
    }

    // Next Question
    void nextQuestion() {
        currentQuestion++;

        if (currentQuestion < questions.length) {
            loadQuestion();
            btnSubmit.setText("Submit");

            // reset colors
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton rb = (RadioButton) radioGroup.getChildAt(i);
                rb.setBackgroundColor(Color.TRANSPARENT);
            }

        } else {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("SCORE", score);
            String name = getIntent().getStringExtra("USERNAME");
            intent.putExtra("USERNAME", name);
            startActivity(intent);
            finish();
        }
    }
}
