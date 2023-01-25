package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private final String EDIT_TEXT_KEY = "EDIT_TEXT_KEY";
    private Button submitButton;
    private Button exitButton;
    private EditText editText;
    private TextView errMess;
    private String message;
    private ImageView img;
    private ImageAnimate imageAnimate;
    private boolean isImageAnimate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            //message = savedInstanceState.getString(EDIT_TEXT_KEY);
        }

        submitButton = findViewById(R.id.sendMessage);
        submitButton.setOnClickListener(this::sendMessage);

        exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(this::exit);

        exitButton = findViewById(R.id.rotateButton);
        exitButton.setOnClickListener(this::rotateImage);

        editText = findViewById(R.id.editTextTextPersonName);

        errMess = findViewById(R.id.errMess);

        img = findViewById(R.id.imageView2);
    }

    // This callback is called only when there is a saved instance that is previously saved by using
    // onSaveInstanceState(). We restore some state in onCreate(), while we can optionally restore
    // other state here, possibly usable after onStart() has completed.
    // The savedInstanceState Bundle is same as the one used in onCreate().
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        editText.setText(savedInstanceState.getString(EDIT_TEXT_KEY));
    }
    // invoked when the activity may be temporarily destroyed, save the instance state here

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(EDIT_TEXT_KEY, editText.getText().toString());

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    private void rotateImage(View view) {
        if (isImageAnimate) {
            if (imageAnimate != null) {
                imageAnimate.interrupt();
            }
            imageAnimate = new ImageAnimate(img);
            imageAnimate.start();
            isImageAnimate = false;

        } else{
            imageAnimate.setKeepRunning(false);
            img.setRotation(0);
            isImageAnimate = true;
            imageAnimate.interrupt();
        }
    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        message = editText.getText().toString();

        if (message.length() == 0) {
            String errString = getResources().getString(R.string.error_message) + ": " +  getResources().getString(R.string.error_empty_message);
            errMess.setText(errString);
            errMess.setVisibility(View.VISIBLE);
        } else {
            errMess.setVisibility(View.INVISIBLE);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }

    public void exit(View view) {
        finish();
    }
}

class ImageAnimate extends Thread {
    ImageView img;
    private boolean keepRunning = true;

    ImageAnimate(ImageView img) {
        this.img = img;
    }

    public void run() {
        int i = 0;
        while (keepRunning || i != 0) {
            img.setRotation(i);
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                System.out.println("Interrupted");
            }
            i += 10;
            if (i >= 360) i = 0;
        }
        img.setRotation(0);
    }

    public synchronized void setKeepRunning(boolean state)
    {
        keepRunning = state;
    }
}
