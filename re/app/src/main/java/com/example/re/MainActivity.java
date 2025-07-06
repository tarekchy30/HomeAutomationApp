package com.example.re;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView tvStatus;
    private Button btnConnect, btnOn, btnOff;
    private final String WEMOS_SSID = "Wemos_Light";
    private final String WEMOS_PASSWORD = "12345678";
    private final String WEMOS_IP = "192.168.4.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        btnConnect = findViewById(R.id.btnConnect);
        btnOn = findViewById(R.id.btnOn);
        btnOff = findViewById(R.id.btnOff);

        btnConnect.setOnClickListener(v -> connectToWemos());
        btnOn.setOnClickListener(v -> sendCommand("on"));
        btnOff.setOnClickListener(v -> sendCommand("off"));
    }

    private void connectToWemos() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // For Android 10+, use WiFi Network Suggestions API instead
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + WEMOS_SSID + "\"";
        config.preSharedKey = "\"" + WEMOS_PASSWORD + "\"";

        int netId = wifiManager.addNetwork(config);
        wifiManager.enableNetwork(netId, true);

        tvStatus.setText("Connecting...");
        new CheckConnectionTask().execute();
    }

    private void sendCommand(String command) {
        new HttpRequestTask().execute(command);
    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String command = params[0];
            try {
                URL url = new URL("http://" + WEMOS_IP + "/" + command);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.contains("ON")) {
                tvStatus.setText("Light: ON");
            } else if (result.contains("OFF")) {
                tvStatus.setText("Light: OFF");
            } else if (result.startsWith("Error")) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class CheckConnectionTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL("http://" + WEMOS_IP + "/status");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(2000);
                return connection.getResponseCode() == 200;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isConnected) {
            if (isConnected) {
                tvStatus.setText("Connected to Wemos!");
                btnOn.setEnabled(true);
                btnOff.setEnabled(true);
            } else {
                tvStatus.setText("Connection failed");
                Toast.makeText(MainActivity.this,
                        "Connect to Wemos_Light WiFi manually", Toast.LENGTH_LONG).show();
            }
        }
    }
}