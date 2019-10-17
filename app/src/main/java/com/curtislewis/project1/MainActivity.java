package com.curtislewis.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;
import android.widget.CalendarView;

import com.google.android.material.snackbar.Snackbar;

import org.mariuszgromada.math.mxparser.Expression;

import java.lang.reflect.Array;


public class MainActivity extends AppCompatActivity {
    //number for identification of this activity for image
    final int CallerCode = 110;

    //image object
    ImageView pictureView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //CODE FOR CALENDAR VIEW
        CalendarView cv = (CalendarView) findViewById(R.id.calendarView);
        final TextView textView = (TextView) findViewById(R.id.textDate);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String date = "" + month + "/" + day + "/" + year;
                textView.setText(date);

            }
        });

        //CODE FOR THE CALCULATOR VIEWS TO MAKE IT FUNCTIONAL BELOW
        //array created to loop through linking onclick method to buttons

        int[] buttonIds = new int[]{
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonAdd, R.id.buttonSub, R.id.buttonMul, R.id.buttonDiv,
                R.id.buttonOp, R.id.buttonCp, R.id.buttonP, R.id.buttonC, R.id.buttonEq
        };

        //use the array of buttons to link button ids for onclick method
        for (int id : buttonIds) {

            if (id == R.id.buttonC) {

                final Button buttonC = findViewById(R.id.buttonC);
                buttonC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // links object to textview layout in xml file
                        final TextView text = (TextView) findViewById(R.id.CalcText);

                        //gets the current text/data from the calctext xml
                        String content = text.getText().toString();

                        //get the text from the android: text in xml file
                        String empty = "";

                        //adds the pressed button to the textfield
                        text.setText(empty);

                    }
                });
            } else if (id == R.id.buttonEq) {

                final Button buttonEq = findViewById(R.id.buttonEq);
                buttonEq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // links object to textview layout in xml file
                        final TextView text = (TextView) findViewById(R.id.CalcText);

                        //gets the current text/data from the calctext xml
                        String mathProb = text.getText().toString();

                        //creates object from org file to eventually use calculation with mathprob as its passed value
                        Expression expression = new Expression(mathProb);

                        //creates a double variable because that is what the method will return
                        Double result = expression.calculate();

                        //shows the results to the textfield
                        text.setText("" + result);

                    }
                });


            } else {

                //use final accessor or make variable global to make compiler happy
                final Button button = findViewById(id);

                //sets onclick listener to each button in the loop
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // links object to textview layout in xml file
                        final TextView text = (TextView) findViewById(R.id.CalcText);

                        //gets the current text/data from the calctext xml
                        String content = text.getText().toString();

                        //get the text from the android: text in xml file
                        String num = button.getText().toString();

                        //adds the pressed button to the textfield
                        text.setText(content + num);
                    }

                });
            }

        }



        //links button to camera view xml
        Button button = findViewById(R.id.SnapButton);

        //links image to area of camera view xml file to be displayed
        pictureView = findViewById(R.id.ImageView1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //line says i wan this object to use the camera provided by hardware
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //starts the activity to take picture and return it to this app as the caller
                startActivityForResult(intent, CallerCode);
            }
        });

        //create connection to the user interface

        final WebView webView = findViewById(R.id.webView1);

        Button button1 = findViewById(R.id.GoButton);

        final EditText editText = findViewById(R.id.editText1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editText.getText().toString();
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(url);


            }
        });



    }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

                //if request code and Caller of Intent matches
                if (requestCode == CallerCode) {

                    //if result successful retrieve image
                    if (resultCode == RESULT_OK) {
                        Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                        pictureView.setImageBitmap(bitmap);

                    } else if (resultCode == RESULT_CANCELED) {
                        View parentView = pictureView.getRootView();
                        Snackbar.make(parentView, "snap cancelled.", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

    }









