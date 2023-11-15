@file:Suppress("DEPRECATION")

package com.example.barcode

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import android.content.ClipboardManager
import android.content.ClipData
import android.content.Context
import android.widget.TextView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var clipboardManager : ClipboardManager
    private lateinit var outputbarcode : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnScan = findViewById<Button>(R.id.scan)
        outputbarcode = findViewById(R.id.hasil)

        btnScan.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled(false)
            scanner.initiateScan()
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode : Int, resultCode : Int, data: Intent?) {
        super.onActivityReenter(requestCode, data)

        if (resultCode == Activity.RESULT_OK) {

            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                val scannedText = result.contents
                // hasil
                outputbarcode.text = scannedText
                outputbarcode.setOnClickListener {

                    // isi

                    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("text", scannedText)
                    clipboardManager.setPrimaryClip(clipData)

                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data)


            }
        }
    }