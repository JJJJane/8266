package com.example.jyn.a8266;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static android.R.attr.data;


public class TcpClientConnector{
    private static TcpClientConnector mTcpClientConnector;
    private Socket mClient;
    private Thread mConnectThread;
    private OutputStream outputStream = null;

    public static TcpClientConnector getInstance() {
        if (mTcpClientConnector == null)
            mTcpClientConnector = new TcpClientConnector();
        return mTcpClientConnector;
    }


    public void creatConnect(final String mSerIP, final int mSerPort) {
        if (mConnectThread == null) {
            mConnectThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    connect(mSerIP, mSerPort);
                }
            });
            mConnectThread.start();
        }
    }

    /**
     * 与服务端进行连接
     *
     * @throws IOException
     */
    private void connect(String mSerIP, int mSerPort){
        if (mClient == null) {
            try {
                mClient = new Socket(mSerIP, mSerPort);
                outputStream = mClient.getOutputStream();
            } catch (Exception e) {
                Log.e("0","123");
            }
        }
        InputStream inputStream = null;
        try {
            inputStream = mClient.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            Log.e("999","123");
            while ((len = inputStream.read(buffer)) != -1) {
                Log.e("1","123");
                String data = new String(buffer, 0, len);
                Message message = new Message();
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("data", data);
                message.setData(bundle);
                MainActivity.handler.sendMessage(message);
                Log.e("2","123");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("xx","123");
        }
    }

    /**
     * 发送数据
     *
     * @param data 需要发送的内容
     */
    public void send(final String data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    outputStream.write(data.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 断开连接
     *
     * @throws IOException
     */
    public void disconnect() throws IOException {
        if (mClient != null) {
            mClient.close();
            mClient = null;
        }
    }
}
