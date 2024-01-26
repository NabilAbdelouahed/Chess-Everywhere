package com.example.chess;

import static android.content.Intent.getIntent;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.Locale;

public class ChessTimer {
    private long timeLeftInMillis;
    public boolean addT;
    private CountDownTimer countDownTimer;
    private boolean isRunning;
    private TimerGame timerGame;


    public ChessTimer(long timeInMillis, boolean addT, TimerGame timerGame) {
        this.timeLeftInMillis = timeInMillis;
        this.addT = addT;
        this.timerGame = timerGame;
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
                if (timerGame.currentTurn.isWhiteSide()){
                    timerGame.status = GameStatus.TIMEOUT_BLACK_WIN;
                    timerGame.showEndGameDialogTimeUp("Black wins by time");
                }
                else{
                    timerGame.status = GameStatus.TIMEOUT_WHITE_WIN;
                    timerGame.showEndGameDialogTimeUp("White wins by time");
                }
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
