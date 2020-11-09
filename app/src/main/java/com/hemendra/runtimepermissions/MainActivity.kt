package com.hemendra.runtimepermissions

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
   lateinit var imgview:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var captureButton = findViewById<Button>(R.id.btn_capture)
        imgview = findViewById<ImageView>(R.id.imgview)

        captureButton.setOnClickListener {
            if(checkPermission())
            {
                takePicture()
            }
            else{

                requestPermission()
            }
        }


    }

    fun checkPermission():Boolean
    {
        return ContextCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission()
    {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE),100)
    }

    fun takePicture()
    {
        var  intent = Intent()
        intent.setAction("android.media.action.IMAGE_CAPTURE")
        startActivityForResult(intent,101)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==100&& grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            takePicture()
        }
        else{
            requestPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==101 && resultCode == Activity.RESULT_OK)
        {
            var bmp: Bitmap = data!!.extras!!.get("data") as Bitmap

                imgview.setImageBitmap(bmp)
        }
    }
}