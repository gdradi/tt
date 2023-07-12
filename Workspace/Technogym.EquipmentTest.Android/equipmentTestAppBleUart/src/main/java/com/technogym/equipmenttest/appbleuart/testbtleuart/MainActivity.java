package com.technogym.equipmenttest.appbleuart.testbtleuart;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.technogym.sdk.fitnessmachineservice.FitnessMachineManager;
import com.technogym.sdk.fitnessmachineservice.model.FitnessBtDevice;
import com.technogym.equipmenttest.appbleuart.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private FitnessMachineManager manager;
    private final List<BluetoothDevice> devices = new ArrayList<>();
    private static final String TAG = "testBT";
    private TextView txtStateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uart_main);
        manager = FitnessMachineManager.getInstance(this);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<String>());
        txtStateValue = (TextView) findViewById(R.id.txtStateValue);
        findViewById(R.id.btnUartCmd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UartCommandActivity.class));
            }
        });
        findViewById(R.id.btnRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
                devices.clear();
                manager.startScan();
            }
        });
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BluetoothDevice device = devices.get(i);
                txtStateValue.setText("Connecting to: " + device.getAddress() + " - name: ");
                manager.connect(device);
            }
        });

        listView.setAdapter(adapter);

        manager.addOnScanCallback(new FitnessMachineManager.OnScanCallback() {
            @Override
            public void onDeviceFound(FitnessBtDevice device) {
                if (!devices.contains(device.getDevice()) /*&& device.getRssi() > -50*/) {
                    Log.d(TAG, "Device found: " + device.getType().toString() + " - Mac: " + device.getDevice().getAddress());
                    devices.add(device.getDevice());
                    adapter.add(device.getDevice().getName() + " - " + device.getDevice().getAddress() + " - " + device.getType().toString());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onConnected(FitnessBtDevice device) {
                Log.d(TAG, "Connected to: " + device.getDevice().getAddress());
                txtStateValue.setText("Connected to: " + device.getDevice().getAddress() + " - name: " + device.getDevice().getName());
            }

            @Override
            public void onConfigured(FitnessBtDevice device) {
                Log.d(TAG, "Configured device: " + device.getDevice().getAddress());
                txtStateValue.setText("Configured: " + device.getDevice().getAddress() + " - name: " + device.getDevice().getName());
            }

            @Override
            public void onDisconnected(FitnessBtDevice device) {
                Log.d(TAG, "Disconnected");
                txtStateValue.setText("Disconnected");
            }

        });

    }

}
