package com.antonioleiva.googlenowsearch

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import org.jetbrains.anko.longToast
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    companion object {
        private val ACTION_VOICE_SEARCH = "com.google.android.gms.actions.SEARCH_ACTION"
    }

    private var searchView by Delegates.notNull<SearchView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleVoiceSearch(intent)
    }

    private fun handleVoiceSearch(intent: Intent) {
        if (ACTION_VOICE_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            setSearchViewVisible(true)
            searchView.setQuery(query, true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        searchView = menu.findCompatActionView(R.id.action_search)
        searchView.setOnSearchClickListener { setSearchViewVisible(true) }

        searchView.onQueryText ({
            longToast(it)
            searchView.clearFocus()
            true
        })

        intent?.let { handleVoiceSearch(it) }

        return true
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            setSearchViewVisible(false)
        } else {
            super.onBackPressed()
        }
    }

    private fun setSearchViewVisible(visible: Boolean) {
        if (searchView.isIconified == visible) {
            searchView.isIconified = !visible
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
    }
}