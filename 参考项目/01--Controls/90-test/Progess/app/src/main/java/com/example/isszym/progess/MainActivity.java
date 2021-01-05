package com.example.isszym.progess;

import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
         btn.setOnClickListener(new View.OnClickListener(){
                 @Override
                 public void onClick(View v){
                         RatingBar pg=(RatingBar)findViewById(R.id.ratingBar);
                         Toast.makeText(MainActivity.this,
                                 "Button3 is clicked!"+pg.getRating(),
                         Toast.LENGTH_SHORT).show();
                 }
         });
    }

}
