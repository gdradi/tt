package com.technogym.equipmenttest.myrun.testbtleuart;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technogym.sdk.fitnessmachineservice.FitnessMachineManager;
import com.technogym.sdk.technogymbtleuart.BLEUartConstants;
import com.technogym.sdk.technogymbtleuart.Exceptions.ExceptionCommandUart;
import com.technogym.sdk.technogymbtleuart.characteristics.UartRxCharacteristic;
import com.technogym.sdk.technogymbtleuart.model.CommandModel;
import com.technogym.sdk.technogymbtleuart.model.CommandType;
import com.technogym.equipmenttest.myrun.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import technogym.testbtleuart.COMMANDSKt;

/**
 * Created by sventurini on 01/08/16.
 */
public class UartCommandActivity extends Activity {

    private FitnessMachineManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = FitnessMachineManager.getInstance(this);
        setContentView(R.layout.activity_uart_command);

        final EditText edtCmd = (EditText) findViewById(R.id.edtCmd);

        findViewById(R.id.btnSendCmd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rawCmd = edtCmd.getText().toString();
                sendCommand(rawCmd);
            }
        });

        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutButtons);
        for (final String command : COMMANDSKt.getCommands()) {
            final Button button = new Button(this);
            button.setText(command);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendCommand(command);
                    edtCmd.setText("");
                }
            });
            layout.addView(button);
        }

        manager.addOnChangeCharacteristicCallback(new FitnessMachineManager.OnChangeCharacteristicCallback<UartRxCharacteristic>() {
            @Override
            public void onChange(UartRxCharacteristic characteristic) {
                CommandModel cmd = characteristic.getCommandModel();
                writeCmdRx(getRawData(cmd));
            }
        }, BLEUartConstants.Characteristics.UART_RX);

    }

    private void sendCommand(String rawCmd) {
        CommandModel cmd = new CommandModel();
        cmd.setType(CommandType._TX);

        String[] rawCmdSplit = rawCmd.split(String.valueOf((char) CommandModel.Uart.TX_PARAM_SEPARATOR));
        if (rawCmdSplit.length > 1) {
            cmd.setCmd(rawCmdSplit[0]);
            List<String> params = new ArrayList<>(Arrays.asList(rawCmdSplit));
            params.remove(0);
            cmd.setParams(params);
            try {
                if (manager.writeUartCmd(cmd)) {
                    writeCmdTx(cmd.encode());
                } else {
                    Toast.makeText(UartCommandActivity.this, "Device not connectedAndReady", Toast.LENGTH_SHORT).show();
                }
            } catch (ExceptionCommandUart exceptionCommandUart) {
                exceptionCommandUart.printStackTrace();
                Toast.makeText(this, exceptionCommandUart.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else if (rawCmdSplit.length > 0) {
            cmd.setCmd(rawCmdSplit[0]);
            try {
                if (manager.writeUartCmd(cmd)) {
                    writeCmdTx(cmd.encode());
                } else {
                    Toast.makeText(UartCommandActivity.this, "Device not connectedAndReady", Toast.LENGTH_SHORT).show();
                }
            } catch (ExceptionCommandUart exceptionCommandUart) {
                exceptionCommandUart.printStackTrace();
                Toast.makeText(this, exceptionCommandUart.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }

    private String getRawData(CommandModel cmd) {
        StringBuilder raw = new StringBuilder();
        raw.append(cmd.getCmd());
        for (String param : cmd.getParams()) {
            if (CommandType._TX.equals(cmd.getType())) {
                raw.append((char) CommandModel.Uart.TX_PARAM_SEPARATOR).append(param);
            } else if (CommandType._RX.equals(cmd.getType())) {
                raw.append((char) CommandModel.Uart.RX_PARAM_SEPARATOR).append(param);
            }
        }
        return raw.toString();
    }

    private void writeCmdRx(String value) {
        TextView view = (TextView) findViewById(R.id.txtRxRecived);
        view.setText(String.format("%s%s\n\n", view.getText().toString(), value));
    }

    private void writeCmdTx(String value) {
        TextView view = (TextView) findViewById(R.id.txtTxRecived);
        view.setText(String.format("%s%s\n\n", view.getText().toString(), value));
    }
}
