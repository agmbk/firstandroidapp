package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayMessageActivity extends AppCompatActivity {
    private Button submitButton;
    private boolean isImageShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

        submitButton = (Button) findViewById(R.id.goBack);
        submitButton.setOnClickListener(this::goBack);

        // submitButton = (Button) findViewById(R.id.showImage);
        // submitButton.setOnClickListener(this::goBack);
    }

    /**
     * Called when the user taps the Send button
     */
    public void goBack(View view) {
        finish();
    }

    /**
     * Called when the user taps the Send button
     */
    public void showImage(View view) {
        isImageShown = !isImageShown;
        ImageView img = (ImageView) findViewById(R.id.imageView3);
        img.setVisibility(isImageShown ? View.VISIBLE : View.INVISIBLE);
    }
}