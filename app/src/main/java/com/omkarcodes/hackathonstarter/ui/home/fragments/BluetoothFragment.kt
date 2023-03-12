package com.omkarcodes.hackathonstarter.ui.home.fragments

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import c.tlgbltcn.library.BluetoothHelper
import c.tlgbltcn.library.BluetoothHelperListener
import com.bluetoothscanning.BluetoothConfig
import com.bluetoothscanning.IDetected
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.common.Resource
import com.omkarcodes.hackathonstarter.databinding.FragmentBluetoothBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentHomeBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentProfileBinding
import com.omkarcodes.hackathonstarter.ui.home.HomeFragmentDirections
import com.omkarcodes.hackathonstarter.ui.home.HomeViewModel
import com.omkarcodes.hackathonstarter.ui.usp.adapters.ChipsAdapter
import com.pluto.plugins.logger.PlutoLog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BluetoothFragment : Fragment(R.layout.fragment_bluetooth){

    private var _binding: FragmentBluetoothBinding? = null
    private val binding: FragmentBluetoothBinding
        get() = _binding!!
    @Inject
    lateinit var pref: SharedPreferences
    private val viewModel: HomeViewModel by viewModels()
    private var bluetoothHelper: BluetoothHelper? = null
    val list = HashSet<Pair<String,String>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBluetoothBinding.bind(view)

        binding.apply {



//            activity?.let { activity ->
//                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
//                        Log.d("bl","requesting pormission")
//                        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.BLUETOOTH_CONNECT), 2)
//                        return
//                    }else setupBluetoothHelper()
//                }else setupBluetoothHelper()
//            }


        }
    }

    private fun setupBluetoothHelper() {
        Log.d("bl","setup b")
        binding?.apply {
            bluetoothHelper = context?.let {
                BluetoothHelper(it,object : BluetoothHelperListener {
                    override fun getBluetoothDeviceList(device: BluetoothDevice?) {
                        if (ActivityCompat.checkSelfPermission(
                                it,
                                Manifest.permission.BLUETOOTH_CONNECT
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return
                        }
                        list.add(Pair(device!!.name, device!!.address))
                        Log.d("bl",device?.name.toString())
                    }

                    override fun onDisabledBluetooh() {

                    }

                    override fun onEnabledBluetooth() {

                    }

                    override fun onFinishDiscovery() {

                    }

                    override fun onStartDiscovery() {

                    }
                })
            }

            if(!bluetoothHelper!!.isBluetoothEnabled()) bluetoothHelper?.enableBluetooth()
            bluetoothHelper?.startDiscovery()
        }
    }

    override fun onResume() {
        super.onResume()
        bluetoothHelper?.registerBluetoothStateChanged()
    }

    override fun onPause() {
        super.onPause()
        bluetoothHelper?.unregisterBluetoothStateChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}