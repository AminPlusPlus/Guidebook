package com.example.guidebook.views

import android.app.Activity
import androidx.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.guidebook.R

class WebViewActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: com.example.guidebook.databinding.ActivityWebviewBinding

    private var url: String? = null
    private var mActivity: Activity? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this@WebViewActivity
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview)

        if (intent.extras != null) {
            url = intent.getStringExtra("url")
            title = intent.getStringExtra("title")
        }

        setupClicks()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                //dialog.show();
                binding.progressBar.visibility = View.VISIBLE
            }


            override fun onPageFinished(view: WebView, url: String) {
                binding.progressBar.visibility = View.GONE

            }

        }

        binding.webView.loadUrl(url)
        binding.tvTitle.text = title


    }


    private fun setupClicks() {
        binding.icBack.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.icBack ->
                finish()
        }
    }
}
