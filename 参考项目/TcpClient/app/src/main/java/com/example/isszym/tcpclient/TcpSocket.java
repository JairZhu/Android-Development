package com.example.isszym.tcpclient;

import java.io.DataOutputStream;
import java.net.Socket;

class TcpSocket {
    private DataOutputStream toServer;
    public Socket socket;
    private boolean mConnected=false;

    boolean isConnected(){
        return mConnected;
    }

    boolean testConnection(String host, int port){
        boolean res = connect(host, port);
        if(res)close();
        return res;
    }

    boolean connect(String host, int port){
        try {
            socket = new Socket(host, port);
            if(socket!=null) {
                mConnected = true;
            }
            else {
                mConnected = false;
                return false;
            }
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    boolean sendString(String msg){
        if(socket==null)return false;
        try {
            toServer.writeUTF(msg);
            return true;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    void close(){
        try {
            socket.close();
            mConnected = false;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
