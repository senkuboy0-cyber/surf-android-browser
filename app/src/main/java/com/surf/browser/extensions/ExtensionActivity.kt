package com.surf.browser.extensions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.surf.browser.databinding.ActivityExtensionBinding
import com.surf.browser.api.Extension

class ExtensionActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityExtensionBinding
    private lateinit var extensionAdapter: ExtensionAdapter
    private lateinit var extensionViewModel: ExtensionViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtensionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupRecyclerView()
        setupViewModel()
        setupListeners()
        loadExtensions()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(com.surf.browser.R.string.extensions)
    }
    
    private fun setupRecyclerView() {
        extensionAdapter = ExtensionAdapter(emptyList()) { extension ->
            handleExtensionClick(extension)
        }
        
        binding.extensionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ExtensionActivity)
            adapter = extensionAdapter
        }
    }
    
    private fun setupViewModel() {
        extensionViewModel = ViewModelProvider(this)[ExtensionViewModel::class.java]
    }
    
    private fun setupListeners() {
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchExtensions(query ?: "")
                return true
            }
            
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    loadExtensions()
                }
                return false
            }
        })
        
        binding.tabLayout.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab) {
                when (tab.position) {
                    0 -> loadExtensions()
                    1 -> loadInstalledExtensions()
                }
            }
            
            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab) {}
            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab) {}
        })
    }
    
    private fun loadExtensions() {
        extensionViewModel.searchExtensions("popular")
    }
    
    private fun loadInstalledExtensions() {
        extensionViewModel.getInstalledExtensions()
    }
    
    private fun searchExtensions(query: String) {
        extensionViewModel.searchExtensions(query)
    }
    
    private fun handleExtensionClick(extension: Extension) {
        showExtensionDetailsDialog(extension)
    }
    
    private fun showExtensionDetailsDialog(extension: Extension) {
        val dialog = ExtensionDetailsDialog.newInstance(extension)
        dialog.show(supportFragmentManager, "ExtensionDetailsDialog")
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}