package com.example.seventhapplication

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE: Int = 1218

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.gotokyo.org/jp/destinations/western-tokyo/shibuya/index.html")

        registerForContextMenu(webView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.top -> {
                webView.loadUrl("https://www.gotokyo.org/jp/destinations/western-tokyo/shibuya/index.html")
                return true
            }
            R.id.shibuya -> {
                webView.loadUrl("https://www.gotokyo.org/jp/destinations/western-tokyo/shibuya/index.html")
                return true
            }
            R.id.meijijingu -> {
                webView.loadUrl("https://www.gotokyo.org/jp/spot/76/index.html")
                return true
            }
            R.id.yoyogi -> {
                webView.loadUrl("https://www.gotokyo.org/jp/spot/21/index.html")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.camera -> {
                checkPermission()
            }
            R.id.share -> {
                val text = "共有する！"
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, text)
                val chooser = Intent.createChooser(intent, null)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(chooser)
                }
                return true
            }
        }
        return super.onContextItemSelected(item)
    }


    private fun onCameraSelected() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onCameraSelected()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        // if permission is not granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            // ask for permission (permission is not granted yet)
            // return true if the user previously denied the request
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {
                // show dialog to ask for permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CODE
                )
            }
        } else {
            // Permission has already been granted
            onCameraSelected()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            // If request is cancelled, the result arrays are empty.
            REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCameraSelected()
                } else {
                    // permission denied
                    return
                }
                onCameraSelected()
            }
            // received incorrect request code
            else -> {
                return
            }
        }
    }
}