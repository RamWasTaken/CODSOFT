package com.flashlight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.widget.Button
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    private lateinit var cameraManager: CameraManager
    private var cameraId: String? = null
    private var flashOn = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.flashlightButton)

        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager

        try {
            cameraId = cameraManager.cameraIdList[0]
        }
        catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        button.setOnClickListener {
            flash()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun flash() {
        if (flashOn) {
            flashOff()
        }
        else {
            flashOn()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun flashOn() {
        try {
            if (cameraId != null) {
                cameraManager.setTorchMode(cameraId!!, true)
                flashOn = true
            }
        }
        catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun flashOff() {
        try {
            if (cameraId != null) {
                cameraManager.setTorchMode(cameraId!!, false)
                flashOn = false
            }
        }
        catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDestroy() {
        super.onDestroy()
        flashOff()
    }
}