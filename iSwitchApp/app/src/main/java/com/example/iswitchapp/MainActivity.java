package com.example.iswitchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private static int port = 65500;
    EditText et;
    private static Socket s;
//    private static ServerSocket ss;
//    private static InputStreamReader isr;
//    private static BufferedReader br;
    private static PrintWriter printWriter;

    String message = "";
    private static String ip = "192.168.2.104";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText)findViewById(R.id.editText);

        Button btnLogin = (Button)findViewById(R.id.btnLogin);
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
                    ds = new DatagramSocket();
                    ds.send(dp);
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