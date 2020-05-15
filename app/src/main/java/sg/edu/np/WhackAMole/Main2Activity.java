package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    private Button buttons, mButton;
    private Integer advancedScore;
    private static final String TAG = "Week_4_Page_2";
    private TextView scoreChanger;
    CountDownTimer gameCountDown, setMoleTimer;
    private static final int[] BUTTON_IDS = {
            R.id.Button1, R.id.Button2, R.id.Button3, R.id.Button4, R.id.Button5, R.id.Button6, R.id.Button7, R.id.Button8, R.id.Button9,
    };

    /*
        Mr Low, it is actually possible to do my timing method for when every time the mole is clicked, a new mole will appear and the timer will reset
        I found out the actual issue is at line 44 where I change from:
        doCheck(buttons);
        to
        doCheck(buttons = findViewById(buttonID));
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent receiveData = getIntent();
        advancedScore = receiveData.getIntExtra("Score", 0);
        scoreChanger = findViewById(R.id.scoreChanger);
        scoreChanger.setText("" + advancedScore);

        Log.v(TAG, "Current User Score: " + advancedScore);

        for(final int buttonID : BUTTON_IDS){
            buttons = findViewById(buttonID);
            Log.i(TAG,"creating button" + buttonID);
            buttons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setMoleTimer.cancel();
                    doCheck(buttons = findViewById(buttonID));
                }
            });
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.v(TAG, "Page 2 starting GUI");
        readyTimer();
    }

    private void doCheck(Button checkButton)
    {
        String buttonText = checkButton.getText().toString();
        Log.d(TAG,"This button has: " + buttonText);
        if (buttonText == "*") {
            Log.v(TAG, "Hit, score added!");
            advancedScore = advancedScore + 1;
            scoreChanger.setText("" + advancedScore);
        }else {
            Log.v(TAG, "Missed, point deducted!");
            advancedScore -= 1;
            scoreChanger.setText("" + advancedScore);
        }
        setNewMole();
    }

    private void readyTimer(){
        Log.v(TAG,"readyTimer() entered");
        gameCountDown = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                Log.v(TAG,"Ready CountDown!" + l/1000);
                Toast.makeText(getApplicationContext(),"Get Ready in " + l/1000 + "seconds", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(),"GO!", Toast.LENGTH_SHORT).show();
                placeMoleTimer();
                gameCountDown.cancel();
            }
        };
        gameCountDown.start();
    }
    private void placeMoleTimer(){
        Log.v(TAG,"Starting infinite Loop");
        setMoleTimer = new CountDownTimer(1000, 100) {
            @Override
            public void onTick(long l) { }
            @Override
            public void onFinish() {
                Log.v(TAG, "New Mole Location!");
                setNewMole();
                //setMoleTimer.start();
            }
        };
        setMoleTimer.start();
    }

    public void setNewMole()
    {
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);

        Log.d(TAG,"the mole is at: " + randomLocation);
        for(int i = 0; i < BUTTON_IDS.length; i++){
            mButton = findViewById(BUTTON_IDS[i]);
            if (i == randomLocation){
                mButton.setText("*");
            }
            else{
                mButton.setText("O");
            }
        }
        placeMoleTimer();
    }
}

