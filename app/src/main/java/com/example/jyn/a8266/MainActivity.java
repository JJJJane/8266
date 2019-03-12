package com.example.jyn.a8266;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TcpClientConnector connector;

    Button btn_begin;
    Button btn_send;
    Button btn_led_on;
    Button btn_led_off;
    Button btn_M_on;
    Button btn_M_off;
    Button btn_bef_on;
    Button btn_bef_off;
    Button btn_dz_on;
    Button btn_dz_off;
    Button btn_temp;

    static TextView tv_temp;

    EditText edit_text;
    static Context context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_begin = (Button) findViewById(R.id.btn_begin);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_led_on = (Button)findViewById(R.id.btn_led_on);
        btn_led_off = (Button)findViewById(R.id.btn_led_off);
        btn_M_on = (Button)findViewById(R.id.btn_M_on);
        btn_M_off = (Button)findViewById(R.id.btn_M_off);
        btn_bef_on = (Button)findViewById(R.id.btn_bef_on);
        btn_bef_off = (Button)findViewById(R.id.btn_bef_off);
        btn_dz_on = (Button)findViewById(R.id.btn_dz_on);
        btn_dz_off = (Button)findViewById(R.id.btn_dz_off);
        btn_temp = (Button)findViewById(R.id.btn_temp);

        edit_text = (EditText)findViewById(R.id.edit_text);

        tv_temp = (TextView)findViewById(R.id.tv_temp);

        btn_begin.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        btn_led_on.setOnClickListener(this);
        btn_led_off.setOnClickListener(this);
        btn_M_on.setOnClickListener(this);
        btn_M_off.setOnClickListener(this);
        btn_bef_on.setOnClickListener(this);
        btn_bef_off.setOnClickListener(this);
        btn_dz_on.setOnClickListener(this);
        btn_dz_off.setOnClickListener(this);
        btn_temp.setOnClickListener(this);
        context = this;

        connector = TcpClientConnector.getInstance();

        Toast.makeText(MainActivity.context, "先连接8266" + "\n" + "再按开始"+"\n" ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        String string = null;
        switch (view.getId())
        {
            case R.id.btn_begin:
                btn_begin.setEnabled(false);
                btn_send.setEnabled(true);
                btn_led_on.setEnabled(true);
                btn_led_off.setEnabled(true);
                btn_M_on.setEnabled(true);
                btn_M_off.setEnabled(true);
                btn_bef_on.setEnabled(true);
                btn_bef_off.setEnabled(true);
                btn_dz_on.setEnabled(true);
                btn_dz_off.setEnabled(true);
                btn_temp.setEnabled(true);
                edit_text.setEnabled(true);
                connector.creatConnect("192.168.4.1",333);
                break;
            case R.id.btn_send:
                string = edit_text.getText().toString();
                break;
                //测试10s开
            case R.id.btn_led_on:
                string = "7";
                break;
            case R.id.btn_led_off:
                string = "2";
                break;
            case R.id.btn_M_on:
                string = "3";
                break;
            case R.id.btn_M_off:
                string = "4";
                break;
            case R.id.btn_bef_on:
                string = "5";
                break;
            case R.id.btn_bef_off:
                string = "6";
                break;
                //23：00关灯
            case R.id.btn_dz_on:
//                SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
//                String currenttime = sdf.format(new Date());
//                int h=Integer.parseInt(currenttime.substring(0,1));
//                int m=Integer.parseInt(currenttime.substring(2,3));
//                int s=Integer.parseInt(currenttime.substring(4,5));
//                int temp=82800-(3600*h+60*m+s);
//                int t=temp>0?temp*1000:(temp*1000+86400000);
//                Timer timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        int y=0;
//                    }
//                },  t);
                int h=0,m=0,s=0;
                while(3600*h+60*m+s!=82800){
                    SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
                    String currenttime = sdf.format(new Date());
                    h=Integer.parseInt(currenttime.substring(0,1));
                    m=Integer.parseInt(currenttime.substring(2,3));
                    s=Integer.parseInt(currenttime.substring(4,5));
                }
                string = "6";
                break;
                //7：00开灯
            case R.id.btn_dz_off:
//                SimpleDateFormat sdf1 = new SimpleDateFormat("hhmmss");
//                String currenttime1 = sdf1.format(new Date());
//                int h1=Integer.parseInt(currenttime1.substring(0,1));
//                int m1=Integer.parseInt(currenttime1.substring(2,3));
//                int s1=Integer.parseInt(currenttime1.substring(4,5));
//                int temp1=25200-(3600*h1+60*m1+s1);
//                int t1=temp1>0?temp1*1000:(temp1*1000+86400000);
//                Timer timer2 = new Timer();
//                timer2.schedule(new TimerTask() {
//                    public void run() {
//                    int x=0;
//                    }
//                },  t1);
                int h1=0,m1=0,s1=0;
                while(3600*h1+60*m1+s1!=25200){
                    SimpleDateFormat sdf1 = new SimpleDateFormat("hhmmss");
                    String currenttime = sdf1.format(new Date());
                    h1=Integer.parseInt(currenttime.substring(0,1));
                    m1=Integer.parseInt(currenttime.substring(2,3));
                    s1=Integer.parseInt(currenttime.substring(4,5));
                }
                string = "5";
                break;
            case R.id.btn_temp:
                string = "9";
                break;
            default:
                break;
        }
        try {
            if(string != null){
                connector.send(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.e("3","123");
            Bundle bundle = msg.getData();
            String data = bundle.getString("data");
            switch (msg.what) {
                case 1:
                    tv_temp.setText("18B20当前温度：" + data + "摄氏度");
            }
        }
    };
}