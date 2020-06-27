package com.example.iswitchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.PrivateKey;

import Utils.DeviceUtils;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    public static Message msg;

    public static Bundle bdl;


    private static int port = 65500;
    EditText et;
    TextView text;
    private static Socket s;
//    private static ServerSocket ss;
//    private static InputStreamReader isr;
//    private static BufferedReader br;
    private static PrintWriter printWriter;

    String message = "";
    private static String ip = "192.168.2.104";
    private String loaclIp;

    public Handler myHandle = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 0x11){
                Bundle bundle = msg.getData();
                text.append(bundle.getString("msg") + "\n");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msg = new Message();
        bdl = new Bundle();

        setContentView(R.layout.activity_main);
        et = (EditText)findViewById(R.id.editText);
        text = (TextView)findViewById(R.id.response_text);

        Button btnLogin = (Button)findViewById(R.id.btnLogin);

        loaclIp = DeviceUtils.getLocalIpAddress(MainActivity.this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendText(v);
            }
        });

        Button btnUdp = (Button)findViewById(R.id.btnUDP);
        btnUdp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    sendUDPRequest(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        receiveUDP();
    }

    public void sendText(View v){
        message = et.getText().toString();
        myTask mt = new myTask();
        mt.execute();
        Toast.makeText(getApplicationContext(), "Data sent", Toast.LENGTH_SHORT).show();
    }

    class myTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            try {
                s = new Socket(ip, port);
                printWriter = new PrintWriter(s.getOutputStream());
                printWriter.write(message);
                printWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(s != null){
                        s.close();
                    }
                    if(printWriter != null){
                        printWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }


    // 发送消息给服务器
    public void sendUDPRequest(View v) throws IOException {
        message = et.getText().toString();
        new Thread(new Runnable(){
            @Override
            public void run() {
                DatagramSocket ds = null;
                byte[] data = message.getBytes();
                InetAddress inet = null;
                try {
                    inet = InetAddress.getByName(ip);
                    DatagramPacket dp = new DatagramPacket(data, data.length, inet, port);
                    ds = new DatagramSocket(6000);
                    ds.send(dp);
                    message = "client [ " +loaclIp + ": " + port + " ] send: " + message;
                    msg.what = 0x11;
                    bdl.clear();
                    bdl.putString("msg", message);
                    msg.setData(bdl);
                } catch (UnknownHostException | SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (ds != null){
                        ds.close();
                    }
                }
            }
        }).start();
    }

    public void receiveUDP(){
        Toast.makeText(MainActivity.this, "start to receive server msg", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable(){
            @Override
            public void run() {


                DatagramSocket ds = null;

                InetAddress inet = null;
                DatagramPacket dp = null;
                byte[] data = new byte[1024 * 10];



                try {
                    dp = new DatagramPacket(data, data.length);
                    ds = new DatagramSocket(port+1);
                    while (true) {
                        data = new byte[1024 * 10];
                        ds.receive(dp);
                        String ip = dp.getAddress().getHostAddress();
                        int sendPort = dp.getPort();
                        int length = dp.getLength();
                        String receiveMsg = "server [ " +ip + ": " + port + " ] send: " +
                                new String(data,0,length);
//                        System.out.println();
                        Log.e(TAG, receiveMsg);
                        msg.what = 0x11;
                        bdl.clear();
                        bdl.putString("msg", receiveMsg);
                        msg.setData(bdl);
                    }




                } catch (UnknownHostException | SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (ds != null){
                        ds.close();
                    }
                }
            }
        }).start();
    }


}