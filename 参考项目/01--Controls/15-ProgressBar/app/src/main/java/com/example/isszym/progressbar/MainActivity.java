package com.example.isszym.progressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ProgressBar pb1= (ProgressBar)findViewById(R.id.progressBar4);
                ProgressBar pb2= (ProgressBar)findViewById(R.id.progressBar6);
                CirclePgBar pb3= (CirclePgBar)findViewById(R.id.progressBar10);
                if(pb1.getProgress()>=pb1.getMax())
                    pb1.setProgress(0);
                else
                   pb1.setProgress(pb1.getProgress()+10);
                if(pb2.getProgress()>=pb2.getMax())
                    pb2.setProgress(0);
                else
                    pb2.setProgress(pb2.getProgress()+10);
                if(pb3.getProgress()>=pb3.getMax())
                    pb3.setProgress(0);
                else
                    pb3.setProgress(pb3.getProgress()+10);
            }
        });
    }
}
