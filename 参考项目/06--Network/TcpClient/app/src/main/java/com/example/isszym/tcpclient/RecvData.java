package com.example.isszym.tcpclient;

import android.content.Context;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by isszym on 2018/7/23.
 */

class RecvData implements Runnable {
    private Socket socket;
    private Context context;
    private DataInputStream fromServer;

    public RecvData(Context context, Socket sock) {
        socket = sock;
        this.context=context;
        try {
            fromServer = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void run() {
        while (true) {
            try {
                String data = fromServer.readUTF();
                System.out.println(data);
             } catch (IOException ex) {
                System.err.println(ex);
                break;
            }
        }
    }
}