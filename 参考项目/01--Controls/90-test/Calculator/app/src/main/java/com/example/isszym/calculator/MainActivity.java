package com.example.isszym.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    String lastNumber="0";
    char op=' ';
    boolean opIsLastChar=false;
    String newNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView =(TextView)findViewById(R.id.textView);
        Button.OnClickListener listener=new Button.OnClickListener(){
            public void onClick(View v){
                String txt=((Button)v).getText().toString();
                char flag=txt.charAt(0);
                String curNumber=textView.getText().toString();
                if(flag=='←'){
                    if(!opIsLastChar){
                        String digits=textView.getText().toString();
                        if(digits.length()>1)
                            textView.setText(digits.substring(0,digits.length()-1));
                        else
                            textView.setText("0");
                    }
                }
                else if(flag=='C'){
                    if(txt.equals("CE")) {
                        if (!opIsLastChar) {
                            textView.setText("0");
                        }
                    }
                    else {
                        textView.setText("0");
                        lastNumber="0";
                        op=' ';
                        opIsLastChar=false;
                    };
                }
                else if(opIsLastChar && (flag>='0' && flag<='9' || flag=='.')){
                    textView.setText(""+flag);
                    opIsLastChar=false;
                }
                else if(flag>='1' && flag<='9'){
                    if(curNumber.equals("0"))
                        textView.setText(""+flag);
                    else
                        textView.setText(curNumber+flag);
                    opIsLastChar=false;
                }
                else if(flag=='0'){
                    if(!curNumber.equals("0"))
                        textView.setText(curNumber+flag);
                    opIsLastChar=false;
                }
                else if(flag=='.'){
                    if(curNumber.indexOf(".")<0){
                       textView.setText(curNumber+flag);
                    }
                    opIsLastChar=false;
                }else{
                    if(!opIsLastChar) {
                        double opnum1=Double.parseDouble("00"+lastNumber+(lastNumber.indexOf(".")>=0?"00":""));
                        double opnum2=Double.parseDouble("00"+curNumber+(curNumber.indexOf(".")>=0?"00":""));
                        switch(op){
                            case '＋':opnum1+=opnum2;break;
                            case '－':opnum1-=opnum2;break;
                            case '×':opnum1*=opnum2;break;
                            case '÷':opnum1/=opnum2;break;
                            default: opnum1=opnum2;
                        }
                        if((""+opnum1).endsWith(".0"))
                            textView.setText((""+opnum1).substring(0,(""+opnum1).length()-2));
                        else
                            textView.setText(""+opnum1);

                        lastNumber=""+opnum1;
                        if(flag=='=')
                            op=' ';
                        else {
                            op = flag;
                        }
                        opIsLastChar=true;
                    }else{
                        op = flag;
                        opIsLastChar=true;
                    }

                }
            }
        };

        Integer[] btnIds={  R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,
                            R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,
                            R.id.btn9,R.id.btn10,R.id.btn11,R.id.btn12,
                            R.id.btn13,R.id.btn14,R.id.btn15,R.id.btn16,
                            R.id.btn17,R.id.btn18,R.id.btn19,R.id.btn20
                          };
        for(int i=0;i<20;i++){
            ((Button)findViewById(btnIds[i])).setOnClickListener(listener);
        }
    }
}
