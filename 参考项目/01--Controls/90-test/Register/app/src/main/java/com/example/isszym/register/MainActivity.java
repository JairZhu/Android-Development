package com.example.isszym.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] list = getResources().getStringArray(R.array.school);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.custom_item_list_1,list);
        Spinner lv = (Spinner) findViewById(R.id.spinnerSchool);
        lv.setAdapter(adapter);
        Button btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View vw){
                StringBuilder res= new StringBuilder("");
                EditText userName=(EditText)findViewById(R.id.etUserName);
                res.append("用户名："+userName.getText()+"\r\n");
                EditText pass=(EditText)findViewById(R.id.etPassword);
                res.append("密码：" + pass.getText()+"\r\n");

                String hobby="";
                CheckBox chk=(CheckBox)findViewById(R.id.checkBoxSport);
                hobby=hobby + (hobby.isEmpty()?"":",")+(chk.isChecked()?"体育":"");
                chk=(CheckBox)findViewById(R.id.checkBoxMusic);
                hobby=hobby + (hobby.isEmpty()?"":",")+(chk.isChecked()?"音乐":"");
                chk=(CheckBox)findViewById(R.id.checkBoxPainting);
                hobby=hobby + (hobby.isEmpty()?"":",")+(chk.isChecked()?"绘画":"");
                res.append("爱好："+(hobby.isEmpty()?"无":hobby)+"\r\n");

                RadioGroup radiogroup =(RadioGroup)findViewById(R.id.radioGroup);
                RadioButton rb =  (RadioButton) findViewById(radiogroup.getCheckedRadioButtonId());
                res.append("年级："+((rb==null)?"":rb.getText().toString())+"\r\n");

                Spinner school = (Spinner)findViewById(R.id.spinnerSchool);
                res.append("学院："+school.getSelectedItem().toString()+"\r\n");

                Switch officer = (Switch)findViewById(R.id.switchOfficer);
                res.append("全日制学生："+(officer.isChecked()?"是":"否")+"\r\n");
                Toast.makeText(MainActivity.this,res.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
