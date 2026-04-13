package com.example.learningportalapp

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.os.Bundle
import android.webkit.*
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var etUrl: EditText

    private val homeUrl = "https://www.google.com"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //  Initialize views
        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        etUrl = findViewById(R.id.etUrl)

        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnForward = findViewById<Button>(R.id.btnForward)
        val btnRefresh = findViewById<Button>(R.id.btnRefresh)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnGo = findViewById<Button>(R.id.btnGo)

        val btnGoogle = findViewById<Button>(R.id.btnGoogle)
        val btnYouTube = findViewById<Button>(R.id.btnYouTube)
        val btnWiki = findViewById<Button>(R.id.btnWiki)
        val btnKhan = findViewById<Button>(R.id.btnKhan)
        val btnUniversity = findViewById<Button>(R.id.btnUniversity)

        //  WebView Settings
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        //  WebViewClient
        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = ProgressBar.VISIBLE
                etUrl.setText(url)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = ProgressBar.GONE
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)

                if (!isConnected()) {
                    webView.loadUrl("file:///android_asset/offline.html")
                }
            }
        }

        //  WebChromeClient
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.progress = newProgress
            }
        }

        //  Load Home
        webView.loadUrl(homeUrl)

        //  Back Button (Modern API)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    finish()
                }
            }
        })


        //  EVENT HANDLING


        btnBack.setOnClickListener {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                Toast.makeText(this, "No more history", Toast.LENGTH_SHORT).show()
            }
        }

        btnForward.setOnClickListener {
            if (webView.canGoForward()) {
                webView.goForward()
            }
        }

        btnRefresh.setOnClickListener {
            webView.reload()
        }

        btnHome.setOnClickListener {
            webView.loadUrl(homeUrl)
        }

        btnGo.setOnClickListener {
            loadUrlFromInput()
        }

        //  Shortcuts
        btnGoogle.setOnClickListener { webView.loadUrl("https://www.google.com") }
        btnYouTube.setOnClickListener { webView.loadUrl("https://www.youtube.com") }
        btnWiki.setOnClickListener { webView.loadUrl("https://www.wikipedia.org") }
        btnKhan.setOnClickListener { webView.loadUrl("https://www.khanacademy.org") }
        btnUniversity.setOnClickListener { webView.loadUrl("https://www.du.ac.bd") }
    }

    //  URL validation
    private fun loadUrlFromInput() {
        var url = etUrl.text.toString().trim()

        if (!url.startsWith("https://")) {
            url = "https://$url"
        }

        webView.loadUrl(url)
    }

    //  Internet check
    private fun isConnected(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetworkInfo
        return network != null && network.isConnected
    }
}