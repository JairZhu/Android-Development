package com.example.calculator;

import android.icu.text.NumberFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button one, two, three, four, five, six, seven, eight, nine, zero, ce, c, backspace, div, mult, sub, add, equal, dot, sign;
    TextView input;
    double value = 0;
    String str, op = "";
    Boolean flag = false, disable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);
        ce = (Button) findViewById(R.id.ce);
        c = (Button) findViewById(R.id.c);
        backspace = (Button) findViewById(R.id.backspace);
        div = (Button) findViewById(R.id.division);
        mult = (Button) findViewById(R.id.multiply);
        sub = (Button) findViewById(R.id.subtraction);
        add = (Button) findViewById(R.id.addition);
        equal = (Button) findViewById(R.id.equal);
        dot = (Button) findViewById(R.id.dot);
        sign = (Button) findViewById(R.id.sign);
        input = (TextView) findViewById(R.id.yminput);

        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        ce.setOnClickListener(this);
        c.setOnClickListener(this);
        backspace.setOnClickListener(this);
        div.setOnClickListener(this);
        mult.setOnClickListener(this);
        sub.setOnClickListener(this);
        add.setOnClickListener(this);
        equal.setOnClickListener(this);
        dot.setOnClickListener(this);
        sign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        str = input.getText().toString();
        switch (v.getId()) {
            case R.id.zero:
                if (disable)
                    break;
                if (flag)
                    input.setText("0");
                if (!str.equals("0"))
                    input.setText(str + ((Button)v).getText());
                flag = false;
                break;
            case R.id.one:
            case R.id.two:
            case R.id.three:
            case R.id.four:
            case R.id.five:
            case R.id.six:
            case R.id.seven:
            case R.id.eight:
            case R.id.nine:
                if (disable)
                    break;
                if (flag)
                    input.setText(((Button)v).getText().toString());
                else if (str.equals("0"))
                    input.setText(((Button)v).getText().toString());
                else
                    input.setText(str + ((Button)v).getText());
                flag = false;
                break;
            case R.id.dot:
                if (disable)
                    break;
                if (flag)
                    input.setText("0.");
                if (!input.getText().toString().contains("."))
                    input.setText(str + ((Button)v).getText());
                flag = false;
                break;
            case R.id.addition:
            case R.id.subtraction:
            case R.id.multiply:
            case R.id.division:
                if (disable)
                    break;
                switch (op) {
                    case "＋":
                        value += Double.valueOf(str);
                        break;
                    case "－":
                        value -= Double.valueOf(str);
                        break;
                    case "×":
                        value *= Double.valueOf(str);
                        break;
                    case "÷":
                        value /= Double.valueOf(str);
                        break;
                    case "":
                        value = Double.valueOf(str);
                }
                input.setText(Double.toString(value));
                op = ((Button)v).getText().toString();
                flag = true;
                break;
            case R.id.ce:
                if (disable)
                    break;
                input.setText("0");
                break;
            case R.id.backspace:
                if (disable)
                    break;
                if (!str.equals("0"))
                    input.setText(str.substring(0, str.length() - 1));
                if (str.length() == 1)
                    input.setText("0");
                break;
            case R.id.equal:
                if (!flag && !disable) {
                    switch (op) {
                        case "＋":
                            value += Double.valueOf(str);
                            break;
                        case "－":
                            value -= Double.valueOf(str);
                            break;
                        case "×":
                            value *= Double.valueOf(str);
                            break;
                        case "÷":
                            value /= Double.valueOf(str);
                            break;
                    }
                    input.setText(Double.toString(value));
                    value = 0;
                    op = "";
                    flag = true;
                    disable = true;
                }
                break;
            case R.id.c:
                input.setText("0");
                value = 0;
                op = "";
                flag = false;
                disable = false;
                break;
            case R.id.sign:
                if (disable)
                    break;
                if (str.contains("-"))
                    input.setText(str.substring(1));
                else
                    input.setText("-" + str);
        }
    }
}
