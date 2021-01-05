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
    private String serverIpAddr="192.168.1.188";
    private int port = 50000;
    private TcpSocket tcpSocket;
    Thread thread;
    RecvData recvData;
    private GameObjects gameObjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        tcpSocket = new TcpSocket();
        gameObjects = new GameObjects(this);

        Button btn=(Button)findViewById(R.id.btnSend);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tcpSocket.isConnected() || tcpSocket.connect(serverIpAddr, port)) {
                    if(recvData!=null)
                        recvData.isRunning = false;
                    recvData = new RecvData(tcpSocket.socket, gameObjects);
                    Thread thread = new Thread(recvData);
                    thread.start();
                    String data = JsonFunc.car2JSON(gameObjects.car);
                    if (tcpSocket.sendString(data)){
                        Toast.makeText(MainActivity.this, "发送成功：" + data, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        Button btnTest=(Button)findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tcpSocket.testConnection(serverIpAddr, 50000)) {
                    Toast.makeText(MainActivity.this,"测试成功！",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"测试失败！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        tcpSocket.close();
    }


}


