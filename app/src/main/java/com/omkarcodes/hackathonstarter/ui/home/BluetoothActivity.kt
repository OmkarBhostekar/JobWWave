package com.omkarcodes.hackathonstarter.ui.home

import android.bluetooth.BluetoothDevice
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluetoothscanning.BluetoothConfig
import com.bluetoothscanning.IDetected
import com.omkarcodes.hackathonstarter.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BluetoothActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)


        BluetoothConfig.with(this)
            .setBackgroundColor(Color.parseColor("#1E90FF"))
            .setPulseColor(Color.parseColor("#ffffff"))
            .setListener(object : IDetected {
                override fun onSelectedDevice(device: BluetoothDevice?) {

                }
            })
            .start();
    }
}
