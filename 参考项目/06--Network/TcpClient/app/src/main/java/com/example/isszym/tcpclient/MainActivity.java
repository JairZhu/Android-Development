package com.example.isszym.tcpclient;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    TcpSocket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        socket = new TcpSocket(MainActivity.this, "192.168.1.188", 8000);
        final EditText text=(EditText)findViewById(R.id.editText);

        Button btn=(Button)findViewById(R.id.btnSend);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                socket.sendString(text.getText().toString());
            }
        });
    }


}
