package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnOn, btnOff;
    private TextView txtStatus;
    private final OkHttpClient client = new OkHttpClient();
    private final String WEMOS_IP = "192.168.4.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnOn = findViewById(R.id.btnOn);
        btnOff = findViewById(R.id.btnOff);
        txtStatus = findViewById(R.id.txtStatus);


        txtStatus.setText("Warning");


        setupNetworkBinding();

        btnOn.setOnClickListener(v -> sendCommand("on"));
        btnOff.setOnClickListener(v -> sendCommand("off"));
    }

    private void setupNetworkBinding() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        NetworkRequest request = builder.build();

        connManager.requestNetwork(request, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                connManager.bindProcessToNetwork(network);
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this,
                            "Connected to Wemos", Toast.LENGTH_SHORT).show();
                    txtStatus.setText("Connected to Wemos");
                });
            }
        });
    }

    private void sendCommand(String cmd) {
        new Thread(() -> {
            String url = "http://" + WEMOS_IP + "/" + cmd;
            Log.d("COMMAND", "Sending: " + url);

            Request request = new Request.Builder().url(url).build();

            try {
                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();

                // Update UI with response
                runOnUiThread(() -> {
                    txtStatus.setText("Light is: " + (cmd.equals("on") ? "ON" : "OFF") +
                            "\n(220V AC)");
                    Toast.makeText(MainActivity.this,
                            "Light turned " + cmd.toUpperCase(), Toast.LENGTH_SHORT).show();
                });

            } catch (IOException e) {
                runOnUiThread(() -> {
                    txtStatus.setText("Error: " + e.getMessage());
                    Toast.makeText(MainActivity.this,
                            "Failed to send command", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
}