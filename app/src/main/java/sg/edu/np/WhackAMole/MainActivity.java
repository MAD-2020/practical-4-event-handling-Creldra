package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Week_4_Page_1" ;
    private Button button1;
    private Button button2;
    private Button button3;
    private TextView scoreChanger;
    private Integer count = 0;

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "Finished Pre-Initialisation!");

        button1 = findViewById(R.id.Button1);
        button2 = findViewById(R.id.Button2);
        button3 = findViewById(R.id.Button3);
        scoreChanger = findViewById(R.id.score);

        button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doCheck(button1);
                }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCheck(button2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doCheck(button3);
                }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(final Button checkButton) {
        if (checkButton.getText().toString() == "*"){
            count +=1 ;
            scoreChanger.setText("" + count);
            if(count % 10 == 0){
                nextLevelQuery();
            }
        }
        else{
            count -= 1;
            scoreChanger.setText("" + count);
        }
        onStart();
    }

    private void nextLevelQuery(){
        Log.v(TAG, "Advance option given to user!");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning! Insane Whack-A-Mole Incoming!");
        builder.setMessage("Would you like to advance to advanced mode?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");
                onStart();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void nextLevel(){
        Intent nextLevel = new Intent(MainActivity.this, Main2Activity.class);
        nextLevel.putExtra("Score", count);
        startActivity(nextLevel);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);

        if (randomLocation == 0){
            button1.setText("*");
            button2.setText("O");
            button3.setText("O");
        }
        else if (randomLocation == 1){
            button1.setText("O");
            button2.setText("*");
            button3.setText("O");
        }
        else if (randomLocation == 2){
            button1.setText("O");
            button2.setText("O");
            button3.setText("*");
        }
        else{
            scoreChanger.setText("Error with Setting a new Mole");
        }
    }
}