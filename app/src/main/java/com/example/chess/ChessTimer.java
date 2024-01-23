package com.example.chess;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Locale;

public class ChessTimer {
    private long timeLeftInMillis;
    public boolean addT;
    private CountDownTimer countDownTimer;
    private boolean isRunning;

    public ChessTimer(long timeInMillis, boolean addT) {
        this.timeLeftInMillis = timeInMillis;
        this.addT = addT;
    }

    public void addTime(long extraTimeInMillis, TextView textView) {
        if (isRunning) {
            // Cancel the current timer and add extra time
            countDownTimer.cancel();
            timeLeftInMillis += extraTimeInMillis;
            start(textView);
            updateCountDownText(textView);
        } else {
            // If the timer is not running, just add the time
            timeLeftInMillis += extraTimeInMillis;
            updateCountDownText(textView);
        }
    }

    public void start(TextView timerTextView) {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText(timerTextView);
            }

            public void onFinish() {
                isRunning = false;
                timerTextView.setText("Time's up!");
            }
        }.start();

        isRunning = true;
    }

    public void pause() {
        if (isRunning) {
            countDownTimer.cancel();
            isRunning = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void updateCountDownText(TextView timerTextView) {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(timeFormatted);
    }
}
