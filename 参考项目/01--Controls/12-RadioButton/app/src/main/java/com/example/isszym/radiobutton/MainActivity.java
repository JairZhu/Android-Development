package com.example.isszym.radiobutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup radiogroup;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.button);
        radiogroup=(RadioGroup)findViewById(R.id.radiogroup1);

        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View vw){
                RadioButton rb = (RadioButton) findViewById(radiogroup.getCheckedRadioButtonId());
                DisplayToast(rb.getText().toString());
            }
        });
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            RadioButton rb = (RadioButton) findViewById(checkedId);
                            DisplayToast(rb.getText().toString());
                        }
                    });
    }
    public void DisplayToast(String str){
        Toast toast= Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,220);
        toast.show();
    }
}
