package com.surf.browser.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.surf.browser.databinding.ActivityHistoryBinding
import com.surf.browser.database.entities.HistoryItem

class HistoryActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyViewModel: HistoryViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupRecyclerView()
        setupViewModel()
        setupListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(com.surf.browser.R.string.history)
    }
    
    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter(emptyList()) { historyItem ->
            handleHistoryClick(historyItem)
        }
        
        binding.historyRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = historyAdapter
        }
    }
    
    private fun setupViewModel() {
        historyViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        
        historyViewModel.allHistory.observe(this) { historyItems ->
            historyAdapter.updateHistory(historyItems)
        }
    }
    
    private fun setupListeners() {
        binding.fabClearHistory.setOnClickListener {
            showClearHistoryDialog()
        }
        
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            
            override fun onQueryTextChange(newText: String?): Boolean {
                historyViewModel.searchHistory(newText ?: "")
                return true
            }
        })
    }
    
    private fun showClearHistoryDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Clear History")
            .setMessage("Are you sure you want to clear all browsing history?")
            .setPositiveButton("Clear") { dialog, _ ->
                historyViewModel.clearHistory()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    
    private fun handleHistoryClick(historyItem: HistoryItem) {
        // Open history item in browser
        // This would start MainActivity with the history URL
    }
    
    private fun handleHistoryLongClick(historyItem: HistoryItem) {
        showHistoryOptionsDialog(historyItem)
    }
    
    private fun showHistoryOptionsDialog(historyItem: HistoryItem) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("History Options")
            .setItems(arrayOf("Open in New Tab", "Copy Link", "Delete")) { dialog, which ->
                when (which) {
                    0 -> openInNewTab(historyItem)
                    1 -> copyLink(historyItem)
                    2 -> deleteHistoryItem(historyItem)
                }
            }
            .show()
    }
    
    private fun openInNewTab(historyItem: HistoryItem) {
        // Open in new tab logic
    }
    
    private fun copyLink(historyItem: HistoryItem) {
        val clipboard = getSystemService(android.content.ClipboardManager::class.java)
        val clip = android.content.ClipData.newPlainText("URL", historyItem.url)
        clipboard.setPrimaryClip(clip)
        
        android.widget.Toast.makeText(this, "Link copied to clipboard", android.widget.Toast.LENGTH_SHORT).show()
    }
    
    private fun deleteHistoryItem(historyItem: HistoryItem) {
        historyViewModel.deleteHistoryItem(historyItem)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}