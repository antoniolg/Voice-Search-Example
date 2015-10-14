package com.antonioleiva.googlenowsearch;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivityJava extends AppCompatActivity {

    private static final String ACTION_VOICE_SEARCH = "com.google.android.gms.actions.SEARCH_ACTION";
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleVoiceSearch(intent);
    }

    private void handleVoiceSearch(Intent intent) {
        if (intent != null && ACTION_VOICE_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            setSearchViewVisible(true);
            searchView.setQuery(query, true);
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                setSearchViewVisible(true);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivityJava.this, query, Toast.LENGTH_LONG).show();
                searchView.clearFocus();
                return true;
            }

            @Override public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        handleVoiceSearch(getIntent());

        return true;
    }

    @Override public void onBackPressed() {
        if (!searchView.isIconified()) {
            setSearchViewVisible(false);
        } else {
            super.onBackPressed();
        }
    }

    private void setSearchViewVisible(boolean visible) {

        if (searchView.isIconified() == visible) {
            searchView.setIconified(!visible);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
        }
    }
}
