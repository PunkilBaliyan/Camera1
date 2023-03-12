@file:Suppress("DEPRECATION")

package com.example.camera1

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Insert


class MainActivity : AppCompatActivity() {
    lateinit var imageView : ImageView
    var photo: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),100)
        }
        val captureButton: Button = findViewById(R.id.button)
        val shareButton: Button = findViewById(R.id.button2)
        imageView = findViewById(R.id.imageView)
        shareButton.setOnClickListener{


             val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/jpeg"
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "captured image")
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            val path = MediaStore.Images.Media.insertImage(contentResolver,photo,"Image Description",null)
            val uri = Uri.parse(path)
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(shareIntent,"share image"))


        }
        captureButton.setOnClickListener{
         val capture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(capture, 200)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==200 && resultCode== Activity.RESULT_OK) {
          photo = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)
        }
    }
}