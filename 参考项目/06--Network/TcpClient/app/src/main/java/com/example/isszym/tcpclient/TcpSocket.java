package com.example.isszym.tcpclient;

import android.content.Context;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by isszym on 2018/8/2.
 */

public class TcpSocket {
    DataOutputStream toServer;
    Context context;
    TcpSocket(Context context, String host, int port){
        this.context=context;
        try {
            Socket socket = new Socket(host, port);
            toServer = new DataOutputStream(socket.getOutputStream());
            Thread thread = new Thread(new RecvData(context, socket));
            thread.start();
        } catch (Exception ex) {
            System.out.println("###"+ex.getMessage());
            ex.printStackTrace();

        }
    }

    void sendString(String msg){
        try {
            toServer.writeUTF(msg);
        }
        catch (Exception ex) {
            Toast.makeText(context,"连接错误！",Toast.LENGTH_SHORT).show();
            System.out.println(ex.getMessage());
        }
    }
}
