package com.surf.browser.bookmarks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.surf.browser.databinding.ActivityBookmarksBinding
import com.surf.browser.database.entities.Bookmark

class BookmarksActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityBookmarksBinding
    private lateinit var bookmarkAdapter: BookmarkAdapter
    private lateinit var bookmarkViewModel: BookmarkViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupRecyclerView()
        setupViewModel()
        setupListeners()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(com.surf.browser.R.string.bookmarks)
    }
    
    private fun setupRecyclerView() {
        bookmarkAdapter = BookmarkAdapter(emptyList()) { bookmark ->
            handleBookmarkClick(bookmark)
        }
        
        binding.bookmarksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@BookmarksActivity)
            adapter = bookmarkAdapter
        }
    }
    
    private fun setupViewModel() {
        bookmarkViewModel = ViewModelProvider(this)[BookmarkViewModel::class.java]
        
        bookmarkViewModel.allBookmarks.observe(this) { bookmarks ->
            bookmarkAdapter.updateBookmarks(bookmarks)
        }
    }
    
    private fun setupListeners() {
        binding.fabAddBookmark.setOnClickListener {
            showAddBookmarkDialog()
        }
        
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            
            override fun onQueryTextChange(newText: String?): Boolean {
                bookmarkViewModel.searchBookmarks(newText ?: "")
                return true
            }
        })
    }
    
    private fun showAddBookmarkDialog() {
        val dialog = AddBookmarkDialog.newInstance()
        dialog.show(supportFragmentManager, "AddBookmarkDialog")
    }
    
    private fun handleBookmarkClick(bookmark: Bookmark) {
        // Open bookmark in browser
        // This would start MainActivity with the bookmark URL
    }
    
    private fun handleBookmarkLongClick(bookmark: Bookmark) {
        showBookmarkOptionsDialog(bookmark)
    }
    
    private fun showBookmarkOptionsDialog(bookmark: Bookmark) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Bookmark Options")
            .setItems(arrayOf("Edit", "Delete", "Share")) { dialog, which ->
                when (which) {
                    0 -> editBookmark(bookmark)
                    1 -> deleteBookmark(bookmark)
                    2 -> shareBookmark(bookmark)
                }
            }
            .show()
    }
    
    private fun editBookmark(bookmark: Bookmark) {
        // Open edit dialog
    }
    
    private fun deleteBookmark(bookmark: Bookmark) {
        bookmarkViewModel.deleteBookmark(bookmark)
    }
    
    private fun shareBookmark(bookmark: Bookmark) {
        val shareIntent = android.content.Intent().apply {
            action = android.content.Intent.ACTION_SEND
            putExtra(android.content.Intent.EXTRA_TEXT, bookmark.url)
            type = "text/plain"
        }
        startActivity(android.content.Intent.createChooser(shareIntent, "Share Bookmark"))
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}