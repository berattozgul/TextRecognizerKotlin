package com.example.textrecognizerkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class ScannedTextActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanned_text)

        imageView=findViewById(R.id.idIVQrcode)
        textView=findViewById(R.id.idTv)

        var bundle :Bundle ?=intent.extras
        var message = bundle!!.getString("value") // 1
        var strUser: String? = intent.getStringExtra("value") // 2

        textView.setText(strUser)
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(strUser, BarcodeFormat.QR_CODE, 512, 512)
        imageView.setImageBitmap(bitmap)
    }
}