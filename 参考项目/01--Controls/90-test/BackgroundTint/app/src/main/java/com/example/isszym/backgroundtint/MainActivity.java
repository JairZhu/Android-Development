package com.example.isszym.backgroundtint;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //final AppCompatImageButton imageButton = (AppCompatImageButton)findViewById(R.id.imageButton);
       /* imageButton.setSupportBackgroundTintList(new ColorStateList(
                new int[][]{new int[]{android.R.attr.state_selected},
                        new int[]{}
                },
                new int[]{Color.GREEN, Color.BLUE}
        ));*/
        //imageButton.setSupportBackgroundTintMode(PorterDuff.Mode.SRC_OVER);

       /* imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButton.setSelected(!imageButton.isSelected());
            }
        });*/
    }
}
