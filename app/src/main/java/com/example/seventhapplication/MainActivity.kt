package com.example.seventhapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
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
}
