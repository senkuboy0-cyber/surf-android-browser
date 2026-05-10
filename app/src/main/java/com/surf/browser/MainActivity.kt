package com.surf.browser

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.surf.browser.bookmarks.BookmarksActivity
import com.surf.browser.extensions.ExtensionActivity
import com.surf.browser.history.HistoryActivity
import com.surf.browser.settings.SettingsActivity
import com.surf.browser.tab.TabManager
import com.surf.browser.utils.PreferenceManager
import com.surf.browser.viewmodel.BrowserViewModel
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView

class MainActivity : AppCompatActivity() {
    
    private lateinit var geckoView: GeckoView
    private lateinit var geckoSession: GeckoSession
    private lateinit var tabManager: TabManager
    private lateinit var browserViewModel: BrowserViewModel
    private lateinit var preferenceManager: PreferenceManager
    
    // UI components
    private lateinit var urlBar: EditText
    private lateinit var securityIndicator: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var backButton: ImageButton
    private lateinit var forwardButton: ImageButton
    private lateinit var refreshButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var newTabButton: ImageButton
    private lateinit var menuButton: ImageButton
    private lateinit var tabIndicator: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize components
        initializeComponents()
        
        // Setup ViewModel
        browserViewModel = ViewModelProvider(this)[BrowserViewModel::class.java]
        
        // Setup Tab Manager
        tabManager = TabManager(this)
        geckoSession = tabManager.getCurrentSession()
        
        // Setup GeckoView
        setupGeckoView()
        
        // Setup UI
        setupUI()
        
        // Setup listeners
        setupListeners()
        
        // Load home page
        loadHomePage()
    }
    
    private fun initializeComponents() {
        geckoView = findViewById(R.id.gecko_view)
        urlBar = findViewById(R.id.url_bar)
        securityIndicator = findViewById(R.id.security_indicator)
        progressBar = findViewById(R.id.progress_bar)
        backButton = findViewById(R.id.back_button)
        forwardButton = findViewById(R.id.forward_button)
        refreshButton = findViewById(R.id.refresh_button)
        homeButton = findViewById(R.id.home_button)
        newTabButton = findViewById(R.id.new_tab_button)
        menuButton = findViewById(R.id.menu_button)
        tabIndicator = findViewById(R.id.tab_indicator)
        
        preferenceManager = PreferenceManager(this)
    }
    
    private fun setupGeckoView() {
        geckoView.setSession(geckoSession)
        
        // Setup Gecko session settings
        val settings = geckoSession.settings
        settings.setJavaScriptEnabled(true)
        settings.setDomStorageEnabled(true)
        
        // Load Gecko session
        geckoSession.open(geckoView)
    }
    
    private fun setupUI() {
        // Update theme based on preferences
        updateTheme()
        
        // Update tab indicator
        updateTabIndicator()
        
        // Update navigation buttons
        updateNavigationButtons()
    }
    
    private fun setupListeners() {
        // URL bar listener
        urlBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.widget.TextView.IME_ACTION_SEARCH) {
                val url = urlBar.text.toString().trim()
                loadUrl(url)
                true
            } else {
                false
            }
        }
        
        // Navigation buttons
        backButton.setOnClickListener { navigateBack() }
        forwardButton.setOnClickListener { navigateForward() }
        refreshButton.setOnClickListener { refreshPage() }
        homeButton.setOnClickListener { loadHomePage() }
        newTabButton.setOnClickListener { openNewTab() }
        
        // Menu button
        menuButton.setOnClickListener { showMenu() }
    }
    
    private fun loadUrl(url: String) {
        if (url.isNotEmpty()) {
            val formattedUrl = if (url.startsWith("http://") || url.startsWith("https://")) {
                url
            } else {
                "https://$url"
            }
            
            geckoSession.loadUri(formattedUrl)
            urlBar.setText(formattedUrl)
        }
    }
    
    private fun navigateBack() {
        if (geckoSession.canGoBack()) {
            geckoSession.goBack()
        }
    }
    
    private fun navigateForward() {
        if (geckoSession.canGoForward()) {
            geckoSession.goForward()
        }
    }
    
    private fun refreshPage() {
        geckoSession.reload()
    }
    
    private fun loadHomePage() {
        val homeUrl = preferenceManager.getHomePage()
        loadUrl(homeUrl)
    }
    
    private fun openNewTab() {
        tabManager.addTab()
        updateTabIndicator()
        loadHomePage()
    }
    
    private fun showMenu() {
        val menuItems = arrayOf(
            getString(R.string.bookmarks),
            getString(R.string.history),
            getString(R.string.extensions),
            getString(R.string.settings)
        )
        
        MaterialAlertDialogBuilder(this)
            .setTitle("Menu")
            .setItems(menuItems) { dialog, which ->
                when (which) {
                    0 -> startActivity(Intent(this, BookmarksActivity::class.java))
                    1 -> startActivity(Intent(this, HistoryActivity::class.java))
                    2 -> startActivity(Intent(this, ExtensionActivity::class.java))
                    3 -> startActivity(Intent(this, SettingsActivity::class.java))
                }
            }
            .show()
    }
    
    private fun updateTheme() {
        when (preferenceManager.getThemeMode()) {
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "system" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
    
    private fun updateTabIndicator() {
        val tabCount = tabManager.getTabCount()
        tabIndicator.text = tabCount.toString()
        tabIndicator.visibility = if (tabCount > 1) View.VISIBLE else View.GONE
    }
    
    private fun updateNavigationButtons() {
        backButton.isEnabled = geckoSession.canGoBack()
        forwardButton.isEnabled = geckoSession.canGoForward()
    }
    
    private fun updateSecurityIndicator(isSecure: Boolean) {
        val color = if (isSecure) {
            ContextCompat.getColor(this, R.color.secure_green)
        } else {
            ContextCompat.getColor(this, R.color.insecure_red)
        }
        securityIndicator.setColorFilter(color)
    }
    
    override fun onBackPressed() {
        if (geckoSession.canGoBack()) {
            geckoSession.goBack()
        } else {
            super.onBackPressed()
        }
    }
    
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Handle configuration changes like screen rotation
    }
    
    override fun onDestroy() {
        super.onDestroy()
        geckoSession.close()
    }
}