package com.example.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn;
    CheckBox chk[];
    RadioButton rdb[];
    EditText editText1, editText2;
    Switch switch1;
    Spinner spinner;
    String mstringarray[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = (EditText) findViewById(R.id.yhm_input);
        editText2 = (EditText) findViewById(R.id.mima_input);

        chk = new CheckBox[3];
        chk[0] = (CheckBox) findViewById(R.id.checkbox1);
        chk[1] = (CheckBox) findViewById(R.id.checkbox2);
        chk[2] = (CheckBox) findViewById(R.id.checkbox3);

        rdb = new RadioButton[2];
        rdb[0] = (RadioButton) findViewById(R.id.radiobutton1);
        rdb[1] = (RadioButton) findViewById(R.id.radiobutton2);

        spinner = (Spinner) findViewById(R.id.spinner);
        mstringarray = getResources().getStringArray(R.array.xueyuan);
        ArrayAdapter<String> madapter = new testarrayadapter(this, mstringarray);
        spinner.setAdapter(madapter);

        switch1 = (Switch) findViewById(R.id.switch1);

        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1 = editText1.getText().toString();
                String str2 = editText2.getText().toString();

                String checkbox_str = "";
                if (chk[0].isChecked())
                    checkbox_str += chk[0].getText() + ",";
                if (chk[1].isChecked())
                    checkbox_str += chk[1].getText() + ",";
                if (chk[2].isChecked())
                    checkbox_str += chk[2].getText() + ",";
                if (checkbox_str.isEmpty())
                    checkbox_str = "无";
                else
                    checkbox_str = checkbox_str.substring(0, checkbox_str.length() - 1);

                String nianji = "";
                if (rdb[0].isChecked())
                    nianji = rdb[0].getText().toString();
                else if (rdb[1].isChecked())
                    nianji = rdb[1].getText().toString();


                String xueyuan = (String) spinner.getSelectedItem();

                String output = "用户名：" + str1 + '\n' + "密码：" + str2 + '\n' + "爱好："
                        + checkbox_str + '\n' + "年级：" + nianji + '\n' + "学院：" + xueyuan + '\n'
                        + "全日制学生：";

                if (switch1.isChecked())
                    output += "是";
                else
                    output += "否";

                Toast toast = Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}