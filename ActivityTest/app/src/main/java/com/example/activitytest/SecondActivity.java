package com.example.activitytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendUDPRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendLoginRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL("192.168.2.104:8080");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendUDPRequest() throws IOException {

        new Thread(new Runnable(){
            @Override
            public void run() {
                DatagramSocket ds = null;
                byte[] password = "12345".getBytes();
                InetAddress inet = null;
                try {
                    inet = InetAddress.getByName("192.168.2.104");
                    DatagramPacket dp = new DatagramPacket(password, password.length, inet, 6999);
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
