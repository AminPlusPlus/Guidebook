package com.example.guidebook.views

import android.app.Activity
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
            /*  override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                  val builder = AlertDialog.Builder(mActivity!!)
                  var message = "SSL Certificate error."
                  when (error.primaryError) {
                      SslError.SSL_UNTRUSTED -> message = "The certificate authority is not trusted."
                      SslError.SSL_EXPIRED -> message = "The certificate has expired."
                      SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch."
                      SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid."
                  }
                  message += " Do you want to continue anyway?"

                  builder.setTitle("SSL Certificate Error")
                  builder.setMessage(message)
                  builder.setPositiveButton(
                      "continue"
                  ) { dialog, which -> handler.proceed() }
                  builder.setNegativeButton("cancel") { dialog, which ->
                      handler.cancel()
                      //finish();
                  }
                  val dialog = builder.create()
                  dialog.show()
              }*/

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                //Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
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
