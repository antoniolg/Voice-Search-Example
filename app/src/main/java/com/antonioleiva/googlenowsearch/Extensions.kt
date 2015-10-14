package com.antonioleiva.googlenowsearch

import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View

inline fun <reified T : View?> Menu.findCompatActionView(actionRes: Int): T {
    val searchItem = findItem(actionRes)
    return MenuItemCompat.getActionView(searchItem) as T
}

fun SearchView.onQueryText(submit: (String) -> Boolean = { false }, textChange: (String) -> Boolean = { false }) {

    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String): Boolean = submit(query)

        override fun onQueryTextChange(newText: String): Boolean = textChange(newText)

    })
}
